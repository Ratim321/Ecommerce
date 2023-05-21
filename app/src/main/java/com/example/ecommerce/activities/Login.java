package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegistrationButton;
    private TextView forgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.loginbtn);
        forgotpass = findViewById(R.id.forgotpass);
        checkuser();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                login(username, password);
            }
        }


        );
       forgotpass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Login.this,SignUp.class);



               startActivity(intent);
           }
       });

    }


    private void login(String Username, String Passoward ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://shaanecommerce.000webhostapp.com/Login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String value=jsonResponse.getString("idValue");
                            String type=jsonResponse.getString("type");
                    if(type.equals("success")){
                        Intent intent = new Intent(Login.this,MainActivity.class);

                        intent.putExtra("id",value);

                      startActivity(intent);
                    }else{
                        Toast.makeText(Login.this,"Invalid Username or Passoward", Toast.LENGTH_SHORT).show();

                    }


                        } catch (JSONException e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("username",Username);
                params.put("password", Passoward);


                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void checkuser(){
        Authentication a=new Authentication(Login.this);
        User u=new User();
        u=a.getCurrentUser();

        if(!u.getUsername().contains("dummy")){
            Intent intent = new Intent(Login.this,MainActivity.class);



            startActivity(intent);
        }
    }

}