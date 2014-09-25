package ir.javagroup.web.domain;

import ir.javagroup.dao.EmployeeDao;
import ir.javagroup.domain.Employee;
import ir.javagroup.web.sorter.LazySorter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by jafar on 8/15/14.
 */
public class EmployeeLazyDataModel extends LazyDataModel<Employee> {
    private List<Employee> datasourse;

    public List<Employee> getDatasourse() {
        return datasourse;
    }

    public void setDatasourse(List<Employee> datasourse) {
        this.datasourse = datasourse;
    }

    @Override
    public Employee getRowData(String rowKey) {
        for (Employee employee : datasourse) {
            if (employee.getId().equals(rowKey)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Employee object) {
        return object.getId();
    }

    @Override
    public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Employee> data = new ArrayList<Employee>();
        EmployeeDao employeeDao = new EmployeeDao();
        datasourse = employeeDao.getEmployeeListLazily(first, pageSize);
        for (Employee employee : datasourse) {
            boolean match = true;
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);
                    try {
                        Field field = employee.getClass().getDeclaredField(filterProperty);
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(employee));
                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        match = false;
                    }
                }
            }
            if (match) {
                data.add(employee);
            }
        }
        //Sort
        if (sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
        int employeeListSize = employeeDao.getEmployeeListSize();
        //Row count
        setRowCount(employeeListSize);
        //Paginate
        if (data.size() > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (Exception e) {
                return data.subList(first, first + (data.size() % pageSize));
            }
        } else {
            return data;
        }
    }
}
