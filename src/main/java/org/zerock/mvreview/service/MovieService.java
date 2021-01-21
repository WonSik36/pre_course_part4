package org.zerock.mvreview.service;

import org.zerock.mvreview.dto.MovieDto;
import org.zerock.mvreview.dto.MovieImageDto;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDto movieDto);

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
