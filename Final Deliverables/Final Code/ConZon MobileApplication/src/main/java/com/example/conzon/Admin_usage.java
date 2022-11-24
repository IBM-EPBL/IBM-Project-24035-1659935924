package com.example.conzon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conzon.backgroud.Application_Rest_url;
import com.google.android.material.button.MaterialButton;

public class Admin_usage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        MaterialButton materialButton = (MaterialButton) findViewById(R.id.SetHostAddress);
        EditText HostAddress = (EditText) findViewById(R.id.Address);
        HostAddress.setText(Application_Rest_url.getUrl());

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application_Rest_url.url = HostAddress.getText().toString();
                HostAddress.setText(Application_Rest_url.getUrl());
                Intent intent = new Intent(Admin_usage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
