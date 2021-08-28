package com.example.impeccable_india;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.impeccable_india.API.ApiClient;
import com.example.impeccable_india.API.ApiInterface;
import com.example.impeccable_india.Model.Article;
import com.example.impeccable_india.Model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class froupage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    public static final String API_KEY="33ed40cda4e74bf69c4615447764a0c8";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private  Adapter adapter;
    private TextView for_you;
    private String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_froupage);

        swipeRefreshLayout =findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        for_you = findViewById(R.id.for_you);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(froupage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setAdapter(adapter);

        onLoadingSwipeRefresh("");
    }

    public  void LoadJson(final String keyword){
        for_you.setVisibility(View.INVISIBLE);
        swipeRefreshLayout .setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country =Utils.getCountry();
        String language =Utils.getlanguage();

        Call<News> call;

        if (keyword.length()>0) {
            call = apiInterface.getNewsSearch(keyword, language, "publishAt", API_KEY);
        }else {
            call = apiInterface.getNews(country, API_KEY);
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() !=null){
                    if(articles.isEmpty()){
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new Adapter(articles,froupage.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    initListener();

                    for_you.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }else {

                    for_you.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(froupage.this,"no Result", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                for_you.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.chat:
                Intent intent =new Intent(froupage.this,chat.class);
                startActivity(intent);
                break;
            case R.id.info:
                Intent intent1 =new Intent(froupage.this,infovideo.class);
                startActivity(intent1);
                break;
            case R.id.culture:
                Intent intent2 =new Intent(froupage.this,culture.class);
                startActivity(intent2);
                break;
            case R.id.technews:
                Intent intent3 =new Intent(froupage.this,technews.class);
                startActivity(intent3);
                break;
            case R.id.Politicalnews:
                Intent intent4 =new Intent(froupage.this,Politicalnews.class);
                startActivity(intent4);
                break;




        }
        return super.onOptionsItemSelected(item);
    }

    private  void  initListener(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(froupage.this,newsdetail.class);
                Article article = articles.get(position);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date",article.getPublishedAt());
                intent.putExtra("source",article.getSource().getName());
                intent.putExtra("author",article.getAuthor());

             
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =(SearchView)menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search latest News....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length()>2) {
                    onLoadingSwipeRefresh(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false,false);


        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("");

    }
    private void onLoadingSwipeRefresh(final  String keyword){
    swipeRefreshLayout.post(
            new Runnable() {
                @Override
                public void run() {
                    LoadJson(keyword);


                }
            }
    );


    }
}

