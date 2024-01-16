package com.example.coursework.dto;

import java.util.Objects;

public class Customer {
    private int customerId;
    private String bankDetails;
    private String phone;
    private String email;
    private String password;
    private final int contactPersonId;
    private final boolean isAdmin;

    public Customer(int customerId,int contactPersonId, String bankDetails, String phone, String email,
                    String password, boolean isAdmin) {
        this.customerId = customerId;
        this.bankDetails = bankDetails;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.contactPersonId = contactPersonId;
        this.isAdmin = isAdmin;
    }
    public Customer(int contactPersonId,String bankDetails, String phone, String email,
                    String password, boolean isAdmin) {
        this.bankDetails = bankDetails;
        this.phone = phone;
        this.email = email;
        this.contactPersonId = contactPersonId;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContactPersonId() {
        return contactPersonId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Customer otherCustomer = (Customer) obj;
        return Objects.equals(getCustomerId(), otherCustomer.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId());
    }

}
