package org.zerock.mvreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mvreview.dto.MovieDto;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.MovieImage;
import org.zerock.mvreview.repository.MovieImageRepository;
import org.zerock.mvreview.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    private final MovieImageRepository movieImageRepository;

    @Transactional
    @Override
    public Long register(MovieDto movieDto) {
        Map<String, Object> entityMap = dtoToEntity(movieDto);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>)entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(img -> {
            movieImageRepository.save(img);
        });

        return movie.getMno();
    }
}
