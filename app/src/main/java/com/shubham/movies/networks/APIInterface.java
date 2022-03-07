package com.shubham.movies.networks;

import static com.shubham.movies.networks.Urls.GET_CAST_DETAILS;
import static com.shubham.movies.networks.Urls.GET_KEYWORDS;
import static com.shubham.movies.networks.Urls.GET_MOVIES_LIST;
import static com.shubham.movies.networks.Urls.GET_MOVIE_DETAILS;
import static com.shubham.movies.networks.Urls.GET_RECOMMENDATIONS;

import com.google.gson.JsonObject;
import com.shubham.movies.models.apiResponse.ResponseModel;
import com.shubham.movies.models.castAndCrew.CastAndCrewModel;
import com.shubham.movies.models.keywords.KeywordsModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET(GET_MOVIES_LIST)
    Call<ResponseModel> callGetMoviesList(@Query("api_key") String api_key,
                                          @Query("language") String language,
                                          @Query("sort_by") String sort_by,
                                          @Query("include_adult") boolean include_adult,
                                          @Query("include_video") boolean include_video,
                                          @Query("page") int page,
                                          @Query("with_watch_monetization_types") String with_watch_monetization_types);

    @GET(GET_MOVIE_DETAILS)
    Call<JsonObject> callGetMovieDetails(@Path("movie_id") String movieId,
                                         @Query("api_key") String api_key);

    @GET(GET_CAST_DETAILS)
    Call<CastAndCrewModel> callGetCastAndCrewDetails(@Path("movie_id") String movieId,
                                                     @Query("api_key") String api_key);

    @GET(GET_RECOMMENDATIONS)
    Call<ResponseModel> callGetRecommendations(@Path("movie_id") String movieId,
                                               @Query("api_key") String api_key,
                                               @Query("page") int page);

    @GET(GET_KEYWORDS)
    Call<KeywordsModel> callGetKeywordsList(@Path("movie_id") String movieId,
                                            @Query("api_key") String api_key);
}
