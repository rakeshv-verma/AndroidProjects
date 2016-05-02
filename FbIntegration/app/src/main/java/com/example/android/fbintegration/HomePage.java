package com.example.android.fbintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity {
    String birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent response=getIntent();
        String s=response.getStringExtra("response");
        
        Toast.makeText(getApplication(),s,Toast.LENGTH_LONG).show();
        TextView userDetails= (TextView) findViewById(R.id.userData);
        try {
            JSONObject json=new JSONObject(s);
            String id=json.getString("id");

            String gender=json.getString("email");

            if(s.contains("birthday"))
            {
                birthday =json.getString("birthday");
            }
            String name=json.getString("name");

            userDetails.setText(id+" "+name+" "+gender+" "+birthday);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
