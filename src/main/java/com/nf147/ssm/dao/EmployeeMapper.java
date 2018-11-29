package com.nf147.ssm.dao;

import com.nf147.ssm.entity.Employee;
import java.util.List;

public interface EmployeeMapper {

    int deleteByPrimaryKey(Integer employeeId);

    int insert(Employee record);

    int insertAll(List<Employee> employees);

    Employee selectByPrimaryKey(Integer employeeId);

    Employee selectByNum(String employeeNum);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);
}