package com.example.ecommerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.ProductAdapter;
import com.example.ecommerce.adapters.orderadapter;
import com.example.ecommerce.databinding.ActivitySearchBinding;

import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.order;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.databinding.ActivitySearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {


    ActivitySearchBinding binding;
    ProductAdapter productAdapter;
    ArrayList<Product> products= new ArrayList<>();
    ArrayList<Product> filterproduct= new ArrayList<>();
    TextView searchavailable;
    private static final String URL_SEARCH = "https://shaanecommerce.000webhostapp.com/search.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        searchavailable=findViewById(R.id.notavailable);

        String query = getIntent().getStringExtra("query");
        getProducts(query);
        getSupportActionBar().setTitle(query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        productAdapter = new ProductAdapter(this, filterproduct);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
        if(filterproduct.size()==0){
            searchavailable.setVisibility(View.GONE);

        }else{
            searchavailable.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void  getProducts(String query) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
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
                                Product p1=new Product();
                                p1.setName(product.getString("name"));

                                p1.setImage(product.getString("image"));
                                p1.setPrice(Double.parseDouble(product.getString("price")));
                                p1.setDiscount(Double.parseDouble(product.getString("discountPercentage")));
                                p1.setCategory(product.getString("category"));
                                    products.add(p1);
                                }
                productAdapter.notifyDataSetChanged();
                                //  Toast.makeText(UserProfile.this,orders.toString(), Toast.LENGTH_SHORT).show();

//                            Toast.makeText(SearchActivity.this,String.valueOf(products.size()), Toast.LENGTH_SHORT).show();

                            for(Product item:products) {

                                if (item.getName().toLowerCase().contains(query.toLowerCase())) {

                                    filterproduct.add(item);
                                }
                            }



                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(SearchActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(SearchActivity.this);
                User u=new User();
                u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("name", query);



                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}