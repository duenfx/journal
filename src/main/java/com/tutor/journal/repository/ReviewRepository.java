package com.tutor.journal.repository;

import com.tutor.journal.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTutorId(Long tutorId);
}
