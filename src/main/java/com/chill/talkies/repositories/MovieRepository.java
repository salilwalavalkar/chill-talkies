package com.chill.talkies.repositories;

import com.chill.talkies.domain.Movie;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"in-memory", "postgres"})
public interface MovieRepository extends JpaRepository<Movie, String> {
}
