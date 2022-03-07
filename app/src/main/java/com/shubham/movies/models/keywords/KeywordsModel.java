package com.shubham.movies.models.keywords;

import java.util.List;

public class KeywordsModel {
    private List<Keywords> keywords;

    private String id;

    public List<Keywords> getKeywords ()
    {
        return keywords;
    }

    public void setKeywords (List<Keywords> keywords)
    {
        this.keywords = keywords;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [keywords = "+keywords+", id = "+id+"]";
    }
}
