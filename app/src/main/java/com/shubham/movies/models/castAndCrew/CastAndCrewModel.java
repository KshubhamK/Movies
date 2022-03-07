package com.shubham.movies.models.castAndCrew;

import java.util.ArrayList;

public class CastAndCrewModel {

    private ArrayList<Cast> cast;

    private String id;

    private ArrayList<Crew> crew;

    public ArrayList<Cast> getCast ()
    {
        return cast;
    }

    public void setCast (ArrayList<Cast> cast)
    {
        this.cast = cast;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public ArrayList<Crew> getCrew ()
    {
        return crew;
    }

    public void setCrew (ArrayList<Crew> crew)
    {
        this.crew = crew;
    }

}
