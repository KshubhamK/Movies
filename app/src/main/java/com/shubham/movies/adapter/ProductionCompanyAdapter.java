package com.shubham.movies.adapter;

import static com.shubham.movies.networks.Urls.GET_MOVIE_POSTER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shubham.movies.R;
import com.shubham.movies.models.keywords.Keywords;
import com.shubham.movies.models.latestAndPopularMovieDetails.Production_companies;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.MyViewHolder> {

    private List<Production_companies> production_companiesList;

    private Activity activity;

    /**
     * constructor is called
     * @param production_companiesList
     * @param activity
     */
    public ProductionCompanyAdapter(List<Production_companies> production_companiesList, Activity activity)
    {
        this.production_companiesList = production_companiesList;
        this.activity = activity;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_image;

        public MyViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
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
    public ProductionCompanyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_company_litem, parent, false);

        return new ProductionCompanyAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Production_companies production_companies = production_companiesList.get(position);

        if (production_companies != null) {
            if (production_companies.getLogo_path() != null) {
                String photo_url = GET_MOVIE_POSTER + production_companies.getLogo_path();
                if (activity != null && holder.iv_image != null && photo_url != null) {
                    Glide.with(activity).load(photo_url)
                            .into(holder.iv_image);
                }
            }
        }

    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return production_companiesList.size();
    }
}
