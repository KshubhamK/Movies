package com.shubham.movies.networks;

public class Urls {

    public static String BASE_URL = "https://api.themoviedb.org/";
    public static String BASE_THREE = "3/";

    public static String APP_BASE_URL = BASE_URL + BASE_THREE;

    public final static String GET_MOVIES_LIST = "discover/movie";
    public final static String GET_MOVIE_POSTER = "https://image.tmdb.org/t/p/w500/";
    public final static String GET_MOVIE_DETAILS = "movie/{movie_id}";
    public final static String GET_CAST_DETAILS = "movie/{movie_id}/credits";
    public final static String GET_RECOMMENDATIONS = "movie/{movie_id}/recommendations";
    public final static String GET_KEYWORDS = "movie/{movie_id}/keywords";
}
