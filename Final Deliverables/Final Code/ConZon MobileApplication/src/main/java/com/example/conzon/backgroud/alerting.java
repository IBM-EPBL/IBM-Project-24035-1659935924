package com.example.conzon.backgroud;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.conzon.ApiRequest.ApiClient;
import com.example.conzon.ContainmentZone;
import com.example.conzon.models.API_Response;
import com.example.conzon.models.userlocation_API_verification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class alerting extends Worker {
    static Boolean flag = false;

    public alerting(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        userlocation_API_verification userlocation_api_verification = new userlocation_API_verification();
        userlocation_api_verification.setLatitude(String.valueOf(current_location.getLati()));
        userlocation_api_verification.setLongitude(String.valueOf(current_location.getLongi()));

        Call<API_Response> user_location_verification = ApiClient.getService().current(userlocation_api_verification);
        user_location_verification.enqueue(new Callback<API_Response>() {
            @Override
            public void onResponse(@NonNull Call<API_Response> call, @NonNull Response<API_Response> response) {
                assert response.body() != null;
                String result = response.body().getResult();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                if (result.equals("YOU ARE IN CONTAINMENT ZONE")) {
                    ContainmentZone.sentAlert(result);
                }
                flag = true;
            }

            @Override
            public void onFailure(@NonNull Call<API_Response> call, @NonNull Throwable t) {

            }
        });
        if (flag)
            return Result.success();
        else
            return Result.retry();
    }


}
