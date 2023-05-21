package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.orderadapter;
import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test2 extends AppCompatActivity {
RecyclerView recyclerView;
    ArrayList<order> list=new ArrayList<>();
    private static final String URL_ORDER = "https://shaanecommerce.000webhostapp.com/orderstatus.php";
    private static final String URL_USERPROFILE = "https://shaanecommerce.000webhostapp.com/UserProfile.php";
    private static final String URL_PRODUCTS = "http://192.168.0.152/ecommerce/getcategory.php";
  TextView username,email,phone,adress,t;
  ImageView imageView;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        username=findViewById(R.id.mainprofileusername);
        email=findViewById(R.id.mainprofileemail);
       phone=findViewById(R.id.mainprofilephone);
        adress =findViewById(R.id.mainprofileaddress);
        recyclerView=findViewById(R.id.testrecycle);

        imageView=findViewById(R.id.userimgview);
        getuserinfo();
        getpreordervalue();

    }

    private void getuserinfo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_USERPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                               JSONObject product = array.getJSONObject(0);

                           username.setText(product.getString("username"));
                            email.setText(product.getString("email"));
                            adress.setText(product.getString("adress"));
                           phone.setText(product.getString("phoneNo"));


//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array

//
//                                Product p1=new Product();
//                                p1.setName(product.getString("name"));
//                                p1.setId(Integer.parseInt(product.getString("id")));
//                                p1.setImage(product.getString("image"));
//                                p1.setPrice(Double.parseDouble(product.getString("price")));
//                                p1.setDiscount(Double.parseDouble(product.getString("discountPercentage")));
//                                p1.setCategory(product.getString("category"));
//
//                                             }

                        } catch (JSONException e) {
                            Toast.makeText(test2.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(test2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(test2.this);
                User u=new User();
                u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("id", u.getId());



                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void getpreordervalue() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                order p2=new order();

                                if(product.getString("status").equals("Waiting")){
                                    p2.setConfirmed("waiting");
                                    p2.setName(product.getString("ProductName"));
                                    p2.setImage(product.getString("image"));
                                    p2.setDelivered(product.getString("OrderNumber"));

                                    String value=product.getString("image");
                                    Glide.with(getApplicationContext())
                                            .load(value)
                                            .into(imageView);

                                    list.add(p2);
                                }else if(product.getString("status").equals("confirmed")){
                                    p2.setConfirmed("confirmed");
                                    p2.setImage(product.getString("image"));
                                    p2.setDelivered(product.getString("OrderNumber"));
                                    String value=product.getString("image");
                                    p2.setName(product.getString("ProductName"));
                                    Glide.with(getApplicationContext())
                                            .load(value)
                                            .into(imageView);

                                    list.add(p2);
                                }else{
                                    p2.setConfirmed("delivered");
                                    String value=product.getString("image");
                                    Glide.with(getApplicationContext())
                                            .load(value)
                                            .into(imageView);
                                    p2.setName(product.getString("ProductName"));
                                    p2.setImage(product.getString("image"));
                                    p2.setDelivered(product.getString("OrderNumber"));

                                    list.add(p2);
                                }

                                //  Toast.makeText(UserProfile.this,orders.toString(), Toast.LENGTH_SHORT).show();


                            }

                            LinearLayoutManager layoutManager=new LinearLayoutManager(test2.this);
                            orderadapter orderadapter=new orderadapter(test2.this,list);

                            recyclerView.setAdapter(orderadapter);
                            recyclerView.setLayoutManager(layoutManager);
                            orderadapter.notifyDataSetChanged();
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(test2.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(test2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(test2.this);
                User u=new User();
                u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("id", u.getId());



                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}