package com.example.entertainmentzone.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.entertainmentzone.R;
import com.example.entertainmentzone.activities.ShowDetailActivity;
import com.example.entertainmentzone.model.show.ShowInfo;
import com.squareup.picasso.Picasso;

import java.time.format.ResolverStyle;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {
    private Context ct;
    private List<ShowInfo> show;

    public ShowAdapter(Context ct, List<ShowInfo> show) {
        this.ct = ct;
        this.show = show;
    }

    public  void setShowData(List<ShowInfo> showInfoList)
    {
        show = showInfoList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ct).inflate(R.layout.image_view,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ShowInfo showInfo = show.get(i);
        String image = showInfo.getShPoster_path();
        Picasso.with(ct).load(image).fit().centerInside().into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return show.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_id);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            ShowInfo si = show.get(pos);
            Intent intent = new Intent(ct, ShowDetailActivity.class);
            intent.putExtra(ct.getString(R.string.obj),si);
            intent.putExtra(ct.getString(R.string.p_path),si.getShPoster_path());
            ct.startActivity(intent);


        }
    }
}
