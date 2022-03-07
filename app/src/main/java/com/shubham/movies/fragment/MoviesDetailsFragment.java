package com.shubham.movies.fragment;

import static com.shubham.movies.networks.Urls.GET_MOVIE_POSTER;
import static com.shubham.movies.utils.AppConstants.API_KEY;
import static com.shubham.movies.utils.AppConstants.LATEST_MOVIE_SELECTED;
import static com.shubham.movies.utils.AppConstants.ORIGINAL_DATE_FORMAT;
import static com.shubham.movies.utils.AppConstants.YEAR;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shubham.movies.R;
import com.shubham.movies.adapter.CastAdapter;
import com.shubham.movies.adapter.CrewAdapter;
import com.shubham.movies.adapter.KeywordsAdapter;
import com.shubham.movies.adapter.ProductionCompanyAdapter;
import com.shubham.movies.adapter.RecommendationsAdapter;
import com.shubham.movies.listeners.PaginationScrollListener;
import com.shubham.movies.models.apiResponse.ResponseModel;
import com.shubham.movies.models.castAndCrew.Cast;
import com.shubham.movies.models.castAndCrew.CastAndCrewModel;
import com.shubham.movies.models.castAndCrew.Crew;
import com.shubham.movies.models.keywords.Keywords;
import com.shubham.movies.models.keywords.KeywordsModel;
import com.shubham.movies.models.latestAndPopularMovie.MovieResultModel;
import com.shubham.movies.models.latestAndPopularMovieDetails.LatestAndPopularMovieModel;
import com.shubham.movies.models.recommendations.RecommendationsModel;
import com.shubham.movies.models.recommendations.Results;
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
public class MoviesDetailsFragment extends Fragment {

    private APIInterface apiInterface;

    private LinearLayout ll_mainPosterView;

    private ImageView iv_backgroundPoster;
    private ImageView iv_moviePoster;

    private TextView tv_movieName;
    private TextView tv_movieYear;
    private TextView tv_percentage;
    private TextView tv_time;
    private TextView tv_language;
    private TextView tv_type;
    private TextView tv_tagline;
    private TextView tv_details;
    private TextView tv_castNot;
    private TextView tv_crewNot;
    private TextView tv_recNot;
    private TextView tv_keywordsNot;
    private TextView tv_compNot;
    private TextView tv_status;
    private TextView tv_originalLanguage;
    private TextView tv_budget;
    private TextView tv_revenue;

    private CircularProgressBar cp_userScore;

    private RecyclerView rv_castList;
    private RecyclerView rv_crewList;
    private RecyclerView rv_recommendationList;
    private RecyclerView rv_keywordsList;
    private RecyclerView rv_companyList;

    private CastAdapter castAdapter;
    private CrewAdapter crewAdapter;
    private RecommendationsAdapter recommendationsAdapter;
    private KeywordsAdapter keywordsAdapter;
    private ProductionCompanyAdapter productionCompanyAdapter;

    private MovieResultModel movieResultModel;
    private LatestAndPopularMovieModel latestAndPopularMovieModel;
    private List<Cast> castList = new ArrayList<>();
    private List<Crew> crewList = new ArrayList<>();
    private List<Results> recommendationsModelList = new ArrayList<>();
    private List<Keywords> keywordsList = new ArrayList<>();

    private int pageNumber = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 0; //your totalPages page

    private Activity activity;

    private View view;

    public MoviesDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies_details, container, false);

        activity = getActivity();
        movieResultModel = new Gson().fromJson(
                AppCommonMethods.getStringPref(LATEST_MOVIE_SELECTED, activity),
                MovieResultModel.class);

        initController();

        return view;
    }

    /**
     * initialization of all views used
     */
    private void initController() {
        ll_mainPosterView = view.findViewById(R.id.ll_main_poster_view);
        iv_backgroundPoster = view.findViewById(R.id.iv_background_poster);
        iv_moviePoster = view.findViewById(R.id.iv_movie_poster);
        tv_movieName = view.findViewById(R.id.tv_movie_name);
        tv_movieYear = view.findViewById(R.id.tv_movie_year);
        tv_percentage = view.findViewById(R.id.tv_percentage);
        tv_time = view.findViewById(R.id.tv_time);
        tv_language = view.findViewById(R.id.tv_language);
        tv_type = view.findViewById(R.id.tv_type);
        tv_tagline = view.findViewById(R.id.tv_tagline);
        tv_details = view.findViewById(R.id.tv_details);
        tv_castNot = view.findViewById(R.id.tv_cast_not);
        tv_crewNot = view.findViewById(R.id.tv_crew_not);
        tv_recNot = view.findViewById(R.id.tv_rec_not);
        tv_keywordsNot = view.findViewById(R.id.tv_keywords_not);
        tv_compNot = view.findViewById(R.id.tv_comp_not);
        tv_status = view.findViewById(R.id.tv_status);
        tv_budget = view.findViewById(R.id.tv_budget);
        tv_revenue = view.findViewById(R.id.tv_revenue);
        tv_originalLanguage = view.findViewById(R.id.tv_original_language);
        cp_userScore = view.findViewById(R.id.cp_user_score);
        rv_castList = view.findViewById(R.id.rv_cast_list);
        rv_crewList = view.findViewById(R.id.rv_crew_list);
        rv_recommendationList = view.findViewById(R.id.rv_recommendation_list);
        rv_keywordsList = view.findViewById(R.id.rv_keywords_list);
        rv_companyList = view.findViewById(R.id.rv_company_list);

        getMovieDetails(movieResultModel.getId());
        getCastAndCrewDetails(movieResultModel.getId());
        setViewToRecommendationsList();
        getRecommendations(movieResultModel.getId(), pageNumber, false);
        getKeywordsList(movieResultModel.getId());
    }

    /**
     * movie details API called based on movie_id
     * @param id
     */
    private void getMovieDetails(String id) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        Call<JsonObject> call = apiInterface.callGetMovieDetails(id,
                API_KEY);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        latestAndPopularMovieModel = new Gson().fromJson(
                                new Gson().toJson(response.body()), LatestAndPopularMovieModel.class);
                        Log.e("latestAndPopularMovie", new Gson().toJson(latestAndPopularMovieModel));
                        setViewToMovieDetailScreen();
                        setViewToCompanyList();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * data set ot movie detail page
     */
    private void setViewToMovieDetailScreen() {
        if (latestAndPopularMovieModel != null) {

            iv_backgroundPoster.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    Log.e("X= ", event.getX() + " Y= " + event.getY());

                    iv_backgroundPoster.buildDrawingCache();
                    Bitmap bitmap = iv_backgroundPoster.getDrawingCache();
                    int projectedX = (int) ((double) x * ((double) bitmap.getWidth() / (double) iv_backgroundPoster.getWidth()));
                    int projectedY = (int) ((double) y * ((double) bitmap.getHeight() / (double) iv_backgroundPoster.getHeight()));

                    int pixel = bitmap.getPixel(projectedX, projectedY);
                    int redValue = Color.red(pixel);
                    int blueValue = Color.blue(pixel);
                    int greenValue = Color.green(pixel);

                    Log.e("touched color: ",  "#" + Integer.toHexString(redValue) +
                            Integer.toHexString(greenValue) + Integer.toHexString(blueValue));
                    ll_mainPosterView.setBackgroundColor(pixel);

                    return false;
                }
            });

            String backgroundPoster = GET_MOVIE_POSTER + latestAndPopularMovieModel.getBackdrop_path();
            if (activity != null && iv_backgroundPoster != null && backgroundPoster != null) {
                Glide.with(activity).load(backgroundPoster)
                        .into(iv_backgroundPoster);
            }

            String moviePoster = GET_MOVIE_POSTER + latestAndPopularMovieModel.getPoster_path();
            if (activity != null && iv_moviePoster != null && moviePoster != null) {
                Glide.with(activity).load(moviePoster)
                        .into(iv_moviePoster);
            }

            tv_movieName.setText(latestAndPopularMovieModel.getTitle());
            tv_movieYear.setText("(" + AppCommonMethods.convertDateFormat(ORIGINAL_DATE_FORMAT,
                    YEAR, latestAndPopularMovieModel.getRelease_date()) + ")");

            int percentage = (int) (movieResultModel.getVote_average() * 10);
            tv_percentage.setText(percentage + " %");
            cp_userScore.setProgress(percentage);

            if (percentage >= 30) {
                cp_userScore.setProgressBarColor(activity.getResources().getColor(R.color.red));
            }
            if (percentage >= 65) {
                cp_userScore.setProgressBarColor(activity.getResources().getColor(R.color.green));
            }
            else {
                cp_userScore.setProgressBarColor(activity.getResources().getColor(R.color.yellow));
            }

            tv_time.setText(AppCommonMethods.convertFromMinutesToHours(latestAndPopularMovieModel.getRuntime()));

            int langLength = latestAndPopularMovieModel.getSpoken_languages().size();
            for (int i = 0 ; i < latestAndPopularMovieModel.getSpoken_languages().size() ; i++) {
                if (i != (langLength-1)) {
                    tv_language.append(latestAndPopularMovieModel.getSpoken_languages().get(i).getEnglish_name() +
                            ", ");
                }
                else {
                    tv_language.append(latestAndPopularMovieModel.getSpoken_languages().get(i).getEnglish_name());
                }
            }

            int genresLength = latestAndPopularMovieModel.getGenres().size();
            for (int i = 0 ; i < latestAndPopularMovieModel.getGenres().size() ; i++) {
                if (i != (genresLength-1)) {
                    tv_type.append(latestAndPopularMovieModel.getGenres().get(i).getName() +
                            ", ");
                }
                else {
                    tv_type.append(latestAndPopularMovieModel.getGenres().get(i).getName());
                }
            }

            tv_tagline.setText(latestAndPopularMovieModel.getTagline());

            tv_details.setText(latestAndPopularMovieModel.getOverview());

            tv_status.setText(latestAndPopularMovieModel.getStatus());

            if (latestAndPopularMovieModel.getOriginal_language().equals("en")) {
                tv_originalLanguage.setText("English");
            }
            else if (latestAndPopularMovieModel.getOriginal_language().equals("hi")) {
                tv_originalLanguage.setText("Hindi");
            }

            tv_budget.setText("$ " + AppCommonMethods.currencyFormat(latestAndPopularMovieModel.getBudget()));

            tv_revenue.setText("$ " + AppCommonMethods.currencyFormat(latestAndPopularMovieModel.getRevenue()));
        }
    }

    /**
     * view is set to production company recycler view
     */
    private void setViewToCompanyList() {
        if (latestAndPopularMovieModel.getProduction_companies().size() > 0) {
            rv_companyList.setVisibility(View.VISIBLE);
            tv_compNot.setVisibility(View.GONE);
            productionCompanyAdapter = new ProductionCompanyAdapter(latestAndPopularMovieModel.getProduction_companies(),
                    activity);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity,
                    LinearLayoutManager.HORIZONTAL, false);
            rv_companyList.setLayoutManager(mLayoutManager);
            rv_companyList.setItemAnimator(new DefaultItemAnimator());
            rv_companyList.setAdapter(productionCompanyAdapter);
        }
        else {
            rv_companyList.setVisibility(View.GONE);
            tv_compNot.setVisibility(View.VISIBLE);
        }
    }

    /**
     * cast and crew API called
     * @param id
     */
    private void getCastAndCrewDetails(String id) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        Call<CastAndCrewModel> call = apiInterface.callGetCastAndCrewDetails(id,
                API_KEY);

        Log.e("apiKey", call.request().url().toString());
        call.enqueue(new Callback<CastAndCrewModel>() {
            @Override
            public void onResponse(Call<CastAndCrewModel> call, Response<CastAndCrewModel> response) {
                CastAndCrewModel castAndCrewModel1 = response.body();
                if (castAndCrewModel1 != null) {
                    castList = castAndCrewModel1.getCast();
                    crewList = castAndCrewModel1.getCrew();

                    if (castList.size() > 0) {
                        rv_castList.setVisibility(View.VISIBLE);
                        tv_castNot.setVisibility(View.GONE);
                        setViewToMovieCastList();
                    }
                    else {
                        rv_castList.setVisibility(View.GONE);
                        tv_castNot.setVisibility(View.VISIBLE);
                    }

                    if (crewList.size() > 0) {
                        rv_crewList.setVisibility(View.VISIBLE);
                        tv_crewNot.setVisibility(View.GONE);
                        setViewToMovieCrewList();
                    }
                    else {
                        rv_crewList.setVisibility(View.GONE);
                        tv_crewNot.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CastAndCrewModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * data set to cast recycler view
     */
    private void setViewToMovieCastList() {
        castAdapter = new CastAdapter(castList, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false);
        rv_castList.setLayoutManager(mLayoutManager);
        rv_castList.setItemAnimator(new DefaultItemAnimator());
        rv_castList.setAdapter(castAdapter);
    }

    /**
     * data set to crew recycler view
     */
    private void setViewToMovieCrewList() {
        crewAdapter = new CrewAdapter(crewList, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false);
        rv_crewList.setLayoutManager(mLayoutManager);
        rv_crewList.setItemAnimator(new DefaultItemAnimator());
        rv_crewList.setAdapter(crewAdapter);
    }

    /**
     * recommendation api called
     * @param id
     * @param page
     * @param isLoadMore
     */
    private void getRecommendations(String id, int page, final boolean isLoadMore) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        isLoading = true;
        Call<ResponseModel> call = apiInterface.callGetRecommendations(id,
                API_KEY,
                page);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    isLoading = false;
                    totalPages = response.body().getTotal_pages();
                    if (!isLoadMore) {
                        recommendationsModelList.clear();
                    }
                    recommendationsModelList.addAll(getStringRecommendationsModel(new Gson().toJson(response.body().getResults())));
                    Log.e("recommendationsModel", new Gson().toJson(recommendationsModelList));

                    if (recommendationsModelList.size() > 0) {
                        rv_recommendationList.setVisibility(View.VISIBLE);
                        tv_recNot.setVisibility(View.GONE);
                    }
                    else {
                        rv_recommendationList.setVisibility(View.GONE);
                        tv_recNot.setVisibility(View.VISIBLE);
                    }

                    recommendationsAdapter.notifyDataSetChanged();
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
    List<Results> getStringRecommendationsModel(String jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Results>>() {
        }.getType();
        List<Results> myModelList = gson.fromJson(jsonArray, listType);
        return myModelList;
    }

    /**
     * view set to recommendation recycler view
     */
    private void setViewToRecommendationsList() {
        recommendationsAdapter = new RecommendationsAdapter(recommendationsModelList, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false);
        rv_recommendationList.setLayoutManager(mLayoutManager);
        rv_recommendationList.setItemAnimator(new DefaultItemAnimator());
        rv_recommendationList.setAdapter(recommendationsAdapter);

        /**
         * scroll listener attached to recycler view for pagination
         */
        rv_recommendationList.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
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
                    getRecommendations(movieResultModel.getId(), pageNumber, true);
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
     * keywords api called
     * @param id
     */
    private void getKeywordsList(String id) {
        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        Call<KeywordsModel> call = apiInterface.callGetKeywordsList(id,
                API_KEY);

        Log.e("apiKey", call.request().url().toString());
        call.enqueue(new Callback<KeywordsModel>() {
            @Override
            public void onResponse(Call<KeywordsModel> call, Response<KeywordsModel> response) {
                KeywordsModel KeywordsModel = response.body();
                if (KeywordsModel != null) {
                    keywordsList = KeywordsModel.getKeywords();

                    if (keywordsList.size() > 0) {
                        rv_keywordsList.setVisibility(View.VISIBLE);
                        tv_keywordsNot.setVisibility(View.GONE);
                        setViewToKeywordsList();
                    }
                    else {
                        rv_keywordsList.setVisibility(View.GONE);
                        tv_keywordsNot.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<KeywordsModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * data set to keywords recycler view
     */
    private void setViewToKeywordsList() {
        keywordsAdapter = new KeywordsAdapter(keywordsList, activity);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 4);
        rv_keywordsList.setLayoutManager(mLayoutManager);
        rv_keywordsList.setItemAnimator(new DefaultItemAnimator());
        rv_keywordsList.setAdapter(keywordsAdapter);
    }
}