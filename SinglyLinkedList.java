// Churong Zhang
//CS 3345.004. Data structures and algorithm analysis
//Fall 2018
//Intensive Track Assignment 1.2
//Aug 29, 2018

package cxz173430;

import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    static class Entry<E> {
        E element;
        Entry<E> next;

        Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }	
    }

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;
    int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }
    public T get(int index)
    {
    	if(size < index || index < 0)
    	{
    		throw new NoSuchElementException();
    	}
    	Entry<T> temp = getEntry(index);
    	return temp.element;
    }
    /** Helper method for get, set
     * 	add, and remove
     * @param index index position
     * @return the Entry at the index position
     */
    private Entry<T> getEntry(int index)
    {
    	Entry<T> temp = head;
    	int i = 0;
    	while(i <= index)
    	{
    		temp = temp.next;
    		i++;
    	}
    	return temp;
    }
    public void set(int index, T x)
    {
    	if(size < index || index < 0)
    	{
    		throw new NoSuchElementException();
    	}
    	Entry<T> temp = getEntry(index);
    	temp.element = x;
    }
    public void add(int index, T x)
    {
    	if(size < index || index < 0)
    	{
    		throw new NoSuchElementException();
    	}
    	
    	Entry<T> temp = getEntry(index-1);
    	Entry<T> newElement = new Entry<T>(x, temp.next);
    	size++;
    	temp.next = newElement;
    }
    public T remove(int index)
    {
    	if(size < index || index < 0)
    	{
    		throw new NoSuchElementException();
    	}
    	
    	Entry<T> temp = getEntry(index-1);
    	// store T before remove it
    	T ret = temp.next.element;
    	temp.next = temp.next.next;
    	size--;
    	return ret;
    }
    public int size()
    {
    	return size;
    }
    
    public Iterator<T> iterator() { return new SLLIterator(); }

    protected class SLLIterator implements Iterator<T> {
	Entry<T> cursor, prev;
	boolean ready;  // is item ready to be removed?

	SLLIterator() {
	    cursor = head;
	    prev = null;
	    ready = false;
	}

	public boolean hasNext() {
	    return cursor.next != null;
	}
	
	public T next() {
	    prev = cursor;
	    cursor = cursor.next;
	    ready = true;
	    return cursor.element;
	}

	// Removes the current element (retrieved by the most recent next())
	// Remove can be called only if next has been called and the element has not been removed
	public void remove() {
	    if(!ready) {
		throw new NoSuchElementException();
	    }
	    prev.next = cursor.next;
	    // Handle case when tail of a list is deleted
	    if(cursor == tail) {
		tail = prev;
	    }
	    cursor = prev;
	    ready = false;  // Calling remove again without calling next will result in exception thrown
	    size--;
		}
    }  // end of class SLLIterator

    // Add new elements to the end of the list
    public void add(T x) {
	add(new Entry<>(x, null));
    }

    public void add(Entry<T> ent) {
	tail.next = ent;
	tail = tail.next;
	size++;
    }

    public void printList() {
	System.out.print(this.size + ": ");
	for(T item: this) {
	    System.out.print(item + " ");
	}

	System.out.println();
    }

    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implemented by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
	if(size < 3) {  // Too few elements.  No change.
	    return;
	}

	Entry<T> tail0 = head.next;
	Entry<T> head1 = tail0.next;
	Entry<T> tail1 = head1;
	Entry<T> c = tail1.next;
	int state = 0;

	// Invariant: tail0 is the tail of the chain of elements with even index.
	// tail1 is the tail of odd index chain.
	// c is current element to be processed.
	// state indicates the state of the finite state machine
	// state = i indicates that the current element is added after taili (i=0,1).
	while(c != null) {
	    if(state == 0) {
		tail0.next = c;
		tail0 = c;
		c = c.next;
	    } else {
		tail1.next = c;
		tail1 = c;
		c = c.next;
	    }
	    state = 1 - state;
	}
	tail0.next = head1;
	tail1.next = null;
	// Update the tail of the list
	tail = tail1;
    }

    public static void main(String[] args) throws NoSuchElementException {
    	
    	
    	/*
    	SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    	for(int i = 0; i< 1000000; i++)
    	{
    		list.add((int)(Math.random()*1000000));
    	}
    	long tStart = System.currentTimeMillis();
    	int max = 0;
    	   for(Integer x: list) { if(max < x) { max = x; } }
    	   System.out.println(max);
    	long tEnd = System.currentTimeMillis();
    	long tDelta = tEnd - tStart;
    	double elapsedSeconds = tDelta / 1000.0;
    	System.out.println("First used time: " + elapsedSeconds);
    	
    	tStart = System.currentTimeMillis();
    	int mai = 0;
    	for(int i=0; i<list.size(); i++) {
    		int x = list.get(i);
    		if(mai < x) { mai = x; }
    	   }
    	   System.out.println(mai);
    	 tEnd = System.currentTimeMillis();
    	 tDelta = tEnd - tStart;
    	 elapsedSeconds = tDelta / 1000.0;
    	 System.out.println("second used time: " + elapsedSeconds);
    	*/
    	 
    	//* Test new methods 
    	  SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    	for(int i = 0; i< 5; i++)
    	{
    		list.add(i);
    	}
    	list.printList();
    	for(int i = 0; i< 5; i++)
    	{
    		System.out.print(list.get(i)+ " ");
    		list.set(i, i*5);
    	}
    	System.out.println("");
    	list.printList();
    	list.add(5, 13);
    	list.printList();
    	System.out.println("Remove " + list.remove(2));
    	list.printList();
    	
    	
    	
      /*  int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

	Iterator<Integer> it = lst.iterator();
	Scanner in = new Scanner(System.in);
	whileloop:
	while(in.hasNext()) {
	    int com = in.nextInt();
	    switch(com) {
	    case 1:  // Move to next element and print it
		if (it.hasNext()) {
		    System.out.println(it.next());
		} else {
		    break whileloop;
		}
		break;
	    case 2:  // Remove element
		it.remove();
		lst.printList();
		break;
	    default:  // Exit loop
		 break whileloop;
	    }
	}
	
	lst.printList();
	lst.unzip();
        lst.printList();*/
    }
	
 }

/* Sample input:
   1 2 1 2 1 1 1 2 1 1 2 0
   Sample output:
10: 1 2 3 4 5 6 7 8 9 10 
1
9: 2 3 4 5 6 7 8 9 10 
2
8: 3 4 5 6 7 8 9 10 
3
4
5
7: 3 4 6 7 8 9 10 
6
7
6: 3 4 6 8 9 10 
6: 3 4 6 8 9 10 
6: 3 6 9 4 8 10
*/