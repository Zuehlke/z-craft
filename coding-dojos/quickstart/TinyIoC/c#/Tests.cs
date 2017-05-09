namespace TinyIoC
{
    using NUnit.Framework;

    [TestFixture]
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
            ioc = new IoC();
        }

        private IoC ioc;


        [Test]
        public void Aufgabe_1_1()
        {
            var implementation = new TestDependencyImplementation();

            ioc.RegisterAsSingleton<ITestInterface>(implementation);
            var actual = ioc.Resolve<ITestInterface>();

            Assert.NotNull(actual);
            Assert.AreEqual(implementation, actual);
        }
    }
}