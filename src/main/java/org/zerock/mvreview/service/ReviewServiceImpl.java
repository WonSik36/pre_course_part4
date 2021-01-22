package org.zerock.mvreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mvreview.dto.ReviewDto;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.Review;
import org.zerock.mvreview.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;


    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDto reviewDto) {
        Review review = dtoToEntity(reviewDto);

        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getReviewnum()).orElseThrow();

        review.changeGrade(reviewDto.getGrade());
        review.changeText(reviewDto.getText());

        reviewRepository.save(review);
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
