package com.tutor.journal.repository;

import com.tutor.journal.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByTutorId(Long tutorId);
}