package com.bookmyshow.service;

import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie saveMovie(Movie movie) {
        throw new UnsupportedOperationException("Unimplemented method 'saveMovie'");
    }
}
