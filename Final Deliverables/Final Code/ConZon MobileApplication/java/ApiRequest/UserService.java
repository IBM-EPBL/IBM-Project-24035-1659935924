package com.example.conzon.ApiRequest;

import com.example.conzon.models.userlocation_API_verification;
import com.example.conzon.models.Login_API_Request;
import com.example.conzon.models.API_Response;
import com.example.conzon.models.Register_API_Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("User_login")
    Call<API_Response> loginUser(@Body Login_API_Request loginRequest);

    @POST("User_Registration")
    Call<API_Response> registerUser(@Body Register_API_Request registerRequest);

    @GET("Con_data")
    Call<List<userlocation_API_verification>> details();

    @POST("/userlocation")
    Call<API_Response> current(@Body userlocation_API_verification userlocation_api_verification);

}
