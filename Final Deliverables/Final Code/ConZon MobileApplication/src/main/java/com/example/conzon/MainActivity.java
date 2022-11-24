package com.example.conzon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.conzon.ApiRequest.ApiClient;
import com.example.conzon.backgroud.Feching_condetails;
import com.example.conzon.models.API_Response;
import com.example.conzon.models.Login_API_Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    TextView textView;
    Operation work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workprocess();
    }

    public void workprocess() {
        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(Feching_condetails.class).build();
        work = WorkManager.getInstance(this).enqueue(uploadWorkRequest);
    }

    public void buttonWorkManager(View view) {
        textView = findViewById(R.id.loginbtn);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            Toast.makeText(this, "Please Fill All the Details", Toast.LENGTH_LONG).show();
        else {
            Login_API_Request loginRequest = new Login_API_Request();
            loginRequest.setUsername(username.getText().toString());
            loginRequest.setPassword(password.getText().toString());
            Call<API_Response> loginresponseCall = ApiClient.getService().loginUser(loginRequest);
            loginresponseCall.enqueue(new Callback<API_Response>() {
                @Override
                public void onResponse(Call<API_Response> call, Response<API_Response> response) {
                    if (response.isSuccessful()) {
                        String result = response.body().getResult();
                        if (result.equals("Logined  Successfully \uD83D\uDE0D")) {
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, Loading_Screen.class));
                        } else
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                    } else {
                        String messages = "Please Try again Later....";
                        Toast.makeText(MainActivity.this, messages, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<API_Response> call, Throwable t) {
                    String messages = "Failed connect with server";
                    Toast.makeText(MainActivity.this, messages, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void Register(View view) {
        textView = findViewById(R.id.Regusername);
        startActivity(new Intent(MainActivity.this, Registration.class));
    }

    public void admin(View view) {
        startActivity(new Intent(MainActivity.this, Admin_usage.class));
    }
    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }
}

