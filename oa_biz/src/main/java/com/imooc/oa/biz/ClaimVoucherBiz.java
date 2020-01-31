package com.imooc.oa.biz;

import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;
import com.imooc.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    //报销单相关操作的业务层接口

    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);  //保存报销单的接口方法声明

    ClaimVoucher get(int id);
    List<ClaimVoucherItem> getItems(int cvid);
    List<DealRecord> getRecords(int cvid);

    List<ClaimVoucher> getForSelf(String sn);
    List<ClaimVoucher> getForDeal(String sn);

    //修改订单信息方法声明
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //提交
    void submit(int id);
    void deal(DealRecord dealRecord);

}
