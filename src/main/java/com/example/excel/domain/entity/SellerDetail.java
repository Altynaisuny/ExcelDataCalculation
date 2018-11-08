package com.example.excel.domain.entity;

import java.util.List;

/**
 * 销售商
 */
public class SellerDetail {
    /**
     * 名称
     */
    private String sellerName;

    private List<SupplyDetail> SupplyDetails;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<SupplyDetail> getSupplyDetails() {
        return SupplyDetails;
    }

    public void setSupplyDetails(List<SupplyDetail> SupplyDetails) {
        this.SupplyDetails = SupplyDetails;
    }
}
