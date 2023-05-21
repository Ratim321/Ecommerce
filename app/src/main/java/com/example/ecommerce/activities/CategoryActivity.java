package com.example.ecommerce.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerce.adapters.ProductAdapter;

import com.example.ecommerce.databinding.ActivityCategoryBinding;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.utils.Constants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    ProductAdapter productAdapter;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);


        int catId = getIntent().getIntExtra("catId", 0);
        String categoryName = getIntent().getStringExtra("categoryName");
        insertData(categoryName);
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void insertData(String name) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://shaanecommerce.000webhostapp.com/getcategoryproductDistinct.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                          //  Toast.makeText(CategoryActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Product p1=new Product();
                                p1.setName(product.getString("name"));
                                p1.setId(Integer.parseInt(product.getString("id")));
                                p1.setImage(product.getString("image"));
                                p1.setPrice(Double.parseDouble(product.getString("price")));
                                p1.setDiscount(Double.parseDouble(product.getString("discountPercentage")));



                                products.add(p1);
                            }
                            productAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category", name);

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}