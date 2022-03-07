package com.shubham.movies.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.movies.R;
import com.shubham.movies.models.keywords.Keywords;

import java.util.List;

public class KeywordsAdapter extends RecyclerView.Adapter<KeywordsAdapter.MyViewHolder> {

    private List<Keywords> keywordsList;

    private Activity activity;

    /**
     * constructor is called
     * @param keywordsList
     * @param activity
     */
    public KeywordsAdapter(List<Keywords> keywordsList, Activity activity)
    {
        this.keywordsList = keywordsList;
        this.activity = activity;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_keyword;

        public MyViewHolder(View view) {
            super(view);
            tv_keyword = view.findViewById(R.id.tv_keyword);
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
    public KeywordsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_keywords_item, parent, false);

        return new KeywordsAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull KeywordsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Keywords keywords = keywordsList.get(position);

        if (keywords != null) {
            holder.tv_keyword.setText(keywords.getName());
        }

    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return keywordsList.size();
    }
}



