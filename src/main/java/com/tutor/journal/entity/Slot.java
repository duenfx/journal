package com.tutor.journal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tutorId;
    private LocalDate date;
    private LocalTime time;
    private Boolean isBooked = false; // За замовчуванням слот вільний

    public Slot() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public Boolean getIsBooked() { return isBooked; }
    public void setIsBooked(Boolean booked) { isBooked = booked; }
}
