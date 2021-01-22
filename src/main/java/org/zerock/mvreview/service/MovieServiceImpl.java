package org.zerock.mvreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mvreview.dto.MovieDto;
import org.zerock.mvreview.dto.PageRequestDto;
import org.zerock.mvreview.dto.PageResultDto;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.MovieImage;
import org.zerock.mvreview.repository.MovieImageRepository;
import org.zerock.mvreview.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDto> fn = (arr -> entityToDto(
                (Movie) arr[0],
                (List<MovieImage>) Arrays.asList((MovieImage)arr[1]),
                (Double) arr[2],
                (Long) arr[3]
        ));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public MovieDto getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAllImage(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entityToDto(movie, movieImageList, avg, reviewCnt);
    }
}
