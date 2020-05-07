
//Don't change the class name
public class Container {
	private Point data;// Don't delete or change this field;
	private Container next;
	private Container prev;
	private Container twinCon;

	public Container(Point data, Container next, Container prev, Container twinCon) {
		this.data = data;
		this.next = next;
		this.prev = prev;
		this.twinCon = twinCon;
	}

	public Container(Point data) {
		this(data, null, null, null);
	}

	public Container getNext() {
		return next;
	}

	public void setNext(Container next) {
		this.next = next;
	}

	public String toString() {
		return data.toString();
	}

	public Container getPrev() {
		return prev;
	}

	public void setPrev(Container prev) {
		this.prev = prev;
	}

	public Container getTwinCon() {
		return twinCon;
	}

	public void setTwinCon(Container twinCon) {
		this.twinCon = twinCon;
	}

	// Don't delete or change this function
	public Point getData() {
		return data;
	}
}
