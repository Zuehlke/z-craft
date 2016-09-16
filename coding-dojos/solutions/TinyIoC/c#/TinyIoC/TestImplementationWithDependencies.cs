namespace TinyIoC
{
    public class TestImplementationWithDependencies : ITestInterface2
    {
        private readonly ITestInterface _testInterface;

        public TestImplementationWithDependencies(ITestInterface testInterface)
        {
            _testInterface = testInterface;
        }

        public int Number
        {
            get { return _testInterface.Number + 1; }
        }
    }
}