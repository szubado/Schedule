package it.camp.schedule.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tday")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int month;
    private int dayOfMonth;
    private int year;

    @ManyToOne(fetch = FetchType.EAGER)
    private User dutyUser1;
    @ManyToOne(fetch = FetchType.EAGER)
    private User dutyUser2;
    @Enumerated(EnumType.STRING)
    private TypeOfDay typeOfDay;
    public enum TypeOfDay {
        WEEKDAY,
        SATURDAY,
        HOLIDAY
    }
}
