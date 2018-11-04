package com.example.excel.domain.enumeration;

public enum orderScreenEnum {
    id(0, "供应商款号"),
    realSendNums(1, "实发数量"),
    salesMoney(2, "销售金额"),
    rejectGoodsNums(3, "当期实退数量"),
    rejectGoodsMoney(4, "当期退货金额"),
    costUnivalent(5, "成本单价"),
    salesCost(6, "销售成本"),
    freightIncomeAverage(7, "运费收入分摊"),
    profit(8, "利润"),
    ProfitMargin(9, "利润率"),
    ;

    /**
     * 节点代码
     */
    private Integer code;
    /**
     * 节点内容
     */
    private String text;

    private orderScreenEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
