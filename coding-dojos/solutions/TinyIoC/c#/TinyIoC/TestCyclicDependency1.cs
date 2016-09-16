namespace TinyIoC
{
    public class TestCyclicDependency1 : ITestInterface
    {
        private readonly ITestInterface2 _dependency;

        public TestCyclicDependency1(ITestInterface2 dependency)
        {
            _dependency = dependency;
        }

        public int Number
        {
            get { return 42; }
        }
    }
}