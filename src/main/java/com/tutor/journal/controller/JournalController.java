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

    // 1. ВІДДАТИ СПИСОК РЕПЕТИТОРІВ (GET-запит)
    @GetMapping("/tutors")
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    // 2. ВІДДАТИ ВІЛЬНІ ВІКНА (GET-запит)
    @GetMapping("/slots")
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    // 3. ЗБЕРЕГТИ НОВИЙ ЗАПИС УЧНЯ (POST-запит)
    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking);
    }
}