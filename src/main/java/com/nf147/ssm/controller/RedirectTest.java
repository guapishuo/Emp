package com.nf147.ssm.controller;

import com.nf147.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RedirectTest {

    @RequestMapping("/aa")
    public String aa(RedirectAttributes attributes) {
        //addFlashAttribute不会值显示在url中
        //addAttribute 会在地址栏中显示你传输的值
        attributes.addFlashAttribute("aa", "aa");
        return "redirect:/bb";
    }

    @RequestMapping("/bb")
    public String bb(@ModelAttribute("aa") String aa, Model model) {
        System.out.println(aa);
        model.addAttribute("bb",aa);
        return "aa";
    }
}