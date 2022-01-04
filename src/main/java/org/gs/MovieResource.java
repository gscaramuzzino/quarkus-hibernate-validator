package org.gs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/movies")
public class MovieResource {

    @Inject
    Validator validator;

    @Inject
    MovieService movieService;

    private List<Movie> movies = new ArrayList<>();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie movie) {

        Set<ConstraintViolation<Movie>> validate = validator.validate(movie);
        if (validate.isEmpty()) {
            movies.add(movie);
            return Response.ok(movies).build();
        } else {
            String violations = validate.stream().map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @POST
    @Path("/valid")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovieWithValid(@Valid @ConvertGroup(to = ValidationMovieGroups.Post.class) Movie movie) {

        movies.add(movie);
        return Response.ok(movies).build();
    }

    @POST
    @Path("/service")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovieWithMovieService(Movie movie) {

        try {
            Movie movieToAdd = movieService.validate(movie);
            movies.add(movieToAdd);
            return Response.ok(movies).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));

            return Response.status(Status.BAD_REQUEST)
                    .entity(violations).build();

        }
    }
}