package com.example.phonepeoffers.modal;

public class modal {
    String id="",title="",expiry_Date="",code="",code_attachment="",offer_status="",offer_taken_by="",offer_posted_by="",amount_without_tax="";

    String[] ary;

    public void setImages(String[] ary) {
        this.ary=ary;
    }

    public String getId() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setitle(String title) {
        this.title = title;
    }

    public String getExpiry_Date() {
        return expiry_Date;
    }

    public void set_Expiry_date(String expiry_Date) {
        this.expiry_Date = expiry_Date;
    }

    public String getCode() {
        return code;
    }

    public void set_code(String code) {
        this.code = code;
    }

    public String getCode_attachment() {
        return code_attachment;
    }

    public void set_code_attachment(String code_attachment) {
        this.code_attachment = code_attachment;
    }

    public String getOffer_status() {
        return offer_status;
    }

    public void set_offer_status(String offer_status) {
        this.offer_status = offer_status;
    }

    public String getOffer_taken_by() {
        return offer_taken_by;
    }

    public void set_offer_taken_by(String offer_taken_by) {
        this.offer_taken_by = offer_taken_by;
    }

    public String getOffer_posted_by() {
        return offer_posted_by;
    }

    public void set_offer_posted_by(String offer_posted_by) {
        this.offer_posted_by = offer_posted_by;
    }

    public String getAmount_without_tax() {
        return amount_without_tax;
    }

    public void set_price_without_Tax(String amount_without_tax) {
        this.amount_without_tax = amount_without_tax;
    }

    public String[] getImagesAry() {
        return ary;
    }

    public void setAry(String[] ary) {
        this.ary = ary;
    }
}
