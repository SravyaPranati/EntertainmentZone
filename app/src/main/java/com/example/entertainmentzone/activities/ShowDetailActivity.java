package com.example.entertainmentzone.activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entertainmentzone.NewAppWidget;
import com.example.entertainmentzone.R;
import com.example.entertainmentzone.adapters.TrailerAdapter;
import com.example.entertainmentzone.database.ShowEntity;
import com.example.entertainmentzone.database.ShowViewModel;
import com.example.entertainmentzone.model.show.ShowInfo;
import com.example.entertainmentzone.model.video.TrailerInfo;
import com.example.entertainmentzone.model.video.TrailerParse;
import com.example.entertainmentzone.network.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {
    TextView title,rating,overview,popularity,poster_path;
    ShowViewModel showViewModel;
    Button button;
    ShowInfo showInfo;
    String videoData = "";
    private List<TrailerInfo> trailers;
    String shId;
    public static final String SHOW_ID = "SHOW_ID";
    RecyclerView tView;
    TrailerAdapter trailerAdapter;

    SharedPreferences sharedPreferences;

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        sharedPreferences = getSharedPreferences(this.getString((R.string.fname)),MODE_PRIVATE);
        ImageView iv = findViewById(R.id.image_icon);
        title = findViewById(R.id.title);
        rating = findViewById(R.id.vote_count);
        popularity = findViewById(R.id.popularity);
        overview = findViewById(R.id.overview);
        button = findViewById(R.id.button);
        Intent intent = getIntent();
        showViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        showInfo = (ShowInfo)intent.getSerializableExtra(this.getString(R.string.obj));
        String images = intent.getStringExtra(this.getString(R.string.p_path));
        title.setText(showInfo.getShName());
        rating.setText(showInfo.getShRating());
        overview.setText(showInfo.getShOverview());
        popularity.setText(showInfo.getShPopularity());
        Picasso.with(this).load(images).fit().centerInside().into(iv);
        setTitle(showInfo.getShName());
        checkIfMovieInDatabase(showInfo.getShId());
        shId = showInfo.getShId();
        tView = findViewById(R.id.recycler_video);
        trailers = new ArrayList<>();
        URL url = NetworkUtils.videoURL(shId);
        new VideoTask().execute(url);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.getString(R.string.sp_title),showInfo.getShName()+"\n"+showInfo.getShRating());
        editor.apply();

        Intent widget_intent = new Intent(this, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(widget_intent);

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void checkIfMovieInDatabase(String mvId) {
        ShowEntity showEntity = showViewModel.checkMovie(mvId);
        if(showEntity!=null)
        {
            button.setText(this.getString(R.string.unfav));
        }
        else
        {
            button.setText(this.getString(R.string.fav));
        }
    }

    public void addToFavorites(View view) {
        if(button.getText().toString().equalsIgnoreCase(this.getString(R.string.fav))){
            ShowEntity showEntity = new ShowEntity(showInfo.getShId(),showInfo.getShName(),showInfo.getShOverview(),showInfo.getShPoster_path(),showInfo.getShPopularity(),showInfo.getShRating());
            showViewModel.insert(showEntity);
            button.setText(this.getString(R.string.unfav));
        }
        else {
            ShowEntity showEntity = new ShowEntity(showInfo.getShId(),showInfo.getShName(),showInfo.getShOverview(),showInfo.getShPoster_path(),showInfo.getShPopularity(),showInfo.getShRating());
            showViewModel.delete(showEntity);
            button.setText(this.getString(R.string.fav));
        }
    }

   public void reviews(View view) {
       Intent intent = new Intent(this,ReviewActivity.class);
       intent.putExtra(SHOW_ID,shId);
       startActivity(intent);
   }


    public class VideoTask extends AsyncTask<URL,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String video = null;
            video = NetworkUtils.getConnection(url);
            return video;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            videoData=s;
            trailers = TrailerParse.getTrailer(videoData);
            trailerAdapter = new TrailerAdapter(ShowDetailActivity.this,trailers,ShowDetailActivity.this);

            trailerAdapter.getVideoData(trailers);
            tView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            tView.setAdapter(trailerAdapter);

        }
    }
}
