package com.example.nikhil.zapposapp;

import android.app.SearchManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import com.example.nikhil.zapposapp.databinding.ActivityMainBinding;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,ProductFragment.OnFragmentInteractionListener {

    public static final String BASE_URL = "https://api.zappos.com";
    public static final String KEY  = "b743e26728e16b81da139182bb2094357c31d331";
    Retrofit retrofit;
    ProductsAPI productsAPI;
    Map<String,String> map;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductFragment fragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentID);
                fragment.updateUI(binding.getPage().getResultAtIndex(position),true);

            }
        });

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        productsAPI = retrofit.create(ProductsAPI.class);
        map = new HashMap<String, String>();
        map.put("key",KEY);
        handleQuery("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        handleQuery(query);
        return false;
    }

    public void handleQuery(final String query) {

        if(map.containsKey("term")) {
            map.remove("term");
        }
        map.put("term",query);
        Call<ProductsPage> call = productsAPI.getProducts(map);
        call.enqueue(new Callback<ProductsPage>() {
            @Override
            public void onResponse(Call<ProductsPage> call, Response<ProductsPage> response) {

                binding.setPage(response.body());
                ProductFragment fragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentID);
                fragment.updateUI(binding.getPage().getResultAtIndex(0),false);
            }

            @Override
            public void onFailure(Call<ProductsPage> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
