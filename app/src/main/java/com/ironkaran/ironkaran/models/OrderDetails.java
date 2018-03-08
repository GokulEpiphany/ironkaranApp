package com.ironkaran.ironkaran.models;


public class OrderDetails {

	private Long orderNumber;
	
	private Long userId;
	private String address;
	private int numberOfClothes;
	private String pickupBefore;
	private int pickedUp;
	private int processed;
	private int categoryA;
	private int categoryB;
	private int categoryC;
	private double price;
	private int delivered;
	private String processState;
	private int done;
	private String orderPlacedWith;
	private Integer apartmentId;

    public OrderDetails() {
    }

    public OrderDetails(Long orderNumber, Long userId, String address, int numberOfClothes, String pickupBefore, int pickedUp, int processed, int categoryA, int categoryB, int categoryC, double price, int delivered, String processState, int done, String orderPlacedWith, Integer apartmentId) {
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.address = address;
        this.numberOfClothes = numberOfClothes;
        this.pickupBefore = pickupBefore;
        this.pickedUp = pickedUp;
        this.processed = processed;
        this.categoryA = categoryA;
        this.categoryB = categoryB;
        this.categoryC = categoryC;
        this.price = price;
        this.delivered = delivered;
        this.processState = processState;
        this.done = done;
        this.orderPlacedWith = orderPlacedWith;
        this.apartmentId = apartmentId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfClothes() {
        return numberOfClothes;
    }

    public void setNumberOfClothes(int numberOfClothes) {
        this.numberOfClothes = numberOfClothes;
    }

    public String getPickupBefore() {
        return pickupBefore;
    }

    public void setPickupBefore(String pickupBefore) {
        this.pickupBefore = pickupBefore;
    }

    public int getPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(int pickedUp) {
        this.pickedUp = pickedUp;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public int getCategoryA() {
        return categoryA;
    }

    public void setCategoryA(int categoryA) {
        this.categoryA = categoryA;
    }

    public int getCategoryB() {
        return categoryB;
    }

    public void setCategoryB(int categoryB) {
        this.categoryB = categoryB;
    }

    public int getCategoryC() {
        return categoryC;
    }

    public void setCategoryC(int categoryC) {
        this.categoryC = categoryC;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getOrderPlacedWith() {
        return orderPlacedWith;
    }

    public void setOrderPlacedWith(String orderPlacedWith) {
        this.orderPlacedWith = orderPlacedWith;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }
}
