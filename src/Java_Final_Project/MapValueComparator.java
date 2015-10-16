package Java_Final_Project;

import java.util.Comparator;
import java.util.Map;

public class MapValueComparator implements Comparator<Integer>{
	private Map<Integer,Integer> mapToSort;
	
	//Parameterized constructor
	public MapValueComparator(Map<Integer,Integer> mapToSort) {
		this.mapToSort = mapToSort;
	}

	
	// Sort by values
	@Override
    public int compare(Integer x, Integer y)
    {
        // Assume neither Integer is null. Real code should
        // probably be more robust
        if (mapToSort.get(x)< mapToSort.get(y))
        {
            return -1;
        }
        if (mapToSort.get(x) > mapToSort.get(y))
        {
            return 1;
        }
        return 0;
    }
	
//	sort by the keys
//	@Override
//    public int compare(Integer x, Integer y)
//    {
//        // Assume neither Integer is null. Real code should
//        // probably be more robust
//        if (x< y)
//        {
//            return -1;
//        }
//        if (x > y)
//        {
//            return 1;
//        }
//        return 0;
//    }
}
