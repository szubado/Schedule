package it.camp.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tday_off")
public class DayOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private int dayOfYear;
/*    @ManyToOne(fetch = FetchType.EAGER)
    private Day day;*/
/*
    @DateTimeFormat(pattern = "yyyy-MM-dd")
*/
    private LocalDate date;
}
