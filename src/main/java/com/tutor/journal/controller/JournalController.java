package com.tutor.journal.controller;

import com.tutor.journal.entity.Booking;
import com.tutor.journal.entity.Slot;
import com.tutor.journal.entity.Tutor;
import com.tutor.journal.repository.BookingRepository;
import com.tutor.journal.repository.SlotRepository;
import com.tutor.journal.repository.TutorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JournalController {

    private final TutorRepository tutorRepository;
    private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;

    public JournalController(TutorRepository tutorRepository, SlotRepository slotRepository, BookingRepository bookingRepository) {
        this.tutorRepository = tutorRepository;
        this.slotRepository = slotRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/tutors")
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    @GetMapping("/slots/{tutorId}")
    public List<Slot> getSlotsByTutor(@PathVariable Long tutorId) {
        return slotRepository.findByTutorId(tutorId);
    }

    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody Booking booking) {
        slotRepository.findById(booking.getSlotId()).ifPresent(slot -> {
            slot.setIsBooked(true);
            slotRepository.save(slot);
        });
        return bookingRepository.save(booking);
    }
}