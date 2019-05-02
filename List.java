// Matthew Halteh
// 5/14/18
// PA3

public class List {
	private class Node {
		private Object value;
		private Node previous;
		private Node next;

		private Node(Object value) {
			this.value = value;
			this.previous = null;
			this.next = null;
		}

		public boolean equals(Object compareTo) {
			if (! (compareTo instanceof Node)) {
				return false;
			}

			return this.value.equals(((Node)compareTo).value);
		}

		public String toString() {
			return this.value.toString();
		}
	}

	private Node front;
	private Node back;
	private Node current;
	private int	index;
	private int	length;

	public List() {
		this.front = null;
		this.back = null;
		this.current = null;
		this.index = -1;
		this.length = 0;
	}

	public List(Object value) {
		Node newNode = new Node(value);
		this.front = newNode;
		this.back = newNode;
		this.index = -1;
		this.length = 1;
	}

	public void clear() {
		this.front = null;
		this.back = null;
		this.current = null;
		this.index = -1;
		this.length = 0;
	}

	public int length() {
		return this.length;
	}

	public int index() {
		return this.index;
	}

	public Object front() {
		if (this.length == 0) {
			return null;
		}

		return this.front.value;
	}

	public Object back() {
		if (this.length == 0) {
			return null;
		}

		return this.back.value;
	}

	public Object get() {
		if (this.current == null) {
			return null;
		}

		return this.current.value;
	}

	public void prepend(Object value) {
		Node newNode = new Node(value);

		if (this.length == 0) {
			this.front = newNode;
			this.back  = newNode;
		}

		else {
			newNode.next = this.front;
			this.front.previous = newNode;
			this.front = newNode;
			if (this.current != null) {
				this.index ++;
			}
		}

		this.length ++;
	}

	public void insertBefore(Object value) {
		if (this.current == null) {
			throw new RuntimeException("Current is null: insertBeforeCurrent()");
		}

		Node newNode = new Node(value);
		newNode.previous = this.current.previous;
		newNode.next = this.current;
		if (this.front == this.current) {
			this.front = newNode;
		}

		else {
			newNode.previous.next = newNode;
		}

		this.current.previous = newNode;
		this.index ++;
		this.length ++;
	}

	public void append(Object value) {
		Node newNode = new Node(value);

		if (this.length == 0) {
			this.front = newNode;
			this.back  = newNode;
		}

		else {
			newNode.previous = this.back;
			this.back.next = newNode;
			this.back = newNode;
		}

		this.length ++;
	}

	public void insertAfter(Object value) {
		if (this.current == null) {
			throw new RuntimeException("Current is null: insertAfterCurrent()");
		}

		Node newNode = new Node(value);
		newNode.previous = this.current;
		newNode.next = this.current.next;
		if (this.back == this.current) {
			this.back = newNode;
		}

		else {
			this.current.next.previous = newNode;
		}

		this.current.next = newNode;
		this.length ++;
	}

	public void replaceCurrent(Object value) {
		if (this.current == null) {
			throw new RuntimeException("Current is null: replaceCurrent()");
		}

		current.value = value;
	}

	public void moveFront() {
		this.current = this.front;
		this.index   = 0;
	}

	public void moveBack() {
		this.current = this.back;
		this.index = this.length - 1;
	}

	public void movePrev() {
		if (this.current == null) {
			throw new RuntimeException("Current is null: movePrev()");
		}

		this.current = this.current.previous;
		this.index --;
	}

	public void moveNext() {
		if (this.current == null) {
			throw new RuntimeException("Current is null: moveNext()");
		}

		this.current = this.current.next;

		if (this.current == null) {
			this.index = -1;
		}

		else {
			this.index ++;
		}
	}

	public void deleteFront() {
		if (this.length == 0) {
			throw new RuntimeException("List is empty: deleteFront()");
		}

		if (this.current == this.front) {
			this.current = null;
		}

		this.front = this.front.next;

		if (this.front != null) {
			this.front.previous = null;
		}

		this.index --;
		this.length --;
	}

	public void deleteBack() {
		if (this.length == 0) {
			throw new RuntimeException("List is empty: deleteBack()");
		}

		if (this.current == this.back) {
			this.current = null;
			this.index   = -1;
		}

		this.back = this.back.previous;

		if (this.back != null) {
			this.back.next = null;
		}

		this.length --;
	}

	public void delete() {
		if (this.length == 0 || this.current == null) {
			throw new RuntimeException("Current is null: delete()");
		}

		if (this.current == this.front) {
			this.front = this.front.next;
			if (this.front != null) {
				this.front.previous = null;
			}
		}
		else
		if (this.current == this.back) {
			this.back = this.back.previous;
			if (this.back != null) {
				this.back.next = null;
			}
		}
		else {
			this.current.previous.next = this.current.next;
			this.current.next.previous = this.current.previous;
		}

		this.current = null;
		this.index = -1;
		this.length --;
	}

	public List copy() {
		List newList = new List();
		for (Node node=this.front; node!=null; node=node.next) {
			newList.append(node.value);
		}

		return newList;
	}

	public void print(String listName, String pfx) {
		System.out.println(listName);
		System.out.println("--------------------------------------");

		for (Node node=this.front; node!=null; node=node.next) {
			System.out.println(pfx + node.toString());
		}

		System.out.println("==============================================================================");
	}

	public boolean equals(Object compareTo) {
		if (! (compareTo instanceof List)) {
			return false;
		}

		if (this.front == ((List)compareTo).front) {
			return true;
		}

		if (this.front == null || ((List)compareTo).front == null) {
			return false;
		}

		if (this.length != ((List)compareTo).length) {
			return false;
		}

		Node thisNode = this.front;
		Node compareToNode = ((List)compareTo).front;
		for (; thisNode != null; thisNode=thisNode.next, compareToNode = compareToNode.next) {
			if (compareToNode == null) {
				return false;
			}

			if (! thisNode.equals(compareToNode)) {
				return false;
			}
		}

		return true;
	}

	public String toString() {
		if (this.length == 0) {
			return "";
		}

		String str = this.front.toString();
		for (Node node=this.front.next; node!=null; node=node.next) {
			str += " " + node.toString();
		}

		return str;
	}
}
