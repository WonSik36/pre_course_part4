package org.zerock.mvreview.service;

import org.zerock.mvreview.dto.MovieDto;
import org.zerock.mvreview.dto.MovieImageDto;
import org.zerock.mvreview.dto.PageRequestDto;
import org.zerock.mvreview.dto.PageResultDto;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDto movieDto);

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto);

    default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDto> movieImageDtoList = movieImages.stream()
                .map(movieImage -> {
                    return MovieImageDto.builder()
                            .imgName(movieImage.getImgName())
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        movieDto.setImageDtoList(movieImageDtoList);
        movieDto.setAvg(avg);
        movieDto.setReviewCnt(reviewCnt.intValue());

        return movieDto;
    }

    default Map<String, Object> dtoToEntity(MovieDto movieDto) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
            .mno(movieDto.getMno())
            .title(movieDto.getTitle())
            .build();

        entityMap.put("movie", movie);

        List<MovieImageDto> imageDtoList = movieDto.getImageDtoList();

        if(imageDtoList != null && imageDtoList.size() > 0) {
            List<MovieImage> movieImageList = imageDtoList.stream()
                    .map(dto -> {
                       MovieImage movieImage = MovieImage.builder()
                               .path(dto.getPath())
                               .imgName(dto.getImgName())
                               .uuid(dto.getUuid())
                               .movie(movie)
                               .build();

                       return movieImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }
}
