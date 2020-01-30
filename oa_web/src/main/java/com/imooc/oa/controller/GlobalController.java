package com.imooc.oa.controller;

import com.imooc.oa.biz.GlobalBiz;
import com.imooc.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller("globalController")
public class GlobalController {
    //登录需要打开登录界面，
    @Autowired
    private GlobalBiz globalBiz;


    @RequestMapping("/to_login")
    public String toLogin() {  //去登录界面， 考虑参数问题  不需要传入数据
        //使用SPring的表单的话需要使用modelAndView
        return "login"; // 页面就是login.jsp
    }
    //然后调用服务层进行业务判断，
    //登录完成，失败之后跳转页面

    @RequestMapping("/login")
    public String login(HttpSession session,@RequestParam String sn, @RequestParam String password) {  //接收多个参数
        Employee employee = globalBiz.login(sn, password);
        if (employee == null) {
            return "redirect:to_login";
        }
        //登录成功的话就保存在session中
        session.setAttribute("employee", employee);
        //保存成功重定向到个人页面的地址
        return "redirect:self";
    }

    @RequestMapping("/self")
    public String self() {
        return "self"; //页面的名称为self.jsp
    }

    //编写退出功能  退出，session状态删除
    @RequestMapping("/quit")
    public String login(HttpSession session) {
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    //编写修改密码，，分为两部，跳转到修改密码的页面，还有修改密码的实现




    @RequestMapping("/to_change_password")
    public String toChangePassword() {  //去登录界面， 考虑参数问题  不需要传入数据
        //使用SPring的表单的话需要使用modelAndView
        return "change_password"; // 页面就是login.jsp
    }
    //然后调用服务层进行业务判断，
    //登录完成，失败之后跳转页面

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session,@RequestParam String old,
                                 @RequestParam String new1,@RequestParam String new2) {  //接收多个参数
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(old)){
            //原始密码正确，允许修改

            if (new1.equals(new2)){
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";

    }
}
