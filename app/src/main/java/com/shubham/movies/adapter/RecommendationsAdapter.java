package com.shubham.movies.adapter;

import static com.shubham.movies.networks.Urls.GET_MOVIE_POSTER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shubham.movies.R;
import com.shubham.movies.models.recommendations.RecommendationsModel;
import com.shubham.movies.models.recommendations.Results;

import java.text.DecimalFormat;
import java.util.List;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.MyViewHolder> {

    private List<Results> resultsList;

    private Activity activity;

    private DecimalFormat format = new DecimalFormat("##");

    /**
     * constructor is called
     * @param resultsList
     * @param activity
     */
    public RecommendationsAdapter(List<Results> resultsList, Activity activity) {
        this.resultsList = resultsList;
        this.activity = activity;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_recMovie;
        private TextView tv_recPercentage;
        private ImageView iv_image;

        public MyViewHolder(View view) {
            super(view);
            tv_recMovie = view.findViewById(R.id.tv_rec_movie);
            tv_recPercentage = view.findViewById(R.id.tv_rec_percentage);
            iv_image = view.findViewById(R.id.tv_image);
        }

        @Override
        public void onClick(View v) {
        }
    }

    /**
     * MyViewHolder attached to xml
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecommendationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_recommendation_item, parent, false);

        return new RecommendationsAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecommendationsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Results results = resultsList.get(position);

        if (results != null) {
            if (results.getBackdrop_path() != null) {
                String photo_url = GET_MOVIE_POSTER + results.getBackdrop_path();
                if (activity != null && holder.iv_image != null && photo_url != null) {
                    Glide.with(activity).load(photo_url)
                            .into(holder.iv_image);
                }
            }

            holder.tv_recMovie.setText(results.getTitle());

            int percentage = (int) (results.getVote_average() * 10);
            holder.tv_recPercentage.setText(String.format("%s", format.format(percentage)) + " %");
        }
    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}

