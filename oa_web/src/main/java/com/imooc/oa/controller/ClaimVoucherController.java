package com.imooc.oa.controller;

import com.imooc.oa.biz.ClaimVoucherBiz;
import com.imooc.oa.dto.ClaimVoucherInfo;
import com.imooc.oa.entity.DealRecord;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Autowired
    private ClaimVoucherBiz claimVoucherBiz;

    //声明打开报销单的方法
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("items", Contant.getItems());
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }
    @RequestMapping("/add")
    public String add(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.save(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }
    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){ //id参数如果不使用注解标注的话需要与传入方一致
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItems(id));
        map.put("record",claimVoucherBiz.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/self")  //查看个人的报销单
    public String self(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    @RequestMapping("/deal")  //查看个人待处理报销单
    public String deal(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }



    //声明打开报销单的方法
    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(id));
        info.setItems(claimVoucherBiz.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }
    @RequestMapping("/update")
    public String update(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee) session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }


    //声明打开报销单的方法
    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItems(id));
        map.put("record",claimVoucherBiz.getRecords(id));
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }
    @RequestMapping("/check")
    public String check(HttpSession session,DealRecord dealRecord){
        Employee employee = (Employee) session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);
        return "redirect:deal";
    }


}
