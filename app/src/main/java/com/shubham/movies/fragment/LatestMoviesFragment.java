package com.shubham.movies.fragment;

import static com.shubham.movies.utils.AppConstants.API_KEY;
import static com.shubham.movies.utils.AppConstants.CHECK_SCREEN;
import static com.shubham.movies.utils.AppConstants.LATEST_MOVIE_SELECTED;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shubham.movies.R;
import com.shubham.movies.activity.MainActivity;
import com.shubham.movies.adapter.LatestMoviesAdapter;
import com.shubham.movies.listeners.PaginationScrollListener;
import com.shubham.movies.models.latestAndPopularMovie.MovieResultModel;
import com.shubham.movies.models.apiResponse.ResponseModel;
import com.shubham.movies.networks.APIClient;
import com.shubham.movies.networks.APIInterface;
import com.shubham.movies.utils.AppCommonMethods;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LatestMoviesFragment extends Fragment implements LatestMoviesAdapter.OnClickInterface {

    private APIInterface apiInterface;

    private RecyclerView rv_latestMoviesList;

    private List<MovieResultModel> latestMoviesList = new ArrayList<>();

    private LatestMoviesAdapter latestMoviesAdapter;

    private int pageNumber = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 0; //your totalPages page

    private Activity activity;

    private View view;

    public LatestMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_latest_movies, container, false);

        initController();

        return view;
    }

    /**
     * initialization of all views used
     */
    private void initController() {
        activity = getActivity();
        rv_latestMoviesList = view.findViewById(R.id.rv_latest_movies_list);
        setViewToLatestMoviesList();
        getLatestMoviesList(pageNumber, false);
    }

    /**
     * latest movie api called
     * @param page
     * @param isLoadMore
     */
    private void getLatestMoviesList(int page, final boolean isLoadMore) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        isLoading = true;
        Call<ResponseModel> call = apiInterface.callGetMoviesList(API_KEY,
                "en-US",
                "latest.desc",
                false,
                false,
                page,
                "flatrate");

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    isLoading = false;
                    totalPages = response.body().getTotal_pages();
                    if (!isLoadMore) {
                        latestMoviesList.clear();
                    }
                    latestMoviesList.addAll(getStringMovieResultModel(new Gson().toJson(response.body().getResults())));
                    Log.e("strJsonOfBody1", new Gson().toJson(latestMoviesList));
                    latestMoviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * response.toString converted into model
     * @param jsonArray
     * @return
     */
    List<MovieResultModel> getStringMovieResultModel(String jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MovieResultModel>>() {
        }.getType();
        List<MovieResultModel> myModelList = gson.fromJson(jsonArray, listType);
        return myModelList;
    }

    /**
     * data set to recycler view
     */
    private void setViewToLatestMoviesList() {
        latestMoviesAdapter = new LatestMoviesAdapter(latestMoviesList, activity, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
        rv_latestMoviesList.setLayoutManager(mLayoutManager);
        rv_latestMoviesList.setItemAnimator(new DefaultItemAnimator());
        rv_latestMoviesList.setAdapter(latestMoviesAdapter);

        /**
         * scroll listener attached to recycler view for pagination
         */
        rv_latestMoviesList.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                pageNumber += 1;
                if (pageNumber == totalPages) {
                    if (pageNumber != 1) {
                        Log.e("Records ", "no more");
                    }
                }
                else {
                    getLatestMoviesList(pageNumber, true);
                }
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                if(pageNumber<=totalPages-1){
                    isLastPage=false;
                }else {
                    isLastPage=true;
                }
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    /**
     * get details of movie on click
     * @param v
     * @param position
     */
    @Override
    public void itemOnClick(View v, int position) {
        CHECK_SCREEN = 1;
        AppCommonMethods.putStringPref(LATEST_MOVIE_SELECTED, new Gson().toJson(latestMoviesList.get(position)), activity);
        ((MainActivity) requireActivity()).movieDetailsSelected();
    }
}