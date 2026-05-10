package com.tutor.journal.controller;

import com.tutor.journal.entity.Booking;
import com.tutor.journal.entity.Review;
import com.tutor.journal.entity.Slot;
import com.tutor.journal.entity.Tutor;
import com.tutor.journal.repository.BookingRepository;
import com.tutor.journal.repository.ReviewRepository;
import com.tutor.journal.repository.SlotRepository;
import com.tutor.journal.repository.TutorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JournalController {

    private final TutorRepository tutorRepository;
    private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public JournalController(TutorRepository tutorRepository,
                             SlotRepository slotRepository,
                             BookingRepository bookingRepository,
                             ReviewRepository reviewRepository) {
        this.tutorRepository = tutorRepository;
        this.slotRepository = slotRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
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
        return slotRepository.findById(booking.getSlotId()).map(slot -> {
            if (Boolean.TRUE.equals(slot.getIsBooked())) {
                throw new RuntimeException("Цей слот вже заброньовано!");
            }
            slot.setIsBooked(true);
            slotRepository.save(slot);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Слот не знайдено"));
    }

    @GetMapping("/bookings/user/{userId}")
    public List<java.util.Map<String, Object>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<java.util.Map<String, Object>> result = new java.util.ArrayList<>();

        for (Booking b : bookings) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("bookingId", b.getId());

            slotRepository.findById(b.getSlotId()).ifPresent(slot -> {
                map.put("date", slot.getDate().toString());
                map.put("time", slot.getTime().toString());
                map.put("tutorId", slot.getTutorId());

                tutorRepository.findById(slot.getTutorId()).ifPresent(tutor -> {
                    map.put("tutorName", tutor.getName());
                    map.put("subject", tutor.getSubject());
                });
            });
            result.add(map);
        }
        return result;
    }


    @PostMapping("/reviews")
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @GetMapping("/reviews/tutor/{tutorId}")
    public List<Review> getTutorReviews(@PathVariable Long tutorId) {
        return reviewRepository.findByTutorId(tutorId);
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }

    @DeleteMapping("/bookings/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingRepository.findById(id).ifPresent(booking -> {
            slotRepository.findById(booking.getSlotId()).ifPresent(slot -> {
                slot.setIsBooked(false);
                slotRepository.save(slot);
            });
            bookingRepository.deleteById(id);
        });
    }
}