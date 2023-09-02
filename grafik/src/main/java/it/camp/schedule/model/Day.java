package it.camp.schedule.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tday")
public class Day {
    @Id
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user1;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user2;
    private boolean holiday;
    private LocalDate date;
}
