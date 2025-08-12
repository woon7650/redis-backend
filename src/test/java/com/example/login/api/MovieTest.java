package com.example.login.api;

import com.example.login.movie.service.MovieService2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieTest {
    @Autowired
    MovieService2 movieService;

    @Test
    void testGetMovie() {
        movieService.saveInternalMovies();
    }
}
