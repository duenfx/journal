package com.tutor.journal.repository;

import com.tutor.journal.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}