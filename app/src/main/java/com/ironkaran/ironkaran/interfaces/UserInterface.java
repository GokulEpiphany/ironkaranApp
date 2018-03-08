package com.ironkaran.ironkaran.interfaces;

import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.OrderDetails;
import com.ironkaran.ironkaran.models.OtpDetails;
import com.ironkaran.ironkaran.models.PickupWrapper;
import com.ironkaran.ironkaran.models.UserDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gokulakrishnanm on 22/02/18.
 */

public interface UserInterface {

    @POST("/user/saveUserDetails")
    public Call<GenericResponse> saveUserDetails(@Body UserDetails userDetails);

    @GET("/user/sendCurrentOtp")
    public Call<OtpDetails> sendOtpDetails();

    @POST("/user/updateAddress")
    public Call<GenericResponse> updateAddress(@Body UserDetails userDetails);

    @POST("/user/initiateOrder")
    public Call<GenericResponse> initiateOrder(@Body OrderDetails orderDetails);

    @POST("/user/initiateOrderFull")
    public Call<GenericResponse> initiateOrderFull(@Body PickupWrapper pickupWrapper);


}
