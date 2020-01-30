package com.imooc.oa.biz;

import com.imooc.oa.entity.Employee;

public interface GlobalBiz {
    //声明这里面的方法

    Employee login(String sn,String  password);
    //通常不会再业务层对session进行操作
    //修改密码
    void changePassword(Employee employee);
}
