package taghipour.dao;

import taghipour.domain.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by jafar on 9/18/14.
 */
public class EmployeeDao {

    private static SessionFactory factory;

    public EmployeeDao() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Employees load lazily
     * @param firstResult
     * @param maxResults
     * @return Employees with specific rows count and start with specific number
     */
    public List<Employee> getEmployeeListLazily(int firstResult, int maxResults){
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Employee> employeeList = null;
        try{
            transaction = session.beginTransaction();
            employeeList = session.createQuery("from Employee").setFirstResult(firstResult).setMaxResults(maxResults).list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeList;
    }

    /**
     *
     * @return Size of all employees
     */
    public int getEmployeeListSize(){
        Session session = factory.openSession();
        Transaction transaction = null;
        int size = 0;
        try{
            transaction = session.beginTransaction();
            size = ((Long) session.createQuery("select count(*) from Employee").uniqueResult()).intValue();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return size;
    }


}
