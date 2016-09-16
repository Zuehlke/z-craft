using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TinyIoC;

namespace TinyIoc.Tests
{
    [TestClass]
    public class UnitTest1
    {
        private IoC ioc;

        [TestInitialize]
        public void Setup()
        {
            ioc = new IoC();
        }

        [TestMethod]
        public void Aufgabe1_1()
        {
            var testImplementation = new TestImplementation();
            ioc.RegisterAsSingleton<ITestInterface>(testImplementation);

            var impl = ioc.Resolve<ITestInterface>();
            Assert.IsNotNull(impl);
            Assert.AreSame(testImplementation, impl);
        }

        [TestMethod]
        public void Aufgabe1_2()
        {
            var testImplementation = new TestImplementation();
            ioc.RegisterAsSingleton<ITestInterface>(testImplementation);
            var testImplementation2 = new TestImplementation2();
            ioc.RegisterAsSingleton<ITestInterface2>(testImplementation2);

            var impl1 = ioc.Resolve<ITestInterface>();
            Assert.IsNotNull(impl1);
            Assert.AreSame(testImplementation, impl1);

            var impl2 = ioc.Resolve<ITestInterface2>();
            Assert.IsNotNull(impl2);
            Assert.AreSame(testImplementation2, impl2);
        }

        [TestMethod]
        [ExpectedException(typeof(KeyNotFoundException))]
        public void Aufgabe1_3()
        {
            ioc.Resolve<ITestInterface>();
        }

        [TestMethod]
        public void Aufgabe2_1()
        {
            ioc.ConstructAndRegisterSingleton<ITestInterface, TestImplementation>();
            var impl = ioc.Resolve<ITestInterface>();

            Assert.IsNotNull(impl);
            Assert.AreEqual(typeof(TestImplementation), impl.GetType());
        }

        [TestMethod]
        public void Aufgabe2_2()
        {
            ioc.ConstructAndRegisterSingleton<ITestInterface, TestImplementation>();
            var impl1 = ioc.Resolve<ITestInterface>();
            var impl2 = ioc.Resolve<ITestInterface>();

            Assert.AreSame(impl1, impl2);
        }

        [TestMethod]
        public void Aufgabe3_1()
        {
            ioc.RegisterType<ITestInterface, TestImplementation>();
            var impl = ioc.Resolve<ITestInterface>();

            Assert.IsNotNull(impl);
            Assert.AreEqual(typeof(TestImplementation), impl.GetType());
        }

        [TestMethod]
        public void Aufgabe3_2()
        {
            ioc.RegisterType<ITestInterface, TestImplementation>();
            var impl1 = ioc.Resolve<ITestInterface>();
            var impl2 = ioc.Resolve<ITestInterface>();

            Assert.AreNotSame(impl1, impl2);
        }

        [TestMethod]
        public void Aufgabe4_1()
        {
            ioc.RegisterType<ITestInterface, TestImplementation>();
            ioc.RegisterType<ITestInterface2, TestImplementationWithDependencies>();

            var impl1 = ioc.Resolve<ITestInterface2>();

            Assert.IsNotNull(impl1);
            Assert.AreEqual(impl1.Number, 4);
        }

        [TestMethod]
        public void Aufgabe4_2()
        {
            ioc.LazyConstructAndRegisterSingleton<ITestInterface, TestImplementation>();
            ioc.LazyConstructAndRegisterSingleton<ITestInterface2, TestImplementationWithDependencies>();

            var impl1 = ioc.Resolve<ITestInterface2>();
            var impl2 = ioc.Resolve<ITestInterface2>();

            Assert.IsNotNull(impl1);
            Assert.AreSame(impl1, impl2);
            Assert.AreEqual(impl1.Number, 4);
        }

        [TestMethod]
        public void Aufgabe5_1()
        {
            ioc.RegisterType<ITestInterface, TestImplementation>();
            ioc.RegisterType<ITestInterface2, TestImplementationWithDependencies>();

            var impl1 = ioc.Resolve<ITestInterface2>();
            var impl2 = ioc.Resolve<ITestInterface2>();

            Assert.IsNotNull(impl1);
            Assert.AreNotSame(impl1, impl2);
            Assert.AreEqual(impl1.Number, 4);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Aufgabe6_1()
        {
            ioc.RegisterType<ITestInterface, TestCyclicDependency1>();
            ioc.RegisterType<ITestInterface2, TestCyclicDependency2>();

            ioc.Resolve<ITestInterface>();
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidOperationException))]
        public void Aufgabe6_2()
        {
            ioc.LazyConstructAndRegisterSingleton<ITestInterface, TestCyclicDependency1>();
            ioc.ConstructAndRegisterSingleton<ITestInterface2, TestCyclicDependency2>();

            ioc.Resolve<ITestInterface>();
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Aufgabe6_3()
        {
            ioc.LazyConstructAndRegisterSingleton<ITestInterface, TestCyclicDependency1>();
            ioc.LazyConstructAndRegisterSingleton<ITestInterface2, TestCyclicDependency2>();

            ioc.Resolve<ITestInterface>();
        }
    }
}