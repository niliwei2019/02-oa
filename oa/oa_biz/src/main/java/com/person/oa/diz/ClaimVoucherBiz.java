package com.person.oa.diz;

import com.person.oa.entity.ClaimVoucher;
import com.person.oa.entity.ClaimVoucherItem;
import com.person.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    ClaimVoucher get(int id);
    List<ClaimVoucherItem> getItems(int id);
    List<DealRecord> getRecords(int id);
    List<ClaimVoucher> getForSelf(String sn);
    List<ClaimVoucher> getForDeal(String sn);
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    void submit(int id);
    void deal(DealRecord dealRecord);
}
