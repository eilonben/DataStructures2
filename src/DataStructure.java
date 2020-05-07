import java.util.Arrays;
import java.util.Comparator;

public class DataStructure implements DT {

	public PointsAxisLinkedList yAxis; // Linked list sorted by y axis
	public PointsAxisLinkedList xAxis; // Linked list sorted by x Axis
	private Comparator xComparator; // Comparator by X value
	private Comparator yComparator; // Comparator by Y value

	public DataStructure() { // Constructor
		xComparator = (PointComparatorByX) new PointComparatorByX();
		yComparator = (PointComparatorByY) new PointComparatorByY();
		yAxis = new PointsAxisLinkedList(yComparator);
		xAxis = new PointsAxisLinkedList(xComparator);
	}

	@Override
	public void addPoint(Point point) { // Adds a point to the data structure and updates the "twin container" field
		Container y = yAxis.add(point);
		Container x = xAxis.add(point);
		y.setTwinCon(x);
		x.setTwinCon(y);
	}

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) { // Returns an of array of points that their axis value is between min and max

		if (axis) {
			Container curr = xAxis.getFirst();
			Point[] tmpArray = new Point[xAxis.getSize() + 1];
			while (curr != null && ((curr.getData().getX()) < min)) {
				curr = curr.getNext();
			}
			int i = 0, counter = 0;
			while (curr != null && ((curr.getData().getX()) <= max)) {
				tmpArray[i] =  curr.getData();
				counter++;
				i++;
				curr = curr.getNext();
			}
			Point[] pArray = new Point[counter];
			for (i = 0; tmpArray[i] != null && i < tmpArray.length; i++) {
				pArray[i] = tmpArray[i];
			}
			return pArray;
		}
		Container curr = yAxis.getFirst();
		Point[] tmpArray = new Point[yAxis.getSize() + 1];
		while (curr != null && ((curr.getData().getY()) < min)) {
			curr = curr.getNext();
		}
		int i = 0, counter = 0;
		while (curr != null && ((curr.getData().getY()) <= max)) {
			tmpArray[i] =  curr.getData();
			counter++;
			i++;
			curr = curr.getNext();
		}
		Point[] pArray = new Point[counter];
		for (i = 0; tmpArray[i] != null && i < tmpArray.length; i++) {
			pArray[i] = tmpArray[i];
		}
		return pArray;
	}

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {// this function returns an array of points sorted by !axis, that their axis value is between min and max 
		if (axis) {
			Container curr = yAxis.getFirst();
			Point[] tmpArray = new Point[xAxis.getSize() + 1];
			int counter = 0;
			int i = 0;
			while (curr != null && i < tmpArray.length) {
				if (((Point) curr.getData()).getX() >= min && (curr.getData().getX() <= max)) {
					tmpArray[i] = curr.getData();
					counter++;
					i++;
				}
				curr = curr.getNext();
			}
			Point[] pArray = new Point[counter];
			for (i = 0; tmpArray[i] != null && i < tmpArray.length; i++) {
				pArray[i] = tmpArray[i];
			}
			return pArray;
		}
		Container curr = xAxis.first;
		Point[] tmpArray = new Point[xAxis.getSize() + 1];
		int counter = 0;
		int i = 0;
		while (curr != null && i < tmpArray.length) {
			if (((Point) curr.getData()).getY() >= min && ( curr.getData().getY() <= max)) {
				tmpArray[i] = curr.getData();
				counter++;
				i++;
			}
			curr = curr.getNext();
		}
		Point[] pArray = new Point[counter];
		for (i = 0; tmpArray[i] != null && i < tmpArray.length; i++) {
			pArray[i] = tmpArray[i];
		}
		return pArray;
	}

	@Override
	public double getDensity() {// returning size/(max X - min X)(max Y - min Y)
		int n = xAxis.getSize();
		int maxX = xAxis.getLast().getData().getX();
		int maxY = yAxis.getLast().getData().getY();
		int minX = xAxis.getFirst().getData().getX();
		int minY = yAxis.getFirst().getData().getY();
		return ((n) / ((Math.abs(maxX - minX)) * (Math.abs(maxY - minY))));

	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {// deleting every container in the dataStructure that has a lower axis value than min or a higher axis value than max
		if (axis) {
			Container startCurr = xAxis.getFirst();
			Container endCurr = xAxis.getLast();
			while (startCurr != null && startCurr.getData().getX() < min) {
				yAxis.remove(startCurr.getTwinCon());
				if (startCurr.getNext() == null) {
					xAxis.remove(startCurr);
					return;
				}
				startCurr = startCurr.getNext();
				xAxis.remove(startCurr.getPrev());
			}
			while (endCurr != null && endCurr.getData().getX() > max) {
				yAxis.remove(endCurr.getTwinCon());
				if (endCurr.getPrev() == null) {
					xAxis.remove(endCurr);
					return;
				}
				endCurr = endCurr.getPrev();
				xAxis.remove(endCurr.getNext());
			}
			return;
		}
		Container startCurr = yAxis.getFirst();
		Container endCurr = yAxis.getLast();
		while (startCurr != null && startCurr.getData().getY() < min) {
			xAxis.remove(startCurr.getTwinCon());
			if (startCurr.getNext() == null) {
				yAxis.remove(startCurr);
				return;
			}
			startCurr = startCurr.getNext();
			yAxis.remove(startCurr.getPrev());
		}
		while (endCurr != null && endCurr.getData().getY() > max) {
			xAxis.remove(endCurr.getTwinCon());
			if (endCurr.getPrev() == null) {
				yAxis.remove(endCurr);
				return;
			}
			endCurr = endCurr.getPrev();
			yAxis.remove(endCurr.getNext());
		}
		return;
	}

	@Override
	public Boolean getLargestAxis() {// returning (max X - min X) > (max Y - min Y)   (boolean value)

		return (xAxis.getLast().getData().getX())
				- (xAxis.getFirst().getData().getX()) > (yAxis.getLast().getData().getY())
						- (yAxis.getFirst().getData().getY());
	}

	@Override
	public Container getMedian(Boolean axis) {// returning the container located at index size/2 in the given axis
		if (axis) {
			Container curr = xAxis.getFirst();
			for (int i = 0; i < xAxis.getSize() / 2; i++) {
				curr = curr.getNext();
			}
			return curr;
		}
		Container curr = yAxis.getFirst();
		for (int i = 0; i < yAxis.getSize() / 2; i++) {
			curr = curr.getNext();
		}
		return curr;
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis) {// finding the closest two points in a given strip 
		Point[] checkStrip = new Point[xAxis.getSize() + 1];
		Container next = container.getNext();
		Container prev = container;
		if (axis) {
			int i = 0;
			while (next != null && next.getData().getX() <= (container.getData().getX() + (width / 2))) {
				checkStrip[i] = next.getData();
				next = next.getNext();
				i++;
			}
			while (prev != null && prev.getData().getX() >= container.getData().getX() - (width / 2)) {
				checkStrip[i] = prev.getData();
				prev = prev.getPrev();
				i++;
			}
			if (xAxis.getSize() < i * Math.log(i)) {
				checkStrip = getPointsInRangeOppAxis(container.getData().getX() - (int)width / 2,
						container.getData().getX() + (int)width / 2, axis);
				return findClosestPair(checkStrip, (int)width, axis);
			}

			Point[] newCheckStrip = new Point[i];
			for (int j = 0; j < i; j++)
				newCheckStrip[j] = checkStrip[j];
			Arrays.sort(newCheckStrip, yComparator);
			return findClosestPair(newCheckStrip, width, axis);
		}
		int i = 0;
		while (next != null && next.getData().getX() <= container.getData().getX() + width / 2) {
			checkStrip[i] = next.getData();
			next = next.getNext();
			i++;
		}
		while (prev != null && prev.getData().getX() >= container.getData().getX() - width / 2) {
			checkStrip[i] = prev.getData();
			prev = prev.getPrev();
			i++;
		}
		if (xAxis.getSize() < i * Math.log(i)) {
			checkStrip = getPointsInRangeOppAxis(container.getData().getX() - (int)width / 2,
					container.getData().getX() + (int)width / 2, axis);
			return findClosestPair(checkStrip, width, axis);
		}

		Point[] newCheckStrip = new Point[i];
		for (int j = 0; j < i; j++)
			newCheckStrip[j] = checkStrip[j];
		Arrays.sort(newCheckStrip, yComparator);
		return findClosestPair(newCheckStrip, width, axis);
	}

	@Override
	public Point[] nearestPair() {// finds the closest 2 points in the database
		Point[] output = new Point[2];
		if (xAxis.size <= 1) // If there are less than 2 points in the  DataStructure
			return output;
		if (xAxis.size == 2) { // If there are exactly 2 points in the DataStructure returns an array with the points
			output[0] = xAxis.getFirst().getData();
			output[1] = xAxis.getLast().getData();
			return output;
		}
		if (xAxis.size == 3) { // If there are 3 points - we find the minimum
								// distance between 3 points by using the function findClosestPair.
			double minDist = Double.MAX_VALUE;
			Point[] strip = new Point[3];
			Container curr = xAxis.getFirst();
			for (int i = 0; i < 3; i++) {
				strip[i] = curr.getData();
				curr = curr.getNext();
			}
			return findClosestPair(strip, minDist, true);
		}

		boolean axis = getLargestAxis();
		if (axis) {
			Container median = getMedian(axis);
			DataStructure left = (split(median.getData().getX(), axis))[0];//splitting the DT to two data structure by the median of the largest axis
			DataStructure right = (split(median.getData().getX(), axis))[1];
			Point[] leftPair = left.nearestPair();//getting the 2 points with the smallest distance in the left half recursively
			Point[] rightPair = right.nearestPair();//getting the 2 points with the smallest distance in the right half recursively
			double minDistL = findDistance(leftPair[0], leftPair[1]);
			double minDistR = findDistance(rightPair[0], rightPair[1]);
			double width = minDistL;
			if (minDistL < minDistR)// checking which side has a smaller minimum distance
				output = leftPair;
			else {
				output = rightPair;
				width = minDistR;
			}
			Point[] stripCheck = nearestPairInStrip(median, (int) width * 2 + 1, axis);// checking the strip between the DT halves
			if ((stripCheck[0] != null) && findDistance(stripCheck[0], stripCheck[1]) < width)
				return stripCheck;
			return output;

		}
		Container median = getMedian(axis);
		DataStructure left = split(median.getData().getY(), axis)[0];
		DataStructure right = split(median.getData().getY(), axis)[1];
		Point[] leftPair = left.nearestPair();
		Point[] rightPair = right.nearestPair();
		double minDistL = findDistance(leftPair[0], leftPair[1]);
		double minDistR = findDistance(rightPair[0], rightPair[1]);
		double width = minDistL;
		if (minDistL < minDistR)
			output = leftPair;
		else {
			output = rightPair;
			width = minDistR;
		}
		Point[] stripCheck = nearestPairInStrip(median, width * 2 + 1, axis);
		if ((stripCheck[0] != null) && findDistance(stripCheck[0], stripCheck[1]) < width)
			return stripCheck;
		return output;

	}

	private DataStructure[] split(int median, Boolean axis) {// splitting the DT to two DT's by the median value of axis, and returning an array with them
		DataStructure left = new DataStructure();
		DataStructure right = new DataStructure();
		if (axis) {
			Point[] leftArrReg = getPointsInRangeRegAxis(xAxis.getFirst().getData().getX(), median, axis);
			Point[] leftArrOpp = getPointsInRangeOppAxis(xAxis.getFirst().getData().getX(), median, axis);
			Point[] rightArrReg = getPointsInRangeRegAxis(median, xAxis.getLast().getData().getX(), axis);
			Point[] rightArrOpp = getPointsInRangeOppAxis(median, xAxis.getLast().getData().getX(), axis);
			for (int i = 0; i < leftArrOpp.length; i++) {
				left.xAxis.AddLast(leftArrReg[i]);
				left.yAxis.AddLast(leftArrOpp[i]);
			}
			for (int i = 0; i < rightArrOpp.length; i++) {
				right.xAxis.AddLast(rightArrReg[i]);
				right.yAxis.AddLast(rightArrOpp[i]);
			}
		} else {
			Point[] leftArrReg = getPointsInRangeRegAxis(yAxis.getFirst().getData().getY(), median, axis);
			Point[] leftArrOpp = getPointsInRangeOppAxis(yAxis.getFirst().getData().getY(), median, axis);
			Point[] rightArrReg = getPointsInRangeRegAxis(median, yAxis.getLast().getData().getY(), axis);
			Point[] rightArrOpp = getPointsInRangeOppAxis(median, yAxis.getLast().getData().getY(), axis);
			for (int i = 0; i < leftArrOpp.length; i++) {
				left.yAxis.AddLast(leftArrReg[i]);
				left.xAxis.AddLast(leftArrOpp[i]);
			}
			for (int i = 0; i < rightArrOpp.length; i++) {
				right.yAxis.AddLast(rightArrReg[i]);
				right.xAxis.AddLast(rightArrOpp[i]);
			}
		}
		DataStructure[] output = new DataStructure[2];
		output[0] = left;
		output[1] = right;
		return output;

	}

	public void PrintAxis(Boolean axis) {
		if (axis) {
			System.out.println(xAxis);
			return;
		}
		System.out.println(yAxis);
	}

	private double findDistance(Point p1, Point p2) {// a function to calculate distance between two points
		return Math.sqrt(
				(p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
	}

	private Point[] findClosestPair(Point[] strip, double minD, Boolean axis) {// a function to find the pair of points with the lowest distance in a given array. if there is no pair with distance smaller than minD, the function returns an empty array
		Point[] output = new Point[2];
		if (strip.length < 2)
			return output;
		double newMin = minD;
		if (axis) {
			for (int i = 1; i < strip.length; i++)
				for (int j = i-1; j < strip.length && (strip[j].getY() - strip[i].getY() < newMin); j++)
					if (findDistance(strip[j], strip[i]) < newMin) {
						output[0] = strip[j];
						output[1] = strip[i];
						newMin = findDistance(strip[j], strip[i]);
					}
			return output;
		}

		for (int i = 1; i < strip.length; i++) {
			for (int j = i - 1; j < strip.length && (strip[j].getX() - strip[i].getX() < newMin); j++)
				if (findDistance(strip[j], strip[i]) < newMin) {
					output[0] = strip[j];
					output[1] = strip[i];
					newMin = findDistance(strip[j], strip[i]);
				}
		}
		return output;

	}
}
