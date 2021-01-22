package org.zerock.mvreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mvreview.dto.ReviewDto;
import org.zerock.mvreview.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getList(@PathVariable("mno") Long mno) {
        log.info("--------------- list ---------------");
        log.info("MNO: {}", mno);

        List<ReviewDto> reviewDtoList = reviewService.getListOfMovie(mno);

        return ResponseEntity.ok(reviewDtoList);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto) {
        log.info("--------------- add Movie Review ---------------");
        log.info("reviewDto: {}", reviewDto);

        Long reviewnum = reviewService.register(reviewDto);

        return ResponseEntity.ok(reviewnum);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable("reviewnum") Long reviewnum, @RequestBody ReviewDto reviewDto) {
        log.info("--------------- modify Movie Review ---------------");
        log.info("reviewDto: {}", reviewDto);

        reviewService.modify(reviewDto);

        return ResponseEntity.ok(reviewnum);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable("reviewnum") Long reviewnum) {
        log.info("--------------- delete Movie Review ---------------");
        log.info("reviewnum: {}", reviewnum);

        reviewService.remove(reviewnum);

        return ResponseEntity.ok(reviewnum);
    }
}
