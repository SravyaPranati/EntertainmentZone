package com.example.entertainmentzone.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.entertainmentzone.R;
import com.example.entertainmentzone.adapters.ShowAdapter;
import com.example.entertainmentzone.database.ShowEntity;
import com.example.entertainmentzone.database.ShowViewModel;
import com.example.entertainmentzone.model.show.ShowInfo;
import com.example.entertainmentzone.model.show.ShowParse;
import com.example.entertainmentzone.network.NetworkUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TeleActivity extends AppCompatActivity  {

    private ProgressBar progressBar;
    private RecyclerView  rView;
    private ShowAdapter sAdapter;
    private List<ShowInfo> shows;
    private String jsonData = "";
    ShowViewModel showViewModel;
    boolean is_fav = false;
    String sort_order = "Popular";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele);
        setTitle(sort_order);
        progressBar = findViewById(R.id.progress_bar);
        rView = findViewById(R.id.recycler_view);
        shows = new ArrayList<>();
        sharedPreferences = getSharedPreferences("SYSPREF",MODE_PRIVATE);
        showViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        if(sharedPreferences!=null){
            jsonData = sharedPreferences.getString("SAVED_JSON","");
            if(jsonData!="" && isInternetAvailable()){

                setTitle(sharedPreferences.getString("order","Popular TeleShows"));
                sAdapter = new ShowAdapter(this, shows);
                shows = ShowParse.getShow(jsonData);
                sAdapter.setShowData(shows);
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
                    rView.setLayoutManager(new GridLayoutManager(this, 2));
                }
                else
                {
                    rView.setLayoutManager(new GridLayoutManager(this, 3));
                }
                rView.setAdapter(sAdapter);
            }
            else {
                loadFavorites();
            }
        }
    }



    private boolean isInternetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void getShow(String item) {
        URL url = NetworkUtils.tvShowURL(item);
        new ShowTask().execute(url);
    }

    public void loadFavorites()
    {
        is_fav = true;
        sort_order = "Favorites";
        sharedPreferences.edit().putString("order","Favorites").apply();
        showViewModel.getAllShows().observe(this, new Observer<List<ShowEntity>>() {
            @Override
            public void onChanged(@Nullable List<ShowEntity> showEntities) {
                if(showEntities!=null){
                    setDataToUI(showEntities);
                }

            }
        });


    }

    private void setDataToUI(List<ShowEntity> showEntities)
    {
        if(sharedPreferences!=null)
        {
            jsonData = sharedPreferences.getString("SAVED_JSON","");
            if(jsonData == "")
            {
                setTitle(sort_order);
                List<ShowInfo> showInfos = new ArrayList<>();
                for(int i = 0; i<showEntities.size();i++)
                {
                    String id = showEntities.get(i).getId();
                    String title = showEntities.get(i).getTitle();
                    String overview = showEntities.get(i).getOverview();
                    String rating = showEntities.get(i).getRating();
                    String popularity = showEntities.get(i).getPopularity();
                    String poster_path = showEntities.get(i).getPoster_path();
                    //String backdrop_path = movieEntities.get(i).getMvBackdropPath();

                    ShowInfo showInfo = new ShowInfo();
                    showInfo.setShId(id);
                    showInfo.setShOverview(overview);
                    showInfo.setShName(title);
                    showInfo.setShPoster_path(poster_path);
                    showInfo.setShRating(rating);
                    showInfo.setShPopularity(popularity);

                    showInfos.add(showInfo);
                }

                ShowAdapter sAdapter = new ShowAdapter(this,showInfos);
                rView.setAdapter(sAdapter);
                if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                    rView.setLayoutManager(new GridLayoutManager(this, 2));
                }
                else {
                    rView.setLayoutManager(new GridLayoutManager(this, 3));
                }
            }

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular_id:
                if(isInternetAvailable()) {
                    sort_order = "Popular";
                    setTitle(sort_order);
                    sharedPreferences.edit().putString("order","Popular TeleShows").apply();
                    getShow("popular");
                    return true;
                }
                else
                    Toast.makeText(this,getString(R.string.internet), Toast.LENGTH_SHORT).show();
            case R.id.top_rated_id:
                if(isInternetAvailable()) {
                    sort_order = "Top Rated";
                    setTitle(sort_order);
                    sharedPreferences.edit().putString("order","Top_Rated TeleShows").apply();
                    getShow("top_rated");
                    return true;
                }
                else
                    Toast.makeText(this,getString(R.string.internet), Toast.LENGTH_SHORT).show();
            case R.id.favorite:
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("SAVED_JSON","");
                edit.apply();
                sort_order = "Favorite Shows";
                setTitle(sort_order);
                sharedPreferences.edit().putString("order",sort_order).apply();
                loadFavorites();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public class ShowTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String getShows = null;
            getShows =NetworkUtils.getConnection(url);
            return getShows;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jsonData = s;
            progressBar.setVisibility(View.INVISIBLE);
            if (s == null) {
                rView.setVisibility(View.INVISIBLE);
            } else {
                rView.setVisibility(View.VISIBLE);
                sAdapter = new ShowAdapter(TeleActivity.this, shows);
                shows = ShowParse.getShow(s);
                sAdapter.setShowData(shows);
                rView.setLayoutManager(new GridLayoutManager(TeleActivity.this, 2));
                rView.setAdapter(sAdapter);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("SAVED_JSON",s);
                edit.apply();
            }

        }

    }


}