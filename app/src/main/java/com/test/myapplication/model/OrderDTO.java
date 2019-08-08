package com.test.myapplication.model;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order")
public class OrderDTO implements Serializable {
    @PrimaryKey
    @NonNull
    private String orderId;
    private String orderOn;
    private String product;
    private String seller;
    private String status;
    private String cp;
    private String sp;
    private String pickedOn;
    private String deliverOn;
    private String returnOn;
    private String creditAmt;
    private String creditOn;
    private String profit;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getOrderOn() {
        return orderOn;
    }

    public void setOrderOn(String orderOn) {
        this.orderOn = orderOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getPickedOn() {
        return pickedOn;
    }

    public void setPickedOn(String pickedOn) {
        this.pickedOn = pickedOn;
    }

    public String getDeliverOn() {
        return deliverOn;
    }

    public void setDeliverOn(String deliverOn) {
        this.deliverOn = deliverOn;
    }

    public String getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getCreditOn() {
        return creditOn;
    }

    public void setCreditOn(String creditOn) {
        this.creditOn = creditOn;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getReturnOn() {
        return returnOn;
    }

    public void setReturnOn(String returnOn) {
        this.returnOn = returnOn;
    }
}
