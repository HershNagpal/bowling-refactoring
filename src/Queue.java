/* Queue.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */
 
import java.util.Vector;
 
/**
 * Wrapper for the Vector class to make them easier to deal with in a sequential manner.
 */
public class Queue {
	/**
	 * v: the vector being wrapped as a Queue
	 */
	private Vector v;
	
	/** 
	 * Creates a new queue around a vector object
	 */
	public Queue() {
		v = new Vector();
	}

	/**
	 * Retrieves the next object sequentially in the Queue.
	 * @return the object directly after the current object
	 */
	public Object next() {
		return v.remove(0);
	}

	/**
	 * Adds a new object to the Queue
	 */
	public void add(Object o) {
		v.addElement(o);
	}
	
	/**
	 * Checks whether the Queue has any more elements left in it.
	 * @return true if the Queue has elements, false if it is empty.
	 */
	public boolean hasMoreElements() {
		return v.size() != 0;
	}

	/**
	 * Returns the Queue as a Vector.
	 * @return the unwrapped Vector beneath the Queue implementation.
	 */
	public Vector asVector() {
		return v;
	}
	
}
