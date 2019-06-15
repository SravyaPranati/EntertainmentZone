package com.example.entertainmentzone.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.entertainmentzone.R;
import com.example.entertainmentzone.adapters.ReviewAdapter;
import com.example.entertainmentzone.model.review.ReviewInfo;
import com.example.entertainmentzone.model.review.ReviewParse;
import com.example.entertainmentzone.network.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    String reviewData = "";
    private List<ReviewInfo> reviewInfos;
    String shId;
    ReviewAdapter rvAdapter;
    RecyclerView rvView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        shId = getIntent().getStringExtra(ShowDetailActivity.SHOW_ID);
        rvView = findViewById(R.id.recycler_review);
        reviewInfos = new ArrayList<>();
        setTitle((R.string.review));
        URL url = NetworkUtils.reviewURL(shId);
        new ReviewTask().execute(url);
    }
    public class ReviewTask extends AsyncTask<URL,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String review = null;
            review = NetworkUtils.getConnection(url);
            return review;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            reviewData=s;
            reviewInfos = ReviewParse.getReview(reviewData);
            rvAdapter = new ReviewAdapter(ReviewActivity.this,reviewInfos);
            rvAdapter.getReviewData(reviewInfos);
            rvView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvView.setAdapter(rvAdapter);
        }
    }
}
