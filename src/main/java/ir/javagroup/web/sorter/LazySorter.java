package ir.javagroup.web.sorter;

import ir.javagroup.domain.Employee;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * Created by jafar on 8/24/14.
 */
public class LazySorter implements Comparator<Employee> {
    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Employee employee1, Employee employee2) {
        try {
            Field f1 = employee1.getClass().getDeclaredField(sortField);
            f1.setAccessible(true);
            Object o1 = f1.get(employee1);
            Field f2 = employee2.getClass().getDeclaredField(sortField);
            f2.setAccessible(true);
            Object o2 = f1.get(employee2);
            int value = ((Comparable)o1).compareTo(o2);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
