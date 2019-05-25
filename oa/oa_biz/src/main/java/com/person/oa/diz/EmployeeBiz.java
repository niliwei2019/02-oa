package com.person.oa.diz;

import com.person.oa.entity.Department;
import com.person.oa.entity.Employee;

import java.util.List;

public interface EmployeeBiz {
    void  add(Employee employee);
    void edit(Employee employeet);
    void remove(String id);
    Employee get(String sn);
    List<Employee> getAll();
}
