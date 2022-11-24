package com.example.conzon;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conzon.ApiRequest.ApiClient;
import com.example.conzon.models.API_Response;
import com.example.conzon.models.Register_API_Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

    }

    public void Register(View view) {
        textView = findViewById(R.id.signupbtn);
        EditText username = findViewById(R.id.Regusername);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText repassword = findViewById(R.id.repassword);
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty()||repassword.getText().toString().isEmpty())
            Toast.makeText(this, "Please Fill All the Details", Toast.LENGTH_LONG).show();
        else {
            if (!password.getText().toString().equals(repassword.getText().toString()))
                Toast.makeText(Registration.this, "Password and Repassword Not Match", Toast.LENGTH_LONG).show();
            else {
                Register_API_Request registerRequest = new Register_API_Request();
                registerRequest.setUsername(username.getText().toString());
                registerRequest.setPassword(password.getText().toString());
                registerRequest.setMailId(email.getText().toString());
                Call<API_Response> regresponseCall = ApiClient.getService().registerUser(registerRequest);
                regresponseCall.enqueue(new Callback<API_Response>() {
                    @Override
                    public void onResponse(@NonNull Call<API_Response> call, @NonNull Response<API_Response> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            String result = response.body().getResult();
                            if (result.equals("Created  Successfully \uD83D\uDE0D")) {
                                Toast.makeText(Registration.this, result, Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(Registration.this, result, Toast.LENGTH_LONG).show();
                        } else {
                            String messages = "Please Try again Later....";
                            Toast.makeText(Registration.this, messages, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<API_Response> call, Throwable t) {
                        String messages = "Failed connect with server";
                        Toast.makeText(Registration.this, messages, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }
}
