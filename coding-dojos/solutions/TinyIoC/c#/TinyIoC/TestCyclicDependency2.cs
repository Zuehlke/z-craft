namespace TinyIoC
{
    public class TestCyclicDependency2 : ITestInterface2
    {
        private readonly ITestInterface _dependency;

        public TestCyclicDependency2(ITestInterface dependency)
        {
            _dependency = dependency;
        }

        public int Number
        {
            get { return 42; }
        }
    }
}