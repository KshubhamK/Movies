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
import com.shubham.movies.adapter.PopularMoviesAdapter;
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
public class PopularMoviesFragment extends Fragment implements PopularMoviesAdapter.OnClickInterface {

    private APIInterface apiInterface;

    private RecyclerView rv_popularMoviesList;

    private List<MovieResultModel> popularMoviesList = new ArrayList<>();

    private PopularMoviesAdapter popularMoviesAdapter;

    private int pageNumber = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 0; //your totalPages page

    private Activity activity;

    private View view;


    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        initController();

        return view;
    }

    /**
     * initialization of all views used
     */
    private void initController() {
        activity = getActivity();
        rv_popularMoviesList = view.findViewById(R.id.rv_popular_movies_list);
        setViewToPopularMoviesList();
        getPopularMoviesList(pageNumber, false);
    }

    /**
     * popular movie api called
     * @param page
     * @param isLoadMore
     */
    private void getPopularMoviesList(int page, final boolean isLoadMore) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        isLoading = true;
        Call<ResponseModel> call = apiInterface.callGetMoviesList(API_KEY,
                "en-US",
                "popularity.desc",
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
                        popularMoviesList.clear();
                    }
                    popularMoviesList.addAll(getStringMovieResultModel(new Gson().toJson(response.body().getResults())));
                    Log.e("strJsonOfBody2", new Gson().toJson(popularMoviesList));
                    popularMoviesAdapter.notifyDataSetChanged();
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
    private void setViewToPopularMoviesList() {
        popularMoviesAdapter = new PopularMoviesAdapter(popularMoviesList, activity, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
        rv_popularMoviesList.setLayoutManager(mLayoutManager);
        rv_popularMoviesList.setItemAnimator(new DefaultItemAnimator());
        rv_popularMoviesList.setAdapter(popularMoviesAdapter);

        /**
         * scroll listener attached to recycler view for pagination
         */
        rv_popularMoviesList.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
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
                    getPopularMoviesList(pageNumber, true);
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
        CHECK_SCREEN = 2;
        AppCommonMethods.putStringPref(LATEST_MOVIE_SELECTED, new Gson().toJson(popularMoviesList.get(position)), activity);
        ((MainActivity) requireActivity()).movieDetailsSelected();
    }
}