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
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user1;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user2;
    private boolean holiday;
}
