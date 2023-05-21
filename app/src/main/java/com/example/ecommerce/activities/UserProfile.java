package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.ProductAdapter;
import com.example.ecommerce.adapters.orderadapter;

import com.example.ecommerce.databinding.ActivityUserProfileBinding;
import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    //ArrayList<order> orders;
    ArrayList<order> list=new ArrayList<>();
    ActivityUserProfileBinding binding;
    private static final String URL_ORDER = "https://shaanecommerce.000webhostapp.com/orderstatus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_profile);
       // ArrayList<order> orders = (ArrayList<order>) getIntent()
             //   .getSerializableExtra("my_array_list");
        getpreordervalue();


        //getOrdervalue();


       //  Toast.makeText(UserProfile.this,"INSIDE"+String.valueOf(orders.size()), Toast.LENGTH_SHORT).show();



        //  getOrder();

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
                                    p2.setDelivered(product.getString("OrderNumber"));
                                    Toast.makeText(UserProfile.this,p2.getDelivered(), Toast.LENGTH_SHORT).show();
                                    list.add(p2);
                                }else if(product.getString("status").equals("confirmed")){
                                    p2.setConfirmed("confirmed");
                                    p2.setDelivered(product.getString("OrderNumber"));
                                    Toast.makeText(UserProfile.this,p2.getDelivered(), Toast.LENGTH_SHORT).show();
                                    list.add(p2);
                                }else{
                                    p2.setConfirmed("delivered");
                                    p2.setDelivered(product.getString("OrderNumber"));
                                    Toast.makeText(UserProfile.this,p2.getDelivered(), Toast.LENGTH_SHORT).show();
                                    list.add(p2);
                                }

                                //  Toast.makeText(UserProfile.this,orders.toString(), Toast.LENGTH_SHORT).show();


                            }

                            LinearLayoutManager layoutManager=new LinearLayoutManager(UserProfile.this);
                            orderadapter orderadapter=new orderadapter(UserProfile.this,list);
                            Toast.makeText(UserProfile.this,String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                            binding.OrderPendingRecycleview.setAdapter(orderadapter);
                            binding.OrderPendingRecycleview.setLayoutManager(layoutManager);

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(UserProfile.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(UserProfile.this);
                User u=new User();
                u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("id", u.getId());



                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

//    private void getOrder() {
//        getpreordervalue();
//
//        orders=new ArrayList<>();
//        orderadapter=new orderadapter(this,orders);
//        //getOrdervalue();
//        Toast.makeText(UserProfile.this,String.valueOf(orders.size()), Toast.LENGTH_SHORT).show();
//
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        binding.OrderPendingRecycleview.setLayoutManager(layoutManager);
//        binding.OrderPendingRecycleview.setAdapter(orderadapter);
//
//    }

    private void getOrdervalue() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ORDER,
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

//                                p2.setName(product.getString("category"));
//                                p2.setIcon(product.getString("image"));
                              // orders.add(p2);



                            }
                            //orderadapter.notifyDataSetChanged();
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(UserProfile.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}