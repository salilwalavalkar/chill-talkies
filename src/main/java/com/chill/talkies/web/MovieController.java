package com.chill.talkies.web;

import com.chill.talkies.domain.Movie;
import com.chill.talkies.error.MovieNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/movies")
public class MovieController {

    private CrudRepository<Movie, String> repository;

    @Autowired
    public MovieController(CrudRepository<Movie, String> repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get all movies", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Movie> movies() {
        log.info("Getting all movies");
        return repository.findAll();
    }

    @ApiOperation(value = "Add a movie", response = Movie.class)
    @RequestMapping(method = RequestMethod.PUT)
    public Movie add(@RequestBody @Valid Movie movie) {
        log.info("Adding movie: {}", movie);
        return repository.save(movie);
    }

    @ApiOperation(value = "Update a movie", response = Movie.class)
    @RequestMapping(method = RequestMethod.POST)
    public Movie update(@RequestBody @Valid Movie movie) {
        log.info("Updating movie: {}", movie);
        return repository.save(movie);
    }

    @ApiOperation(value = "Get a movie by id", response = Movie.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Movie getById(@PathVariable String id) {
        log.info("Getting movie by id: {}", id);
        return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @ApiOperation(value = "Delete a movie by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable String id) {
        log.info("Deleting movie by id: {}" + id);
        repository.deleteById(id);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(RuntimeException ex) {
        return ex.getMessage();
    }
}
