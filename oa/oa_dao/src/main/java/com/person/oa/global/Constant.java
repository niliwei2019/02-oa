package com.person.oa.global;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String POST_STAFF="员工";
    public static final String POST_DM="部门经理";
    public static final String POST_GM="总经理";
    public static final String POST_CASHIER="财务";
    public static List<String> getPosts(){
        List<String> list=new ArrayList<String>();
        list.add(POST_CASHIER);
        list.add(POST_STAFF);
        list.add(POST_DM);
        list.add(POST_GM);
        return list;
    }

    public static List<String> getItems(){
        List<String> list=new ArrayList<String>();
        list.add("交通");
        list.add("餐饮");
        list.add("住宿");
        list.add("办公");
        return list;
    }

    public static final String CLAIMVOUCHER_CREATED="新创建";
    public static final String CLAIMVOUCHER_SUBMIT="已提交";
    public static final String CLAIMVOUCHER_APPROVED="已审核";
    public static final String CLAIMVOUCHER_BACK="已打回";
    public static final String CLAIMVOUCHER_TERMINATED="已终止";
    public static final String CLAIMVOUCHER_RECHECK="待复审";
    public static final String CLAIMVOUCHER_PAID="已打款";

    public static final double LIMIT_CHECK=5000.00;

    public static final String DEAL_CREATE="创建";
    public static final String DEAL_SUBMIT="提交";
    public static final String DEAL_UPDATE="修改";
    public static final String DEAL_BACK="打回";
    public static final String DEAL_REJECT="拒绝";
    public static final String DEAL_PASS="通过";
    public static final String DEAL_PAID="打款";
}
