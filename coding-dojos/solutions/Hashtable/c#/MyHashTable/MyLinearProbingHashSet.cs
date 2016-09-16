using System.Diagnostics;

namespace MyHashTable
{
    public class MyLinearProbingHashSet
    {
        private long _elementCount;
        private long?[] _elements;

        public MyLinearProbingHashSet(int setSize = 64)
        {
            _elements = new long?[setSize];
        }

        private void DoubleMySetSize()
        {
            var newElements = new MyLinearProbingHashSet(_elements.Length*2);
            foreach (var element in _elements)
            {
                if (element.HasValue)
                {
                    newElements.Add(element.Value);
                }
            }

            _elements = newElements._elements;
        }

        public void Add(long i)
        {
            if (_elementCount >= _elements.Length/2)
            {
                DoubleMySetSize();
            }

            var index = GetHashIndex(i);
            while (_elements[index].HasValue)
            {
                index = (index + 1)%_elements.Length;
            }

            _elements[index] = i;
            _elementCount++;
        }

        public bool Contains(long i)
        {
            for (var index = GetHashIndex(i); _elements[index].HasValue; index = (index + 1)%_elements.Length)
            {
                if (_elements[index] == i)
                {
                    return true;
                }
            }

            return false;
        }

        private int GetHashIndex(long i)
        {
            return i.GetHashCode()%_elements.Length;
        }

        public bool Remove(long i)
        {
            Debug.WriteLine($"Looking for element at {GetHashIndex(i)}: {i}");
            if (!_elements[GetHashIndex(i)].HasValue)
            {
                Debug.WriteLine("\tNot found...");
                Debugger.Break();
                return false;
            }

            var elementIndex = -1;

            // Looking for the element to be removed
            for (var index = GetHashIndex(i); _elements[index].HasValue; index = NextIndex(index))
            {
                // Found different element
                if (_elements[index] != i)
                {
                    Debug.WriteLine($"\tFound different element at {index}: {_elements[index]}");
                    continue;
                }

                Debug.WriteLine($"\tFound it at {index}");
                // Found the element we were looking for
                _elements[index] = null;
                elementIndex = index;
                _elementCount--;
                break;
            }

            // Element not found
            if (elementIndex == -1)
            {
                Debug.WriteLine("\tNot found...");
                Debugger.Break();
                return false;
            }

            for (var index = NextIndex(elementIndex); _elements[index].HasValue; index = NextIndex(index))
            {
                // Found an element that belongs here
                if (GetHashIndex(_elements[index].Value) > elementIndex)
                {
                    Debug.WriteLine($"\tElement at {index} cannot be moved to {elementIndex}");
                    continue;
                }

                Debug.WriteLine($"\tMoved element at {index} to {elementIndex}");
                // Found an element that needs to fill the gap
                _elements[elementIndex] = _elements[index];
                _elements[index] = null;
                elementIndex = index;
            }

            return true;
        }

        private int NextIndex(int index)
        {
            return (index + 1)%_elements.Length;
        }
    }
}