package com.ironkaran.ironkaran.models;


public class PickupWrapper {

    OrderDetails orderDetails;
    UserDetails userDetails;
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
    public UserDetails getUserDetails() {
        return userDetails;
    }
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public PickupWrapper(){

    }
    public PickupWrapper(OrderDetails orderDetails, UserDetails userDetails) {
        super();
        this.orderDetails = orderDetails;
        this.userDetails = userDetails;
    }
    @Override
    public String toString() {
        return "PickupWrapper [orderDetails=" + orderDetails + ", userDetails=" + userDetails + "]";
    }




}
