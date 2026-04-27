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
import java.util.List;

@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(TutorRepository tutorRepo, SlotRepository slotRepo) {
        return args -> {
            // Очищаємо стару базу, щоб при кожному перезапуску сервера дані були актуальними і без дублікатів
            slotRepo.deleteAll();
            tutorRepo.deleteAll();

            // Масив даних: Предмет, Ім'я, Ціна, Опис
            String[][] tutorsData = {
                    {"Англійська мова", "Марія English", "450", "Підготовка до НМТ та IELTS."},
                    {"Англійська мова", "John Smith", "600", "Native speaker. Improve your speaking skills!"},
                    {"Англійська мова", "Анна Граммар", "400", "Пояснюю часи так, що ви їх полюбите."},

                    {"Математика", "Олександр Мат", "500", "Підготовка до ЗНО/НМТ на 190+."},
                    {"Математика", "Тетяна Логіка", "450", "Алгебра та геометрія для школярів."},
                    {"Математика", "Ігор Аналіз", "550", "Вища математика для студентів."},

                    {"Українська мова", "Наталія Солов'їна", "400", "Готуємось до творчого завдання разом."},
                    {"Українська мова", "Оксана Правопис", "350", "Українська — це модно та просто."},
                    {"Українська мова", "Іван Слівце", "380", "Підготовка до НМТ без нудних правил."},

                    {"Фізика", "Віктор Тесла", "550", "Електрика, механіка, кванти — все зрозуміло."},
                    {"Фізика", "Сергій Квант", "500", "Фізика для тих, хто хоче розуміти світ."},
                    {"Фізика", "Марина Оптика", "480", "Лабораторні та задачі будь-якої складності."},

                    {"Біологія", "Олена Біо", "420", "Ботаніка, зоологія та генетика."},
                    {"Біологія", "Артем ДНК", "460", "Підготовка до вступу в мед. університети."},
                    {"Біологія", "Ірина Клітина", "400", "Цікава біологія для допитливих."},

                    {"Хімія", "Дмитро Менделєєв", "480", "Органічна та неорганічна хімія."},
                    {"Хімія", "Людмила Колба", "450", "Зрозумілі реакції та формули."},
                    {"Хімія", "Артем Валентність", "430", "Хімія з нуля для школярів."}
            };

            for (String[] row : tutorsData) {
                Tutor t = new Tutor();
                t.setSubject(row[0]);
                t.setName(row[1]);
                t.setPrice(Integer.parseInt(row[2]));
                t.setDescription(row[3]);
                tutorRepo.save(t);

                // Додаємо по 2 вільних слоти на завтра
                slotRepo.save(new Slot(t.getId(), LocalDate.now().plusDays(1), LocalTime.of(12, 0), false));
                slotRepo.save(new Slot(t.getId(), LocalDate.now().plusDays(1), LocalTime.of(16, 30), false));
                // Один зайнятий слот на післязавтра
                slotRepo.save(new Slot(t.getId(), LocalDate.now().plusDays(2), LocalTime.of(10, 0), true));
            }

            System.out.println("Базу успішно оновлено: 18 репетиторів додано! 🚀");
        };
    }
}
