package com.example.excel.domain.entity;

/**
 * 供应商
 */
public class SupplyDetail {
    /**
     * 供应商品编号
     */
    private String goodsNo;
    /**
     * 供应商品列名
     */
    private String goodsColumn;
    /**
     * 供应商品内容
     */
    private String goodsValue;

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsColumn() {
        return goodsColumn;
    }

    public void setGoodsColumn(String goodsColumn) {
        this.goodsColumn = goodsColumn;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }
}
