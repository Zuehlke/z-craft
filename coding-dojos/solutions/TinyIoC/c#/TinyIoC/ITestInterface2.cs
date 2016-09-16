namespace TinyIoC
{
    public interface ITestInterface2
    {
        int Number { get; }
    }

    public class TestImplementation2 : ITestInterface2
    {
        public int Number
        {
            get { return 5; }
        }
    }
}