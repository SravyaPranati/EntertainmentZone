package com.example.entertainmentzone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.entertainmentzone.R;
import com.example.entertainmentzone.model.review.ReviewInfo;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context ct;
    private List<ReviewInfo> reviews;

    public ReviewAdapter(Context ct, List<ReviewInfo> reviews) {
        this.ct = ct;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ct).inflate(R.layout.review_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.review.setText(reviews.get(i).getContent());
            viewHolder.reviewAuthor.setText(reviews.get(i).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

     public void getReviewData(List<ReviewInfo> reviewInfoList)
     {
         reviews=reviewInfoList;
         notifyDataSetChanged();
     }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView review, reviewAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.review);
            reviewAuthor = itemView.findViewById(R.id.author);
        }


    }
}
