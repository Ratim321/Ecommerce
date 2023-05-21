package com.example.ecommerce.activities;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.util.Log;
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
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.adapters.ProductAdapter;
import com.example.ecommerce.databinding.ActivityMainBinding;

import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.order;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.model.Product;
import com.fxn.OnBubbleClickListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 import com.example.ecommerce.model.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<order> orders;
    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    private static final String URL_CATEGORY = "https://shaanecommerce.000webhostapp.com/test.php";
    private static final String URL_PRODUCTS = "https://shaanecommerce.000webhostapp.com/getcategory.php";
    ProductAdapter productAdapter;
    ArrayList<Product> products;

    private static final String URL_ORDER = "https://shaanecommerce.000webhostapp.com/orderstatus.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orders=new ArrayList<>();
        getpreordervalue();
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());

                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        binding.bottomNav.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                //Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                switch (i) {

                    case 0:
                       // Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(MainActivity.this, CartActivity.class);

                        startActivity(intent3);
                        break;
                    case 1:
                       // Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, CartActivity.class);

                        startActivity(intent);
                        break;
                    case 2131361925:
                      //  Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this, CartActivity.class);

                        startActivity(intent1);
                        break;
                    case 2131362258:
                      //  Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MainActivity.this, test2.class);
                        intent2.putExtra("my_array_list", orders);
                        startActivity(intent2);
                        break;
                }
            }
        });

        initCategories();
        initProducts();
        initSlider();
    }

    private void initSlider() {
        getRecentOffers();
    }

    void initCategories() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
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

                                if(product.getString("status").equals("waiting")){
                                    p2.setConfirmed("waiting");
                                }
                                orders.add(p2);
                                //  Toast.makeText(UserProfile.this,orders.toString(), Toast.LENGTH_SHORT).show();


                              //  Toast.makeText(MainActivity.this,"INSIDE"+String.valueOf(orders.size()), Toast.LENGTH_SHORT).show();

                            }

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(MainActivity.this);
                User u=new User();
                u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("id", u.getId());



                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    void getCategories() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CATEGORY,
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

                                Category p2=new Category();

                                p2.setName(product.getString("category"));
                                p2.setIcon(product.getString("image"));
                                categories.add(p2);



                            }
                           categoryAdapter.notifyDataSetChanged();
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    void getRecentProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
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
                                p1.setId(Integer.parseInt(product.getString("id")));
                                p1.setImage(product.getString("image"));
                                p1.setPrice(Double.parseDouble(product.getString("price")));
                                p1.setDiscount(Double.parseDouble(product.getString("discountPercentage")));
                                p1.setCategory(product.getString("category"));

                                products.add(p1);


                            }
                     productAdapter.notifyDataSetChanged();
                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    void getRecentOffers() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
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





                                binding.carousel.addData(
                                        new CarouselItem(
                                                product.getString("image"),
                                                product.getString("category")
                                        )
                                );


                            }


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }










    void initProducts() {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);

        getRecentProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

}