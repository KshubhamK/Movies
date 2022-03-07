package com.shubham.movies.models.castAndCrew;

public class Cast
{
    private String cast_id;

    private String character;

    private String gender;

    private String credit_id;

    private String known_for_department;

    private String original_name;

    private String popularity;

    private String name;

    private String profile_path;

    private String id;

    private String adult;

    private String order;

    public String getCast_id ()
    {
        return cast_id;
    }

    public void setCast_id (String cast_id)
    {
        this.cast_id = cast_id;
    }

    public String getCharacter ()
    {
        return character;
    }

    public void setCharacter (String character)
    {
        this.character = character;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getCredit_id ()
    {
        return credit_id;
    }

    public void setCredit_id (String credit_id)
    {
        this.credit_id = credit_id;
    }

    public String getKnown_for_department ()
    {
        return known_for_department;
    }

    public void setKnown_for_department (String known_for_department)
    {
        this.known_for_department = known_for_department;
    }

    public String getOriginal_name ()
    {
        return original_name;
    }

    public void setOriginal_name (String original_name)
    {
        this.original_name = original_name;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getProfile_path ()
    {
        return profile_path;
    }

    public void setProfile_path (String profile_path)
    {
        this.profile_path = profile_path;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getOrder ()
    {
        return order;
    }

    public void setOrder (String order)
    {
        this.order = order;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cast_id = "+cast_id+", character = "+character+", gender = "+gender+", credit_id = "+credit_id+", known_for_department = "+known_for_department+", original_name = "+original_name+", popularity = "+popularity+", name = "+name+", profile_path = "+profile_path+", id = "+id+", adult = "+adult+", order = "+order+"]";
    }
}
			
			