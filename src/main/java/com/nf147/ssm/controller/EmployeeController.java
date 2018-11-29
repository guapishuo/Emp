package com.nf147.ssm.controller;


import com.nf147.ssm.ExcelLogs;
import com.nf147.ssm.ExcelUtil;
import com.nf147.ssm.dao.EducationMapper;
import com.nf147.ssm.dao.EmployeeMapper;
import com.nf147.ssm.entity.Education;
import com.nf147.ssm.entity.Employee;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.faces.annotation.RequestParameterValuesMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EducationMapper educationMapper;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<Employee> employees = employeeMapper.selectAll();
        model.addAttribute("employees", employees);
        List<Education> educations = educationMapper.selectAll();
        model.addAttribute("educations", educations);
        return "index";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST ,produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String insertAll(@RequestBody List<Employee> employees) {
        for (Employee employee : employees) {
            Employee emp = employeeMapper.selectByNum(employee.getEmployeeNum());
            if (emp!=null){
                return "{\"msg\":\"你添加的员工中，已经有人入职了，看清楚点\"}";
            }
        }
        employeeMapper.insertAll(employees);
        System.out.println(employees);
        return "{\"msg\":\"成功\"}";
    }


    @RequestMapping(value = "/daochu", method = RequestMethod.GET)
    public ResponseEntity<byte[]> daochu() {
        List<Employee> list = employeeMapper.selectAll();
        byte[] bytes = null;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("第一页");

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("编号");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("姓名");
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("性别");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("学历");
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("月薪");


        for (int i = 1; i < list.size(); i++) {

            row = sheet.createRow(i);
            HSSFCell cell5 = row.createCell(0);
            cell5.setCellValue(list.get(i).getEmployeeNum());
            HSSFCell cell6 = row.createCell(1);
            cell6.setCellValue(list.get(i).getEmployeeName());
            HSSFCell cell7 = row.createCell(2);
            cell7.setCellValue(list.get(i).getEmployeeSex());
            HSSFCell cell8 = row.createCell(3);
            cell8.setCellValue(list.get(i).getEducation().getEducationName());
            HSSFCell cell9 = row.createCell(4);
            cell9.setCellValue(list.get(i).getEmployeePrice());
        }
        //FileOutputStream fout = null;
        try {
           Date date=new Date();
           SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
           String time=format.format(date);
           String filename = "D://demo.xls";
           // fout = new FileOutputStream(filename);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);
            bytes = stream.toByteArray();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(bytes.length);
        headers.setContentDispositionFormData("attachment", "demo" +  ".xls");

        return ResponseEntity.ok().headers(headers).body(bytes);
    }

}
