package com.ironkaran.ironkaran.interfaces;

import com.ironkaran.ironkaran.models.ApartmentDetails;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.OrderDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gokulakrishnanm on 22/02/18.
 */

public interface DetailsInterface {

    @GET("/user/getApartments")
    public Call<List<ApartmentDetails>> getAparmentDetails();

    @GET("/user/getCurrentOrderDetails")
    public Call<OrderDetails> getCurrentOrderDetails(@Query(value="userId")long userId);

    @GET("/user/getAllOrders")
    public Call<List<OrderDetails>> getAllOrderDetails(@Query(value="userId") long userId);

    @GET("/user/ironkaranFeedback")
    public Call<GenericResponse> saveFeedback(@Query(value="userId") long userId, @Query(value="feedback") String feedback);

}
