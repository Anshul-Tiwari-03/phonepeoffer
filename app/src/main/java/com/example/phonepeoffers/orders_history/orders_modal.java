package com.example.phonepeoffers.orders_history;

public class orders_modal {

    String id="",coupon_id="",coupon_owner_id="",coupon_buyer_id="",amount_without_tax="",offer_buyed_date="";
    public void setid(String id) {
        this.id=id;
    }

    public void set_coupon_id(String coupon_id) {
        this.coupon_id=coupon_id;
    }

    public String getId() {
        return id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getCoupon_owner_id() {
        return coupon_owner_id;
    }

    public String getCoupon_buyer_id() {
        return coupon_buyer_id;
    }

    public String getAmount_without_tax() {
        return amount_without_tax;
    }

    public String getOffer_buyed_date() {
        return offer_buyed_date;
    }

    public void set_coupon_owner_id(String coupon_owner_id) {
        this.coupon_owner_id=coupon_owner_id;
    }

    public void set_coupon_buyer_id(String coupon_buyer_id) {
        this.coupon_buyer_id=coupon_buyer_id;
    }

    public void set_price_without_Tax(String amount_without_tax) {
        this.amount_without_tax=amount_without_tax;
    }

    public void set_Buy_date(String offer_buyed_date) {
        this.offer_buyed_date=offer_buyed_date;
    }
}
