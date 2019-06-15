package com.example.entertainmentzone.model.review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewParse {
    public static List<ReviewInfo> getReview(String json)
    {
        List<ReviewInfo> review = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("results");
            for(int i =0;i<result.length();i++)
            {
                ReviewInfo reviewInfo = new ReviewInfo();
                JSONObject reviewData = result.getJSONObject(i);
                reviewInfo.setAuthor(reviewData.getString("author"));
                reviewInfo.setContent(reviewData.getString("content"));

                review.add(reviewInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return review;
    }
}

