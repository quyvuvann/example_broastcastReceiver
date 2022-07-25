package com.example.example_broadcastrecever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    Button btnsendBroadReceiver;
    TextView txtview;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if("key_intent".equals(intent.getAction())){
                String jsonObject = intent.getStringExtra("key_user");
                Gson gson = new Gson();
                User user = gson.fromJson(jsonObject,User.class);
                txtview.setText("ID : " + user.getId() + "  Username : " + user.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        btnsendBroadReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoradcast();
            }
        });
    }

    private void clickBoradcast() {
        Intent intent = new Intent("key_intent");
        User user = new User(1,"vu van quy");
        Gson gson = new Gson();
        String  jsonString = gson.toJson(user);
        intent.putExtra("key_user",jsonString);
        sendBroadcast(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("key_intent");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    public void anhxa(){
        btnsendBroadReceiver = findViewById(R.id.idsendbroadcast);
        txtview = findViewById(R.id.txtview);
    }
}