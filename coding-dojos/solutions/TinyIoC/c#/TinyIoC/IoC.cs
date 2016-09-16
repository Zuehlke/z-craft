using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace TinyIoC
{
    public interface IResolver
    {
        object Resolve(ResolveContext resolveContext);
    }

    public class SingletonResolver : IResolver
    {
        private readonly object _instance;

        public SingletonResolver(object instance)
        {
            _instance = instance;
        }

        public object Resolve(ResolveContext resolveContext)
        {
            return _instance;
        }
    }

    public class TypeResolver : IResolver
    {
        private readonly Func<ResolveContext, object> _ctor;
        private readonly Type _myInterface;

        public TypeResolver(Func<ResolveContext, object> ctor, Type myInterface)
        {
            _ctor = ctor;
            _myInterface = myInterface;
        }

        public object Resolve(ResolveContext resolveContext)
        {
            resolveContext.OnResolve(_myInterface);
            return _ctor(resolveContext);
        }
    }

    public class LazySingletonResolver : IResolver
    {
        private readonly Func<ResolveContext, object> _ctor;
        private readonly Type _myInterface;
        private object _instance;

        public LazySingletonResolver(Func<ResolveContext, object> ctor, Type myInterface)
        {
            _ctor = ctor;
            _myInterface = myInterface;
        }

        public object Resolve(ResolveContext resolveContext)
        {
            return _instance ?? CreateInstance(resolveContext);
        }

        private object CreateInstance(ResolveContext resolveContext)
        {
            resolveContext.OnResolve(_myInterface);
            return (_instance =_ctor(resolveContext));
        }
    }

    public  class IoC
    {
        private readonly Dictionary<Type, IResolver> _resolvers = new Dictionary<Type, IResolver>();

        public  void RegisterAsSingleton<T>(T instance) 
        {
            _resolvers[typeof(T)] = new SingletonResolver(instance);
        }

        public  T Resolve<T>()
        {
            return (T) Resolve(typeof(T));
        }

        public void ConstructAndRegisterSingleton<T, TConcrete>() where TConcrete : T
        {
            RegisterAsSingleton<T>(CreateInstance<TConcrete>(new ResolveContext()));
        }

        public void RegisterType<T, TConcrete>() where TConcrete : T
        {
            _resolvers[typeof(T)] = new TypeResolver((c) => CreateInstance<TConcrete>(c), typeof(T));
        }

        public void LazyConstructAndRegisterSingleton<T, TConcrete>() where TConcrete : T
        {
            _resolvers[typeof(T)] = new LazySingletonResolver((c) => CreateInstance<TConcrete>(c), typeof(T));
        }

        private TConcrete CreateInstance<TConcrete>(ResolveContext context) 
        {
            var constructor = typeof(TConcrete)
                .GetConstructors()
                .OrderByDescending(c => c.GetParameters().Length)
                .First(IsResolvable);

            var parameterInfos = constructor.GetParameters();
            var parameterValues = parameterInfos.Select(p => Resolve(p.ParameterType, context));

            return (TConcrete)constructor.Invoke(parameterValues.ToArray());
        }

        private bool IsResolvable(ConstructorInfo c)
        {
            return c.GetParameters().All(IsResolvable);
        }

        private bool IsResolvable(ParameterInfo p)
        {
            return _resolvers.ContainsKey(p.ParameterType);
        }

        public object Resolve(Type t, ResolveContext resolveContext = null)
        {
            resolveContext = resolveContext ?? new ResolveContext();
            return _resolvers[t].Resolve(resolveContext);
        }
    }

    public class ResolveContext
    {
        private readonly List<Type> seenTypes = new List<Type>();

       
        public void OnResolve(Type t)
        {
            if (seenTypes.Contains(t))
            {
                throw new ArgumentException("Because this is cyclic, bitch");
            }
            seenTypes.Add(t);
        }
    }
}
