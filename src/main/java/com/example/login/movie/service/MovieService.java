package com.example.login.movie.service;

import com.example.login.movie.dto.MovieDto;
import com.example.login.movie.repository.MovieRepository;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("movieService")
@Transactional
public class MovieService {

    @Resource(name ="movieRepository")
    private MovieRepository movieRepository;



    public void saveExternalMovies(MovieDto movieDto){

        movieRepository.save(movieDto.toEntity());
    }

}
