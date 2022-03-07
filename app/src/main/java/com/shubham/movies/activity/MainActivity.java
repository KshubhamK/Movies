package com.shubham.movies.activity;

import static com.shubham.movies.utils.AppConstants.CHECK_SCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shubham.movies.R;
import com.shubham.movies.fragment.LatestMoviesFragment;
import com.shubham.movies.fragment.MoviesDetailsFragment;
import com.shubham.movies.fragment.PopularMoviesFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_latest;
    private LinearLayout ll_popular;

    private ImageView iv_latestMovie;
    private ImageView iv_popularMovie;
    private ImageView iv_movieDetails;

    private TextView tv_latestMovie;
    private TextView tv_popularMovie;
    private TextView tv_movieDetails;

    private LatestMoviesFragment latestMoviesFragment;
    private PopularMoviesFragment popularMoviesFragment;
    private MoviesDetailsFragment moviesDetailsFragment;

    private String backStateName = "";

    private static final String TAG = "LatestMoviesFragment";

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initController();
    }

    /**
     * initialization of all views used
     */
    private void initController() {
        activity = MainActivity.this;
        ll_latest = findViewById(R.id.ll_latest);
        ll_popular = findViewById(R.id.ll_popular);
        iv_latestMovie = findViewById(R.id.iv_latest_movie);
        iv_popularMovie = findViewById(R.id.iv_popular_movie);
        iv_movieDetails = findViewById(R.id.iv_movie_details);
        tv_latestMovie = findViewById(R.id.tv_latest_movie);
        tv_popularMovie = findViewById(R.id.tv_popular_movie);
        tv_movieDetails = findViewById(R.id.tv_movie_details);

        ll_latest.setOnClickListener(this);
        ll_popular.setOnClickListener(this);

        latestMoviesFragment = new LatestMoviesFragment();
        popularMoviesFragment = new PopularMoviesFragment();
        moviesDetailsFragment = new MoviesDetailsFragment();

        setFragment(latestMoviesFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_latest:
                latestMoviesSelected();
                break;

            case R.id.ll_popular:
                popularMoviesSelected();
                break;
        }
    }

    /**
     * to set the fragment in activity
     * @param fragment
     */
    public void setFragment(Fragment fragment) {
        String fragmentName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && !backStateName.equalsIgnoreCase(fragmentName)) {
            backStateName = fragment.getClass().getName();
            //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fl_container, fragment, TAG);
            ft.commit();
        }
    }

    /**
     * after changing every fragment, bottom views get reset to its original view
     */
    private void resetBottomView() {
        iv_latestMovie.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.black_strick_stroke));
        iv_popularMovie.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.black_strick_stroke));
        iv_movieDetails.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.black_strick_stroke));
        tv_latestMovie.setTextColor(getResources().getColor(R.color.black_strick_stroke));
        tv_popularMovie.setTextColor(getResources().getColor(R.color.black_strick_stroke));
        tv_movieDetails.setTextColor(getResources().getColor(R.color.black_strick_stroke));
    }

    /**
     * latest movie tab selected
     */
    public void latestMoviesSelected() {
        setFragment(latestMoviesFragment);
        resetBottomView();
        iv_latestMovie.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.purple_500));
        tv_latestMovie.setTextColor(getResources().getColor(R.color.purple_500));
    }

    /**
     * popular movie tab selected
     */
    public void popularMoviesSelected() {
        setFragment(popularMoviesFragment);
        resetBottomView();
        iv_popularMovie.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.purple_500));
        tv_popularMovie.setTextColor(getResources().getColor(R.color.purple_500));
    }

    /**
     * movie detail selected
     */
    public void movieDetailsSelected() {
        setFragment(moviesDetailsFragment);
        resetBottomView();
        iv_movieDetails.setBackgroundTintList(AppCompatResources.getColorStateList(activity, R.color.purple_500));
        tv_movieDetails.setTextColor(getResources().getColor(R.color.purple_500));
    }

    /**
     * on back pressed
     */
    @Override
    public void onBackPressed() {
        try {
            if (latestMoviesFragment.isVisible()) {
                super.onBackPressed();
            }

            if (popularMoviesFragment.isVisible()) {
                super.onBackPressed();
            }

            if (moviesDetailsFragment.isVisible()) {
                if (CHECK_SCREEN == 1) {
                    latestMoviesSelected();
                }
                else if (CHECK_SCREEN == 2) {
                    popularMoviesSelected();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}