package com.chill.talkies.web;

import com.chill.talkies.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private CrudRepository<Movie, String> repository;

    @Autowired
    public MovieController(CrudRepository<Movie, String> repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Movie> movies() {
        log.info("Getting all movies");
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Movie add(@RequestBody @Valid Movie movie) {
        log.info("Adding movie: {}", movie);
        return repository.save(movie);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Movie update(@RequestBody @Valid Movie movie) {
        log.info("Updating movie: {}", movie);
        return repository.save(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Movie> getById(@PathVariable String id) {
        log.info("Getting movie by id: {}", id);
        return repository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable String id) {
        log.info("Deleting movie by id: {}" + id);
        repository.deleteById(id);
    }
}
