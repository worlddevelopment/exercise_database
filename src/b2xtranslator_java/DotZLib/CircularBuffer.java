//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;


//
// � Copyright Henrik Ravn 2004
//
// Use, modification and distribution are subject to the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
/**
* This class implements a circular buffer
*/
public class CircularBuffer   
{
    private int _capacity;
    private int _head;
    private int _tail;
    private int _size;
    private byte[] _buffer;
    public CircularBuffer(int capacity) throws Exception {
        Debug.Assert(capacity > 0);
        _buffer = new byte[capacity];
        _capacity = capacity;
        _head = 0;
        _tail = 0;
        _size = 0;
    }

    public int getSize() throws Exception {
        return _size;
    }

    public int put(byte[] source, int offset, int count) throws Exception {
        Debug.Assert(count > 0);
        int trueCount = Math.min(count,_capacity - getSize());
        for (int i = 0;i < trueCount;++i)
            _buffer[(_tail + i) % _capacity] = source[offset + i];
        _tail += trueCount;
        _tail %= _capacity;
        _size += trueCount;
        return trueCount;
    }

    public boolean put(byte b) throws Exception {
        if (getSize() == _capacity)
            return false;
         
        // no room
        _buffer[_tail++] = b;
        _tail %= _capacity;
        ++_size;
        return true;
    }

    public int get(byte[] destination, int offset, int count) throws Exception {
        int trueCount = Math.min(count,getSize());
        for (int i = 0;i < trueCount;++i)
            destination[offset + i] = _buffer[(_head + i) % _capacity];
        _head += trueCount;
        _head %= _capacity;
        _size -= trueCount;
        return trueCount;
    }

    public int get() throws Exception {
        if (getSize() == 0)
            return -1;
         
        int result = (int)_buffer[_head++ % _capacity];
        --_size;
        return result;
    }

}


