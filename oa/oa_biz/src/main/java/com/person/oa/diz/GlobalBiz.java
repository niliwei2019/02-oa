package com.person.oa.diz;

import com.person.oa.entity.Employee;

public interface GlobalBiz {


    Employee login(String sn,String password);
    void changePassword(Employee employee);
}
