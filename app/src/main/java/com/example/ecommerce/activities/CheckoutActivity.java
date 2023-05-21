package com.example.ecommerce.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ecommerce.adapters.CartAdapter;
import com.example.ecommerce.databinding.ActivityCheckoutBinding;
import com.example.ecommerce.model.Authentication;
import com.example.ecommerce.model.Product;

import com.example.ecommerce.model.User;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    double totalPrice = 0;
    final int tax = 11;
    ProgressDialog progressDialog;
    Cart cart;
    private static final String URL_CATEGORY = "http://192.168.0.152/ecommerce/test.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");

        products = new ArrayList<>();

        cart = TinyCartHelper.getCart();

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }

        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("PKR %.2f",cart.getTotalPrice()));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);

        binding.subtotal.setText(String.format("Taka: %.2f",cart.getTotalPrice()));

        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.total.setText("PKR " + totalPrice);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void processOrder() {
        for(int i=0;i<products.size();i++) {
            Product p=products.get(i);
            String ProductName=p.getName();
            String TotalPrice= String.valueOf(p.getPrice());
            String quantity= String.valueOf(p.getQuantity());
            String category= p.getCategory();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy");
            Date date1 = new Date();
            String date= sdf.format(date1);
            String status="Waiting";
            String image=p.getImage();
            insertData(ProductName,TotalPrice,quantity,date,status,category,image);
        }
      //  progressDialog.show();
    }
    private void insertData(String productName, String totalPrice, String quantity, String date, String status,String category,String image) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://shaanecommerce.000webhostapp.com/updateproductorder.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CheckoutActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckoutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Authentication a=new Authentication(CheckoutActivity.this);
                User u=new User();
               u= a.getCurrentUser();

                Map<String, String> params = new HashMap<>();
                params.put("ProductName", productName);
                params.put("quantity", totalPrice);
                params.put("TotalPrice",  quantity);
                params.put("date", date);
                params.put("status", status);
                params.put("delivary", "Null");
                params.put("category", category);
                params.put("image", image);
                params.put("CustomerName", u.getUsername());
                params.put("CustomerId", u.getId());


                return params;
            }

        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}