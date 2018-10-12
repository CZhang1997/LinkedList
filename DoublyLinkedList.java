// Churong Zhang
//CS 3345.004. Data structures and algorithm analysis
//Fall 2018
//Intensive Track Assignment 1.1
//Aug 29, 2018

package cxz173430;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import cxz173430.SinglyLinkedList.Entry;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> 
{
    static class Entry<E> extends SinglyLinkedList.Entry<E> 
    {
		Entry<E> prev;
		Entry(E x, Entry<E> next, Entry<E> prev)
		{
		    super(x, next);
		    this.prev = prev;
		}
    }
    public DoublyLinkedList()
    {
    	super.head = new Entry<>(null, null, null);
    	super.tail = head; 
    	
    	
    }
    public void add(T x) {
    	add(new Entry<>(x, null, null));
        }

    public void add(Entry<T> ent) {
    	ent.prev = (DoublyLinkedList.Entry<T>)tail;
    	tail.next = ent;
    	tail = tail.next;
    	size++;
        }
    
    public DoublyLinkedListIterator<T> dllIterator() { return new DLLIterator(); }
   
    protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T>
    {
    public DLLIterator() {
    	super.cursor = head;
    	ready = false;
    }
    public boolean hasPrevious()
    {
    	return ((DoublyLinkedList.Entry<T>)cursor).prev != head;
    }
    public T previous() 
    {  
    	prev =  cursor;
    	cursor = ((DoublyLinkedList.Entry<T>)cursor).prev; 
    	ready = true;
    	return cursor.element;
    }
    public void add(T x) {
    	add(new Entry<>(x, null, null));
        }
    public void add(Entry<T> ent) {
	   ent.prev = (DoublyLinkedList.Entry<T>)cursor;
	   if(cursor.next != null)
	   {
		   ((DoublyLinkedList.Entry<T>)cursor.next).prev = ent;
		   ent.next = cursor.next;
	   }
	
	   cursor.next = ent;
	   size++;
        }
    public void remove()
    {
    	 if(!ready) {
    			throw new NoSuchElementException();
    		    }
    	 
    	(((DoublyLinkedList.Entry<T>)cursor).prev).next =  (DoublyLinkedList.Entry<T>)cursor.next; 
    	// this would not create problem
    	// because the it require a minimum size of 2 to get here.
    	// and SLL used dummy head which make the list has a minimum of 3 entries 
        if(cursor == tail) {
     		tail = prev;
     	}
        else
        	 ((DoublyLinkedList.Entry<T>)cursor.next).prev = ((DoublyLinkedList.Entry<T>)cursor).prev;
   
    	 cursor = prev;
    	 ready = false;
    	 size--;
    }
    
 }
    public interface DoublyLinkedListIterator<T> extends Iterator<T> {
    	public void add(Entry<T> x);

    	public void add(T x);

    	public T previous();

    	public boolean hasPrevious();
    }
    
    
    public static void main(String[] args) throws NoSuchElementException{
    	
    	  DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    	   for(int i=1; i<=10; i++) { list.add(i); }
    	   DoublyLinkedListIterator<Integer> iter = list.dllIterator();
    	   
    	   while(iter.hasNext()) {
    		int x = iter.next();
    		if(x == 3 || x == 8) {
    		    iter.add(10+x);
    		} else if(x == 5) {
    		    iter.remove();
    		}
    	   }
    	  
    	   list.printList();
    	   iter.remove(); // remove the last item
    	while(iter.hasPrevious())
    	{
    		int y = iter.previous();
    		//if(y == 7)
    			//iter.remove();
    		System.out.print(y + " ");
    	}
    	
    	System.out.println("");
    	System.out.println("Remove the first element ");
    	iter.remove();
		list.printList();
    
    }
    

}
