package moe.cnkirito.sleuth.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xujingfeng on 2017/11/7.
 */
public class MainOrder {

    private String orderId;
    private BigDecimal totalFare;
    private Date createDate;

    public MainOrder() {
    }

    public MainOrder(String orderId, BigDecimal totalFare, Date createDate) {
        this.orderId = orderId;
        this.totalFare = totalFare;
        this.createDate = createDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
