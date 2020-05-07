
import java.util.Comparator;

public class PointComparatorByY implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof Point)||!(o2 instanceof Point) )
			throw new ClassCastException();
		
		return ((Point)o1).getY() - ((Point)o2).getY();
	}

}
