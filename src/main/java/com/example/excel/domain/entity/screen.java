package com.example.excel.domain.entity;

public class screen {
    /**
     * 供应商款号
     */
    private String id;
    /**
     * 实发数量
     */
    private String realSendNums;
    /**
     * 销售金额
     */
    private String salesMoney;
    /**
     * 当期实退数量
     */
    private String rejectGoodsNums;
    /**
     * 当期退货金额
     */
    private String rejectGoodsMoney;
    /**
     * 运费收入分摊
     */
    private String freightIncomeAverage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealSendNums() {
        return realSendNums;
    }

    public void setRealSendNums(String realSendNums) {
        this.realSendNums = realSendNums;
    }

    public String getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(String salesMoney) {
        this.salesMoney = salesMoney;
    }

    public String getRejectGoodsNums() {
        return rejectGoodsNums;
    }

    public void setRejectGoodsNums(String rejectGoodsNums) {
        this.rejectGoodsNums = rejectGoodsNums;
    }

    public String getRejectGoodsMoney() {
        return rejectGoodsMoney;
    }

    public void setRejectGoodsMoney(String rejectGoodsMoney) {
        this.rejectGoodsMoney = rejectGoodsMoney;
    }

    public String getFreightIncomeAverage() {
        return freightIncomeAverage;
    }

    public void setFreightIncomeAverage(String freightIncomeAverage) {
        this.freightIncomeAverage = freightIncomeAverage;
    }

    @Override
    public String toString() {
        return "screen{" +
                "id='" + id + '\'' +
                ", realSendNums='" + realSendNums + '\'' +
                ", salesMoney='" + salesMoney + '\'' +
                ", rejectGoodsNums='" + rejectGoodsNums + '\'' +
                ", rejectGoodsMoney='" + rejectGoodsMoney + '\'' +
                ", freightIncomeAverage='" + freightIncomeAverage + '\'' +
                '}';
    }
}
