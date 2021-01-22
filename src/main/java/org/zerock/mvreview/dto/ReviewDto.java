package org.zerock.mvreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long reviewnum;

    private Long mno;   // movie

    private Long mid;   // member
    private String nickname;
    private String email;

    private int grade;
    private String text;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
