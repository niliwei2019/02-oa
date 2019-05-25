package com.person.oa.controller;

import com.person.oa.diz.DepartmentBiz;
import com.person.oa.diz.EmployeeBiz;
import com.person.oa.entity.Department;
import com.person.oa.entity.Employee;
import com.person.oa.global.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller("employeeController")
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeBiz.getAll());
        return "employee_list";
    }
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("employee",new Employee());
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Constant.getPosts());
        return "employee_add";
    }
    @RequestMapping("/add")
    public String add(Employee employee){
        employeeBiz.add(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",employeeBiz.get(sn));
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Constant.getPosts());
        return "employee_update";
    }
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBiz.edit(employee);
        return "redirect:list";
    }
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeBiz.remove(sn);
        return "redirect:list";
    }
//    @Autowired
//    private EmployeeBiz employeeBiz;
//    @Autowired
//    private DepartmentBiz departmentBiz;
//
//    @RequestMapping("/list")
//    public String list(Map<String,Object> map){
//        map.put("list",employeeBiz.getAll());
//        return "employee_list";
//    }
//
//    @RequestMapping("/to_add")
//    public String toAdd(Map<String,Object> map){
//        map.put("employee",new Employee());
//        map.put("dlist",departmentBiz.getAll());
//        map.put("plist", Constant.getPosts());
//        return "employee_add";
//    }
//
//    @RequestMapping("/add")
//    public String add(Employee employee){
//        employeeBiz.add(employee);
////        return "department_list"; 直接跳转页面没有值
//        return "redirect:list";
//    }
//    @RequestMapping(value = "/to_update",params = "sn")
//    public String toUpdate(String sn,Map<String,Object> map){
//        map.put("employee",employeeBiz.get(sn));
//        map.put("dlist",departmentBiz.getAll());
//        map.put("plist", Constant.getPosts());
////        return "department_list"; 直接跳转页面没有值
//        return "employee_update";
//    }
//
//    @RequestMapping("/update")
//    public String update(Employee employee){
//        employeeBiz.edit(employee);
////        return "department_list"; 直接跳转页面没有值
//        return "redirect:list";
//    }
//
//    @RequestMapping(value = "/remove",params = "sn")
//    public String toUpdate(String sn){
//        employeeBiz.remove(sn);
////        return "department_list"; 直接跳转页面没有值
//        return "redirect:list";
//    }
}
