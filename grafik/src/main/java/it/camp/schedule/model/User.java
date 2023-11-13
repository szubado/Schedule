package it.camp.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public User(int id) {
        this.id = id;
    }

    public User(int id, Lab lab) {
        this.id = id;
        this.lab = lab;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
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
        HEMATOLOGIA,
        ANALITYKA,
        BIOCHEMIA,
        IMMUNOCHEMIA
    }
}
