using System.Collections.Generic;

namespace MyHashTable
{
    public class MyHashSet
    {
        private List<long>[] _elements;

        private long _elementCount = 0;

        public MyHashSet(int setSize = 64)
        {
            _elements = new List<long>[setSize];
            for (long i = 0; i < _elements.Length; i++)
            {
                _elements[i] = new List<long>();
            }
        }

        private void DoubleMySetSize()
        {
            var newElements = new MyHashSet(_elements.Length * 2);
            foreach (var element in _elements)
            {
                foreach (var e in element)
                {
                    newElements.Add(e);
                }
            }

            _elements = newElements._elements;
        }

        public void Add(long i)
        {
            _elements[GetHashIndex(i)].Add(i);
            _elementCount++;

            if (_elementCount > _elements.Length)
            {
                DoubleMySetSize();
            }
        }

        public bool Contains(long i)
        {
            return _elements[GetHashIndex(i)].Contains(i);
        }

        private int GetHashIndex(long i)
        {
            return i.GetHashCode()%_elements.Length;
        }

        public bool Remove(long i)
        {
            var success = _elements[GetHashIndex(i)].Remove(i);
            if (success)
            {
                _elementCount--;
            }
            return success;
        }
    }
}