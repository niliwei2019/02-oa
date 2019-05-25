package com.person.oa.diz.impl;

import com.person.oa.dao.EmployeeDao;
import com.person.oa.diz.EmployeeBiz;
import com.person.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("employeeBiz")
public class EmployeeBizImpl implements EmployeeBiz {
    @Autowired
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        employee.setPassword("000000");
        employeeDao.insert(employee);
    }

    public void edit(Employee employeet) {
        employeeDao.update(employeet);
    }

    public void remove(String id) {
        employeeDao.delete(id);
    }

    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
