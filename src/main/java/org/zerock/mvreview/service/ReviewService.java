package org.zerock.mvreview.service;

import org.zerock.mvreview.dto.ReviewDto;
import org.zerock.mvreview.entity.Member;
import org.zerock.mvreview.entity.Movie;
import org.zerock.mvreview.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getListOfMovie(Long mno);

    Long register(ReviewDto reviewDto);

    void modify(ReviewDto reviewDto);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDto reviewDto) {
        Review review = Review.builder()
                .reviewnum(reviewDto.getReviewnum())
                .movie(Movie.builder().mno(reviewDto.getMno()).build())
                .member(Member.builder().mid(reviewDto.getMid()).build())
                .grade(reviewDto.getGrade())
                .text(reviewDto.getText())
                .build();

        return review;
    }

    default ReviewDto entityToDto(Review review) {
        ReviewDto dto = ReviewDto.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .nickname(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return dto;
    }
}
