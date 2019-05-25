package com.person.oa.diz.impl;

import com.person.oa.dao.ClaimVoucherDao;
import com.person.oa.dao.ClaimVoucherItemDao;
import com.person.oa.dao.DealRecordDao;
import com.person.oa.dao.EmployeeDao;
import com.person.oa.diz.ClaimVoucherBiz;
import com.person.oa.entity.ClaimVoucher;
import com.person.oa.entity.ClaimVoucherItem;
import com.person.oa.entity.DealRecord;
import com.person.oa.entity.Employee;
import com.person.oa.global.Constant;
import com.sun.tools.internal.jxc.ap.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DealRecordDao recordDao;

    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_SUBMIT);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
//        System.out.println("deparement:" + employee.getDepartmentSn());
        List<Employee> employees = employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Constant.POST_DM);
        System.out.println("employee:" + employees.get(0).getSn());
        claimVoucher.setNextDealSn(employees.get(0).getSn());
        //最后update数据库信息
        claimVoucherDao.update(claimVoucher);


        //处理record类
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Constant.DEAL_SUBMIT);
        dealRecord.setDealWay(employee.getSn());
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setDealResult(Constant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        recordDao.insert(dealRecord);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item : items) {
                if (item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                claimVoucherItemDao.delete(old.getId());
            }
        }
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId() != null) {
                claimVoucherItemDao.update(item);
            } else {
                claimVoucherItemDao.insert(item);
            }
        }
    }

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItems(int id) {
        return claimVoucherItemDao.selectByClaimVoucher(id);
    }

    public List<DealRecord> getRecords(int id) {
        return dealRecordDao.selectByClaimVoucher(id);
    }


    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());

        if (dealRecord.getDealWay().equals(Constant.DEAL_PASS)) {
            if (claimVoucher.getTotalAmount() <= Constant.LIMIT_CHECK || employee.getPost().equals(Constant.POST_GM)) {
                claimVoucher.setStatus(Constant.CLAIMVOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Constant.POST_CASHIER).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIMVOUCHER_APPROVED);
            } else {
                claimVoucher.setStatus(Constant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Constant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIMVOUCHER_RECHECK);
            }
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_BACK)) {
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_BACK);
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_REJECT)) {
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_TERMINATED);
        } else if (dealRecord.getDealWay().equals(Constant.DEAL_PAID)) {
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }
}

