// A simple linked list implementation



public class LinkedList  {

	private ListNode head;
	private ListNode tail;
	private int size;

	public LinkedList() {
	}
	
	//Add value to linked list
	public void add(Object value) {
		ListNode n = new ListNode(null, value);
		if (tail == null) {
			head = tail = n;
		} else {
			tail.setNext(n);
		}
		tail = n;
		size++;
	}
	
	//Clear list
	public void clear() {
		head = tail = null;
		size = 0;
	}
	
	// Return true if empty
	public boolean isEmpty() {
		return size() == 0;
	}

	//Return size
	public int size() {
		return size;
	}
	
	//return object in index
	public Object get(int index) {
		Object result = null;
		if (index == 0 && head != null) {
			result = head.getValue();
		} else {
			ListNode previous = getNodeBefore(index);
			if (previous != null && previous.getNext() != null) {
				result = previous.getNext().getValue();
			}
		}
		return result;
	}
	
	private ListNode getNodeBefore(int position) {
		ListNode result = null;
		if (position > 0) {
			int count = 0;
			ListNode current = head;
			while (count < (position - 1) && current != null) {
				current = current.getNext();
				count++;
			}
			result = current;
		}
		return result;
	}

	public void print() {
		ListNode current = head;
		System.out.print("head => ");
		while (current != null) {
			System.out.print(current.getValue());
			current = current.getNext();
			System.out.print(" => ");
		}
		System.out.println();
	}

	private class ListNode {
		private Object value;
		private ListNode next;

		public ListNode(ListNode next, Object value) {
			this.next = next;
			this.value = value;
		}

		public ListNode getNext() {
			return next;
		}

		public void setNext(ListNode next) {
			this.next = next;
		}

		public Object getValue() {
			return value;
		}
	}



}
