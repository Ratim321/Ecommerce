package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {


    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mPhoneEditText;
    private EditText memailEditText;
    private Button mLoginButton;
    private Button mRegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsernameEditText = findViewById(R.id.Susername);
        mPasswordEditText = findViewById(R.id.Spassword);
        mPhoneEditText = findViewById(R.id.mobile);
        memailEditText = findViewById(R.id.Semail);
        mLoginButton = findViewById(R.id.Sbtn);
        checkuser();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String phone = mPhoneEditText.getText().toString();
                String email = memailEditText.getText().toString();
                login(username, password,phone,email);
            }
        });


    }

    private void login(String Username, String Passoward ,String phone,String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://shaanecommerce.000webhostapp.com/SignUp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            String type=jsonResponse.getString("status");
                            Toast.makeText(SignUp.this,type, Toast.LENGTH_SHORT).show();

                            if(type.equals("success")){
                                Toast.makeText(SignUp.this,"Done", Toast.LENGTH_SHORT).show();
                                String id=jsonResponse.getString("id");

                                User u=new User(Username,Passoward,id);
                                Authentication a=new Authentication(SignUp.this);
                                a.setCurrentUser(u);



                                Intent intent = new Intent(SignUp.this,MainActivity.class);

                                intent.putExtra("id",id);

                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUp.this,"INVALID", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("username",Username);
                params.put("password", Passoward);
                params.put("email",email);
                params.put("phone", phone);


                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void checkuser(){
        Authentication a=new Authentication(SignUp.this);
        User u=new User();
        u=a.getCurrentUser();
        if(u.getId()!=null){
            Intent intent = new Intent(SignUp.this,MainActivity.class);



            startActivity(intent);
        }
    }

}