package org.zerock.mvreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mvreview.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
