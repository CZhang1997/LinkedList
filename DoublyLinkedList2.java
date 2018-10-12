// Churong Zhang
//CS 3345.004. Data structures and algorithm analysis
//Fall 2018
//Intensive Track Assignment 1.3
//Aug 29, 2018

package cxz173430;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class DoublyLinkedList2<T>implements Iterable<T>
{

	private Entry<T> head;
	private Entry<T> tail;
	int size;
	
	static class Entry<E>{
		E element;
		Entry<E> next;
		Entry<E> prev;
		
		Entry(E ele, Entry<E> pre, Entry<E> nex)
		{
			element = ele;
			next = nex;
			prev = pre;
		}
	}
	
	public DoublyLinkedList2()
	{
		size = 0;
		//head = new Entry(null, null, null);
		//tail = head;
	}
	public Entry<T> findEntry(T ele)
	{
		Entry<T> current = head;
		while(current != null && current.element != ele)
		{
			current = current.next;   
		}
		return current;
	}
	public DoublyLinkedListIterator<T> iterator()
	{
		return new DLLIterator();
	}
	
	protected class DLLIterator implements DoublyLinkedListIterator<T>{
		private Entry<T> cursor;
		private boolean ready;
		
		public DLLIterator()
		{
			// head did not link with the initial point
			// so once iterator call next, it will go to head
			// and would not able to go back
			cursor = new Entry<T>(null, null,head);
			ready = false;
		}
		public boolean hasNext()
		{
			return cursor.next != null;
		}
		public T next()
		{
			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}
		
		public boolean hasPrev()
		{
			return cursor.prev != null;
		}
		public T prev() {
			cursor = cursor.prev;
			ready = true;
			return cursor.element;
			
		}
		
		public void add(T x)
		{
			if(!ready)
			{
				
				throw new NoSuchElementException();
			}
			
				Entry<T> ent = new Entry<>(x, cursor, cursor.next);
			
				if(cursor.next != null)
					cursor.next.prev = ent;
				cursor.next = ent;
				size++;
		}
		
		public void remove()
		{
			if(!ready)
			{
				//throw an exception
				throw new NoSuchElementException();
			}
			if(cursor.next == null)
			{
				cursor.prev.next = null;
				cursor = cursor.prev;
			
				//return remove(cursor);
			}
			else if (cursor.prev == null)
			{
				cursor.next.prev = null;
				cursor = cursor.next;
			}
			else
			{
				cursor.next.prev = cursor.prev;
				cursor.prev.next = cursor.next;
				cursor = cursor.prev;
			}
			size --;
			ready = false;
		}
		
	}
	/// end of DLLIterator class
	public interface DoublyLinkedListIterator<T> extends Iterator<T> {
    	//public void add(Entry<T> x);

    	public void add(T x);

    	public T prev();

    	public boolean hasPrev();
    }
	public T remove(T x)
	{
		Entry<T> ent = findEntry(x);
		if(ent == null)
		{
			throw new NoSuchElementException();
			//return null;
		}
		else 
		{
			return remove(ent);
		}
	}
	private T remove(Entry<T> ent)
	{
		
		if(size == 0)
		{
			// throw a exception
			throw new NoSuchElementException();
		}
		T ele = ent.element;
		if(ent == head)
		{
			if(size == 1)
			{
				head = null;
				tail = head;
			}
			else
			{
				head = head.next;
				head.prev = null;
			}
		}
		else if(ent == tail)
		{
			tail = tail.prev;
			tail.next = null;
		}
		else
		{
			// when list has minimum size of 3 
			ent.prev.next = ent.next;
			ent.next.prev = ent.prev;
		}
		size--;
		return ele;
	}
	public int size()
	{
		return size;
	}
	public boolean contain(T x)
	{
		return findEntry(x) != null;
	}
	public void addFirst(T x)
	{
		Entry<T> ent = new Entry<>(x, null, head);
		size++;
		head.prev = ent;
		head = head.prev;
	}
	public void addLast(T x)
	{
		add(x); 
	}
	public T removeFirst()
	{
		return remove(head);
	}
	public T removeLast()
	{
		return remove(tail);
	}
	public Entry<T> find(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
			//return null;
		}
		else 
		{
			Entry<T> cursor = head;
			int i = 0;
			while(i < index)
			{
				cursor = cursor.next;
				i++;
			}
			return cursor;
			
		}
	}
	public T get(int index)
	{
		return find(index).element;
	}
	public void set(int index, T x)
	{
		find(index).element = x;
	}
	public void add(int index, T x)
	{
		if(index == 0)
			addFirst(x);
		else if(index == size)
			add(x);
		else
			add(find(index).prev, x);
	}
	public T removeByIndex(int index)
	{
		return remove(find(index));
	}
	public void add(T x)
	{
		add(tail,x);
	}
	public void add(Entry<T> p , T x)
	{
		if(size == 0)
		{
			head = new Entry<T>(x, null, null);
			tail = head;
		}
		else if(p == head)
		{
			Entry<T> ent = new Entry<>(x,p, p.next);
			if(head.next != null)
			{
				head.next.prev = ent;
			}
			else
			{
				tail = ent;
			}
			head.next = ent;
			
		}
		else if(p == tail)
		{
			Entry<T> ent = new Entry<>(x,p, null);
			p.next = ent;
			tail = tail.next;
		}
		else
		{
			Entry<T> ent = new Entry<>(x,p, p.next);
			p.next.prev = ent;
			p.next = ent;
		}
		size++;
	}
	
	public void printList() {
		System.out.print(this.size + ": ");
		for(T item: this) {
		    System.out.print(item + " ");
		}

		System.out.println();
	    }
	
	public static void main(String [] args)
	{
		DoublyLinkedList2<Integer> list = new DoublyLinkedList2<>();
		list.add(5);
		
		Entry<Integer> testFind = list.findEntry(5);
		System.out.println("Testing Find method, assume 5 is inside the list, and 6 is not");
		System.out.println("The result for find 5: " + testFind.element);
		try {
			testFind = list.findEntry(6);
			System.out.println("The result for find 6: " + testFind.element);
		}
		catch(NullPointerException e)
		{
			System.out.println("Null Pointer Exception.");
		}
		
		list.add(8);
		list.add(9);
		list.add(56);
		list.addFirst(66);
		list.addLast(18);
		
		System.out.println("Test for hasNext, next ");
		DoublyLinkedListIterator<Integer> output = list.iterator();
		System.out.println("Original List of size: "+ list.size());
		list.printList();
		while(output.hasNext())
		{
			System.out.print(output.next() + " ");
		}
		System.out.println("");
		System.out.println("//// test contain");
		
		if(list.contain(9))
		{
			if(!list.contain(4))
			{
				System.out.println("contain works");
			}
		}
		
		
		System.out.println("Remove First: " + list.removeFirst());
		System.out.println("Remove Last: " + list.removeLast());
		System.out.println("Remove 8: " + list.remove(8));
		output = list.iterator();
		System.out.println("List after delete first and last of size: " + list.size());
		list.add(64);
		list.add(68);
		list.printList();
		while(output.hasNext())
		{
			int a = output.next();
			//System.out.print(a + " ");
			if(a == 9)
				{
				output.remove();
				System.out.println("remove 9");
				}
				
		}
		//System.out.println("");
		System.out.println("///// test find(index)//////////");
		System.out.println("The index at 1 has element: " + list.find(1).element);
		System.out.println("//// test Iterator add, hasPrev, prev");
		output.add(25);
		
		list.printList();
		
		while(output.hasPrev())
		{
			int c = output.prev();
			if(c == 64)
				output.remove();
			//System.out.print(c + " ");
		}
		//System.out.println("");
		
		output.add(24);
		list.printList();
		System.out.println("///// test get, set, and add(index, x) ");
		System.out.println("The index at 1: " + list.get(1));
		System.out.println("Set index 1 to 30.");
		list.set(1, 30);
		System.out.println("add value 36 at index 2");
		list.add(2, 36);
		list.printList();
		System.out.println("Remove index 3");
		list.removeByIndex(3);
		list.printList();
		
		
	}
	
}
