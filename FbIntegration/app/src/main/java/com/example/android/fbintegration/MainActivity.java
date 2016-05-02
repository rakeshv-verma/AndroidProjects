package com.example.android.fbintegration;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
LoginButton loginButton;
    CallbackManager callbackManager;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e) {
//        }

        loginButton= (LoginButton) findViewById(R.id.loginButton);
       // loginButton.setReadPermissions("email", "user_likes", "user_friends","public_profile");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code

                                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
                                Log.e("Login :", "Success");
                                Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show();

                                Profile profile = Profile.getCurrentProfile();
                                Log.e("Profile class ", "" +profile);

                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback(){
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                if (response.getError()!=null)
                                                {
                                                    Log.e(" Fb ","Error in Response "+ response);


                                                }
                                                else{
                                                   // Profile profile = Profile.getCurrentProfile();
                                                    
                                                   // name=object.optString("name");
                                                    //Log.e(" Fb ","Json Object Data "+object+" Name "+ name);
                                                    String r= response.getRawResponse();
                                                    Log.e(" Fb ",r);

                                                    Intent homePage=new Intent(getApplication(),HomePage.class);
                                                    homePage.putExtra("response",r);
                                                    startActivity(homePage);
                                                }
                                                // Application code
//                                                try {
//                                                    String email = object.getString("email");
//                                                    String birthday = object.getString("birthday"); // 01/31/1980 format
//
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }

                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email,gender,birthday");
                                request.setParameters(parameters);
                                request.executeAsync();

//                                new GraphRequest(
//                                        AccessToken.getCurrentAccessToken(),
//                                        "/{user-id}",
//                                        null,
//                                        HttpMethod.GET,
//                                        new GraphRequest.Callback() {
//                                            public void onCompleted(GraphResponse response) {
//                                                JSONObject json=response.getJSONObject();
//                                                try {
//                                                   Log.e("Birthday",json.getString("birthday")) ;
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }
//                                ).executeAsync();



                            }

                            @Override
                            public void onCancel() {
                                // App code
                                Log.e("Login :", "Cancelled");
                                Toast.makeText(getApplication(), "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.e("Login :", "Failed");
                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        });


//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.e("Content", "User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.e("Login ", "Cancelled");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Log.e("Login ", "Failed");
//            }
//        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
