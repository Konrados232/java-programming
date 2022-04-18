package uj.java.w3;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ListMerger {
    public static List<Object> mergeLists(List<?> l1, List<?> l2) {

        List<Object> mergedList = new ArrayList<Object>();

        if ((l1 == null || l2 == null) || (l1.isEmpty() && l2.isEmpty())) {
            mergedList = Collections.unmodifiableList(mergedList);
            return mergedList;
        }

        int biggerListSize = Math.max(l1.size(), l2.size());

        for (int i = 0; i < biggerListSize; i++) {
            if (i < l1.size()) mergedList.add(l1.get(i));
            if (i < l2.size()) mergedList.add(l2.get(i));
        }

        mergedList = Collections.unmodifiableList(mergedList);
        return mergedList;
    }

}