package taghipour.web.domain;

import taghipour.domain.Employee;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by jafar on 8/15/14.
 */
@ManagedBean
@ViewScoped
public class LazyBean {
    private LazyDataModel<Employee> lazyModel;
    private Employee selectedEmployee;

    public LazyDataModel<Employee> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Employee> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    @PostConstruct
    public void init(){
        lazyModel = new EmployeeLazyDataModel();
    }
}
