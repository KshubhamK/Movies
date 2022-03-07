package com.shubham.movies.models.castAndCrew;

public class Crew
{
    private String gender;

    private String credit_id;

    private String known_for_department;

    private String original_name;

    private String popularity;

    private String name;

    private String profile_path;

    private String id;

    private String adult;

    private String department;

    private String job;

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

    public String getDepartment ()
    {
        return department;
    }

    public void setDepartment (String department)
    {
        this.department = department;
    }

    public String getJob ()
    {
        return job;
    }

    public void setJob (String job)
    {
        this.job = job;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gender = "+gender+", credit_id = "+credit_id+", known_for_department = "+known_for_department+", original_name = "+original_name+", popularity = "+popularity+", name = "+name+", profile_path = "+profile_path+", id = "+id+", adult = "+adult+", department = "+department+", job = "+job+"]";
    }
}
			
			