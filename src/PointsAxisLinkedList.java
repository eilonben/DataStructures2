import java.util.Comparator;

public class PointsAxisLinkedList { 
	protected Container first; // first link (minimum in list)
	protected Container last; // last link (maximum in list)
	protected int size; // size of list
	protected Comparator myComparator;

	public PointsAxisLinkedList(Comparator myComparator) { // constructor
		this.myComparator = myComparator;
		first = null;
		last = null;
		size = 0;
	}

	public Container add(Point toAdd) { // adds a given object to the list
		if (!(toAdd instanceof Point))
			throw new ClassCastException();
		toAdd = (Point) toAdd;
		Container addLink = new Container(toAdd);
		if (first == null) { // if the list is empty
			first = addLink;
			last = addLink;
			size++;
			return first;
		}
		if (myComparator.compare(toAdd, first.getData()) < 0) { // if the object toAdd is smaller than the minimum
			first.setPrev(addLink);
			addLink.setNext(first);
			first = addLink;
			size++;
			return first;
		}
		if (myComparator.compare(toAdd, last.getData()) > 0) { // if the object toAdd is bigger than the maximum
			addLink.setPrev(last);
			last.setNext(addLink);
			last = addLink;
			size++;
			return last;
		}
		Container curr = first.getNext();
		Container prev = first;
		while (myComparator.compare(toAdd, curr.getData()) >= 0) { // finds the exact position of adding toAdd 
			prev = curr;
			curr = curr.getNext();
		}
		addLink.setPrev(prev);
		prev.setNext(addLink);
		addLink.setNext(curr);
		curr.setPrev(addLink);
		size++;
		return addLink;
	}
	public void remove(Container toRemove){
		if (toRemove==null)
			throw new NullPointerException();
		if (toRemove==first){
			first=toRemove.getNext();
			if (first!=null)
				first.setPrev(null);
			size--;
			return;
		}
		if (toRemove==last){
			last=toRemove.getPrev();
			if (last!=null)
				last.setNext(null);
			size--;
			return;
		}
		toRemove.getPrev().setNext(toRemove.getNext());
		toRemove.getNext().setPrev(toRemove.getPrev());
		size--;
			
	}
	public void AddLast(Point data){
		Container toAdd = new Container(data);
		if (first==null){
			first=toAdd;
			last=toAdd;
			size++;
			return;
		}
		toAdd.setPrev(last);
		last.setNext(toAdd);
		last=toAdd;
		size++;
		
	}
	

	public void setFirst(Container first) {
		this.first = first;
		this.first.setPrev(null);
	}

	public void setLast(Container last) {
		this.last = last;
		this.last.setNext(null);
	}

	public Container getFirst() {
		return first;
	}

	public Container getLast() {
		return last;
	}

	public int getSize() {
		return size;
	}
	public String toString() { 
		if (first==null)
			return "empty";
		Container curr = first;
		String str = "The list Contains: { ";
		while (curr!=null){
			str+=curr.getData()+" , ";
			curr=curr.getNext();
		}
		str=str.substring(0,str.length()-3);
		str+= " }";
		return str;
	}
}
