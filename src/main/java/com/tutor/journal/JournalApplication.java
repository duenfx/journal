package com.tutor.journal;

import com.tutor.journal.entity.Slot;
import com.tutor.journal.entity.Tutor;
import com.tutor.journal.repository.SlotRepository;
import com.tutor.journal.repository.TutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(TutorRepository tutorRepo, SlotRepository slotRepo) {
        return args -> {
            // Створюємо репетитора
            Tutor t1 = new Tutor();
            t1.setName("Олексій Прогер");
            t1.setSubject("Java Backend");
            t1.setPrice(600);
            t1.setDescription("Навчу писати код без багів (майже).");
            tutorRepo.save(t1);

            // Додаємо йому кілька слотів на завтра
            slotRepo.save(new Slot(1L, LocalDate.now().plusDays(1), LocalTime.of(10, 0), false));
            slotRepo.save(new Slot(1L, LocalDate.now().plusDays(1), LocalTime.of(14, 0), false));
            slotRepo.save(new Slot(1L, LocalDate.now().plusDays(1), LocalTime.of(16, 0), true)); // Цей вже зайнятий
        };
    }
}
