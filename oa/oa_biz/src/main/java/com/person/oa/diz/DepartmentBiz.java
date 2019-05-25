package com.person.oa.diz;

import com.person.oa.entity.Department;

import java.util.List;

public interface DepartmentBiz {
    void  add(Department department);
    void edit(Department department);
    void remove(String id);
    Department get(String sn);
    List<Department> getAll();
}
