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
import com.shubham.movies.models.castAndCrew.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<Cast> castList;

    private Activity activity;

    /**
     * constructor is called
     * @param castList
     * @param activity
     */
    public CastAdapter(List<Cast> castList, Activity activity)
    {
        this.castList = castList;
        this.activity = activity;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_name;
        private TextView tv_actName;
        private ImageView iv_image;

        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_actName = view.findViewById(R.id.tv_act_name);
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
    public CastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_cast_crew_item, parent, false);

        return new CastAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CastAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Cast cast = castList.get(position);

        if (cast != null) {
            if (cast.getProfile_path() != null) {
                String photo_url = GET_MOVIE_POSTER + cast.getProfile_path();
                if (activity != null && holder.iv_image != null && photo_url != null) {
                    Glide.with(activity).load(photo_url)
                            .into(holder.iv_image);
                }
            }

            holder.tv_name.setText(cast.getName());
            holder.tv_actName.setText(cast.getCharacter());
        }
    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return castList.size();
    }
}

