package com.example.ecommerce.model;

import java.io.Serializable;

public class order implements Serializable {
    private static final long serialVersionUID = 1234567890L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String t1line,s1line,f1line,waiting,confirmed,delivered,f1,s1,t1,image,name;

    public String getT1line() {
        return t1line;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setT1line(String t1line) {
        this.t1line = t1line;
    }

    public order() {
    }

    public String getS1line() {
        return s1line;
    }

    public void setS1line(String s1line) {
        this.s1line = s1line;
    }

    public String getF1line() {
        return f1line;
    }

    public void setF1line(String f1line) {
        this.f1line = f1line;
    }

    public String getWaiting() {
        return waiting;
    }

    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public order(String t1line, String s1line, String f1line, String waiting, String confirmed, String delivered, String f1, String s1, String t1) {
        this.t1line = t1line;
        this.s1line = s1line;
        this.f1line = f1line;
        this.waiting = waiting;
        this.confirmed = confirmed;
        this.delivered = delivered;
        this.f1 = f1;
        this.s1 = s1;
        this.t1 = t1;
    }
}
