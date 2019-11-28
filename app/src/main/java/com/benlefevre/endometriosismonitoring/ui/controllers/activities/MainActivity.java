package com.benlefevre.endometriosismonitoring.ui.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.benlefevre.endometriosismonitoring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        } else {
            setContentView(R.layout.activity_main);
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
    }
}
