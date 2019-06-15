package com.example.entertainmentzone.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entertainmentzone.R;
import com.example.entertainmentzone.model.video.TrailerInfo;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context ct;
    private Activity application;
    private List<TrailerInfo> trailers;

    public TrailerAdapter(Context ct, List<TrailerInfo> trailers,Activity application) {
        this.ct = ct;
        this.trailers = trailers;
        this.application = application;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ct).inflate(R.layout.video_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tView.setText(trailers.get(i).getShTitle());
    }

    public void getVideoData(List<TrailerInfo> trailerItemList) {
        trailers = trailerItemList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tView;
        private ImageView iView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tView = itemView.findViewById(R.id.video_title);
            tView.setOnClickListener(this);
            iView = itemView.findViewById(R.id.share_video);
            iView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.video_title) {
                int position = getAdapterPosition();
                String keyTrailer = trailers.get(position).getShVideoKey();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(("http://www.youtube.com/watch?v=" + keyTrailer)));
                ct.startActivity(intent);
            } else {
                int position = getAdapterPosition();
                String keyTrailer = trailers.get(position).getShVideoKey();
                Uri uri = Uri.parse(("http://www.youtube.com/watch?v=" + keyTrailer));

                ShareCompat.IntentBuilder.from(application).setType(ct.getString(R.string.tt)).setChooserTitle(ct.getString(R.string.sharefriends)).setText(uri.toString()).startChooser();
            }


        }
    }
}
