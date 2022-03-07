package com.shubham.movies.adapter;

import static com.shubham.movies.networks.Urls.GET_MOVIE_POSTER;
import static com.shubham.movies.utils.AppConstants.CONVERTED_DATE_FORMAT;
import static com.shubham.movies.utils.AppConstants.ORIGINAL_DATE_FORMAT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shubham.movies.R;
import com.shubham.movies.models.latestAndPopularMovie.MovieResultModel;
import com.shubham.movies.utils.AppCommonMethods;

import java.util.List;

public class LatestMoviesAdapter extends RecyclerView.Adapter<LatestMoviesAdapter.MyViewHolder> {

    private List<MovieResultModel> latestMoviesList;

    private Activity activity;

    private LatestMoviesAdapter.OnClickInterface onClickInterface;

    /**
     * constructor is called
     * @param latestMoviesList
     * @param activity
     * @param onClickInterface
     */
    public LatestMoviesAdapter(List<MovieResultModel> latestMoviesList, Activity activity,
                               LatestMoviesAdapter.OnClickInterface onClickInterface)
    {
        this.latestMoviesList = latestMoviesList;
        this.activity = activity;
        this.onClickInterface = onClickInterface;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_movieTitle;
        private TextView tv_date;
        private TextView tv_percentage;
        private ImageView iv_image;
        private CircularProgressBar cp_progressPercentage;
        private LinearLayout ll_main;

        public MyViewHolder(View view) {
            super(view);
            tv_movieTitle = view.findViewById(R.id.tv_movie_title);
            tv_date = view.findViewById(R.id.tv_date);
            tv_percentage = view.findViewById(R.id.tv_percentage);
            iv_image = view.findViewById(R.id.tv_image);
            cp_progressPercentage = view.findViewById(R.id.cp_progress_percentage);
            ll_main = view.findViewById(R.id.ll_main);
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
    public LatestMoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_movies_list, parent, false);

        return new LatestMoviesAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull LatestMoviesAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MovieResultModel movieResultModel = latestMoviesList.get(position);

        if (movieResultModel != null) {
            holder.tv_movieTitle.setText(movieResultModel.getTitle());
            holder.tv_date.setText(AppCommonMethods.convertDateFormat(ORIGINAL_DATE_FORMAT,
                    CONVERTED_DATE_FORMAT, movieResultModel.getRelease_date()));

            if (movieResultModel.getPoster_path() != null) {
                String photo_url = GET_MOVIE_POSTER + movieResultModel.getPoster_path();
                if (activity != null && holder.iv_image != null && photo_url != null) {
                    Glide.with(activity).load(photo_url)
                            .into(holder.iv_image);
                }
            }

            int percentage = (int) (movieResultModel.getVote_average() * 10);
            holder.tv_percentage.setText(percentage + " %");
            holder.cp_progressPercentage.setProgress(percentage);

            if (percentage >= 30) {
                holder.cp_progressPercentage.setProgressBarColor(activity.getResources().getColor(R.color.red));
            }
            if (percentage >= 65) {
                holder.cp_progressPercentage.setProgressBarColor(activity.getResources().getColor(R.color.green));
            }
            else {
                holder.cp_progressPercentage.setProgressBarColor(activity.getResources().getColor(R.color.yellow));
            }
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.itemOnClick(v, position);
            }
        });
    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return latestMoviesList.size();
    }

    /**
     * interface created for onClick
     */
    public interface OnClickInterface
    {
        void itemOnClick(View v, int position);
    }
}
