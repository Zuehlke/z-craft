using System;
using NUnit.Framework;

namespace MyHashTable.Test
{
    [TestFixture]
    public class MyHashTableTests
    {
        [SetUp]
        public void Setup()
        {
            _hashTable = new MyLinearProbingHashSet();
            _timer = DateTime.Now;
        }

        [TearDown]
        public void TearDown()
        {
            Console.WriteLine($"The test duration was (ms): {DateTime.Now.Subtract(_timer).TotalMilliseconds}");
        }

        private MyLinearProbingHashSet _hashTable;
        private DateTime _timer;

        [Test]
        public void CanAddElement()
        {
            _hashTable.Add(5);
            Assert.IsTrue(_hashTable.Contains(5));
        }

        [Test]
        [TestCase(1000)]
        //[TestCase(10000)]
        //[TestCase(100000)]
        //[TestCase(1000000)]
        public void CanHandleLargeInputs(int inputSize)
        {
            _timer = DateTime.Now;
            var r = new Random();
            var randomValues = new long[inputSize];

            for (long i = 0; i < randomValues.Length; i++)
            {
                randomValues[i] = r.Next();
            }

            for (long i = 0; i < randomValues.Length; i++)
            {
                _hashTable.Add(randomValues[i]);
            }
            for (long i = 0; i < randomValues.Length; i++)
            {
                Assert.IsTrue(_hashTable.Contains(randomValues[i]),
                    $"Collision at {i}: {randomValues[i]}");
            }
        }

        [Test]
        public void CannotRemoveMissingElement()
        {
            Assert.IsFalse(_hashTable.Remove(5));
        }

        [Test]
        public void CanRemoveExistingElement()
        {
            _hashTable.Add(5);
            Assert.IsTrue(_hashTable.Remove(5));
        }

        [Test]
        [TestCase(1000)]
        [TestCase(10000)]
        // [TestCase(100000)]
        // [TestCase(1000000)]
        public void CanRemoveLargeInputs(int inputSize)
        {
            _timer = DateTime.Now;
            var r = new Random();
            var randomValues = new long[inputSize];

            for (long i = 0; i < randomValues.Length; i++)
            {
                randomValues[i] = r.Next();
            }

            for (long i = 0; i < randomValues.Length; i++)
            {
                _hashTable.Add(randomValues[i]);
            }

            for (long i = 0; i < randomValues.Length*3/4; i++)
            {
                Assert.IsTrue(_hashTable.Remove(randomValues[i]),
                    $"Could not remove element at {i}: {randomValues[i]}");
            }

            for (long i = randomValues.Length*3/4; i < randomValues.Length; i++)
            {
                Assert.IsTrue(_hashTable.Contains(randomValues[i]),
                    $"Could not find element at {i}: {randomValues[i]}");
            }
        }

        [Test]
        public void CanRemoveElementsAtTheEnd()
        {
            _timer = DateTime.Now;
            var randomValues = new long[20];
            randomValues[0] = 62;
            randomValues[1] = 62;
            randomValues[2] = 62;
            randomValues[3] = 62;
            randomValues[4] = 62;
            randomValues[5] = 1;
            randomValues[6] = 1;
            randomValues[7] = 62;
            randomValues[8] = 62;
            randomValues[9] = 62;
            randomValues[10] = 1;
            randomValues[11] = 1;
            randomValues[12] = 1;
            randomValues[13] = 1;
            randomValues[14] = 1;
            randomValues[15] = 1;
            randomValues[16] = 1;
            randomValues[17] = 1;
            randomValues[18] = 1;
            randomValues[19] = 1;

            for (long i = 0; i < randomValues.Length; i++)
            {
                _hashTable.Add(randomValues[i]);
            }

            for (long i = 0; i < randomValues.Length / 2; i++)
            {
                Assert.IsTrue(_hashTable.Remove(randomValues[i]),
                    $"Could not remove element at {i}: {randomValues[i]}");
            }

            for (long i = randomValues.Length / 2; i < randomValues.Length; i++)
            {
                Assert.IsTrue(_hashTable.Contains(randomValues[i]),
                    $"Could not find element at {i}: {randomValues[i]}");
            }
        }

        [Test]
        public void Inital_HashSet_Is_Empty()
        {
            Assert.IsFalse(_hashTable.Contains(0));
        }
    }
}