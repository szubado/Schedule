package it.camp.schedule.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    public enum Role {
        ADMIN,
        USER
    }
    @Enumerated(EnumType.STRING)
    private Lab lab;
    public enum Lab {
        HEMATOLOGY,
        ANALYTICS,
        BIOCHEMISTRY,
        IMMUNOCHEMISTRY
    }
}
