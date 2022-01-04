package org.gs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class Movie {

    @NotBlank(message = "The title must not be blank or empty")
    @Length(min = 10, groups = ValidationMovieGroups.Post.class)
    @Length(min = 20, groups = ValidationMovieGroups.PostVithService.class)
    private String title;

    @NotBlank(message = "The director must not be blank or empty")
    private String director;

    @Min(message = "The movie must be at least 60 minutes", value = 60)
    private int minutes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    
    
}
