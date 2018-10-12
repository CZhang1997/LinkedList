//		CS 3345.004. Data structures and algorithm analysis
//		Fall 2018
//		Churong Zhang
//		Assignment 3
//		Sep 5, 2018

package cxz173430;
import java.util.Iterator;

/////////////////////////////////////////////
//////////////Problem 1 /////////////////////
/////////////////////////////////////////////

//	1. What is the running time of the following two programs on a linked list with n elements:

	//   (a) for(x: list) { print x }  /* using the iterator */
		/* same as:  it = list.iterator();  
		 * 				while(it.hasNext()) { print it.next() } */

/*		list.iterator() 		-> O(1)
 * 		it.hasNext()   			-> O(1)
 * 		print it.next()			-> O(1)
 * 		while(it.hasNext()) 	-> O(n)
 * 		conclusion RT of (a) 	-> O(n);
 */

	//   (b) for(i=0; i<list.size(); i++) { print list.get(i) }
/*		
 * 		i = 0					-> O(1)
 * 		i<list.size()			-> O(1)
 * 		i++ 					-> O(1)
 * 		print					-> O(1) 
 * 		list.get(i)				-> O(n) because get(i) run from the beginning of the list until it reach i, 
 * 										worst case is i = n-1; which is O(n)
 * 		for loop 				-> O(n) because the loop run from zero to list.size - 1, which is O(n)
 * 		since list.get(i) is inside the for loop, 		
 * 		the RT of (b) is O(n^2)
 */

///////////////////////////////////////////////////
///////////////Problem 2 //////////////////////////
///////////////////////////////////////////////////
public class ArrayList implements Iterable<Object>{
	Object [] list;
	int size;
	
	public ArrayList()
	{
		list = new Object[2];
		size = 0;
	}
	public Object get(int index)
	{
		return list[index];
	}
	public void set(int index, Object o)
	{
		list[index] = o;
	}
	public void add(int index, Object o)
	{
		
		// check if the current array is full
		// resize the array if it is full
		if(size == list.length)
		{
			reSize();
			add(index, o);
		}
		else
		{
			int a = size;
			for(; a > index; a--)
			{
				list[a] = list[a-1];
			}
			list[a] = o;
			size ++ ;
		}
	}
	public void add(Object o)
	{
		add(size, o);
		
	}
	
	private void reSize()
	{
		Object[] temp = new Object[size * 2];
		for(int a = 0; a < list.length; a++)
		{
			temp[a] = list[a];
		}
		list = temp;
	}
	public Object remove(int index)
	{
		size --;
		Object ret = list[index];
		for(; index < size ; index ++)
		{
			list[index] = list[index + 1];
		}
		return ret;
	}
	public int size()
	{
		return size;
	}
	public String toString()
	{
		String s = size + ": ";
		for(int i = 0; i < size; i++)
			s += list[i] + " ";
		//s += '\n';
		return s;
	}
	public Iterator<Object> iterator() {
		return new ArrayIterator();
	}
	public int find(Object o)
	{
		int i = 0;
		while(i < size)
		{
			if(list[i] == o)
				return i;
			i++;
		}
		return -1;
	}
	public boolean contain(Object o)
	{
		return find(o) != -1;
	}
	public void addFirst(Object o)
	{
		add(0,o);
	}
	public void addLast(Object o)
	{
		add(o);
	}
	public Object removeFirst()
	{
		return remove(0);
	}
	public Object removeLast()
	{
		return remove(size-1);
	}

	protected class ArrayIterator implements Iterator<Object>

	
	{
		int pos;
		public ArrayIterator()
		{
			pos = -1;
		}
		public boolean hasNext()
		{
			return pos < size - 1;
		}
		public Object next() 
		{
			pos ++;
			return list[pos];
		}
		
		public void remove()
		{
			size --;
			for(int a = pos; a < size; a++ )
			{
				list[a] = list [a+1];
			}
		}
		public void add(Object o)
		{
			int a = size;
			if(size == list.length)
			{
				Object[] temp = new Object[size * 2];
				for(int i = 0; i < list.length; i++)
				{
					temp[i] = list[i];
				}
				list = temp;
			}
			for(; a > pos + 1; a--)
			{
				list[a] = list[a-1];
			}
			list[a] = o;
			size ++ ;
		}
	}
	
	
	public static void main(String[] args) {
		
		ArrayList list = new ArrayList();
		for(int a = 0; a < 8; a++)
		{
			list.add((int)(Math.random() * 8));
		}
		System.out.println(list);
		list.remove(5);
		System.out.println(list);
		Iterator <Object> out = list.iterator();
		while(out.hasNext())
		{
			int a = (int)out.next();
			if(a == 4)
				out.remove();
			
			System.out.print(a + " ");
			
		}
		
		System.out.print('\n' + "number 5 at index: " + list.find(5) + '\n');
		System.out.println(list);
		list.add(64);
		list.add(98);
		list.add(7, 25);
		list.removeLast();
		System.out.println(list);
		
		
	}
	/////////////////////////////////////////////////////
	/////////////Problem 3//////////////////////////////
	/////beginning of BoundedQueue
	 public class BoundedQueue
	{
		Object[] queue;
		int start;
		int end;
		int size;
		public BoundedQueue(int size)
		{
			queue = new Object[size];
			start = 0;
			end = 0;
			this.size = size;
		}
		public boolean offer(Object x)
		{
			if(end - start == size)
				return false;
			queue[end%size] = x;
			end++;
			return true;
		}
		public Object poll()
		{
			if(isEmpty())
				return null;
			start++;
			return queue[(start-1)%size];
		}
		public Object peek()
		{
			if(isEmpty())
				return null;
			return queue[start%size];
		}
		public int size()
		{
			return end - start;
		}
		public boolean isEmpty()
		{
			return start == end;
		}
		public void clear()
		{
			start = end = 0;
		}
		public void toArray(Object[] a)
		{
			clear();
			int i = 0;
			while(i < size)
			{
				offer(a[i]);
				i++;
			}
		}
////////////////////////////////////
////////////end of BoundedQueue/////
////////////////////////////////////
		
	/*///////////Tester can be use on another file
	public static void main(String[] args) {
		
		///////////////// test queue
		BoundedQueue queue = new BoundedQueue(5);
		int items = 7;
		for(int a = 0; a < items; a++ )
		{
			if(!queue.offer(a))
				System.out.println(a + " is not added to the queue");
		}
		System.out.println(queue.peek() + " is at the top.");
		System.out.println(queue.poll() + " is remove");
		while(!queue.isEmpty())
			System.out.println(queue.poll() + " is remove");
		System.out.println(queue.poll() + " is remove");
		Object[] list = new Object[10];
		for(int a= 0; a < list.length; a++)
		{
			list[a] = (int) (Math.random()* 50);
		}
		queue.toArray(list);
		System.out.println(queue.poll() + " is remove");
		System.out.println(list[5]+ " is here");
		while(!queue.isEmpty())
			System.out.println(queue.poll() + " is remove");
	}
	 * 
	 */
	}
}

