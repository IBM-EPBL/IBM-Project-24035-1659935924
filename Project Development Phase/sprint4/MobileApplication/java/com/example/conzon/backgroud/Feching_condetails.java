package com.example.conzon.backgroud;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.conzon.ApiRequest.ApiClient;
import com.example.conzon.models.userlocation_API_verification;
import com.example.conzon.models.containment_API_request;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feching_condetails extends Worker {
    static Boolean flag = false;

    public Feching_condetails(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<List<userlocation_API_verification>> containment_details = ApiClient.getService().details();
        containment_details.enqueue(new Callback<List<userlocation_API_verification>>() {
            @Override
            public void onResponse(Call<List<userlocation_API_verification>> call, Response<List<userlocation_API_verification>> response) {
                List<userlocation_API_verification> details = response.body();
                containment_API_request.clear();

                for (userlocation_API_verification ss : details) {
                    containment_API_request.add_condetails(Double.parseDouble(ss.getLatitude()), Double.parseDouble(ss.getLongitude()));
                }
                flag = true;
            }

            @Override
            public void onFailure(Call<List<userlocation_API_verification>> call, Throwable t) {
            }
        });
        if (flag)
            return Result.success();
        else
            return Result.retry();
    }
}
