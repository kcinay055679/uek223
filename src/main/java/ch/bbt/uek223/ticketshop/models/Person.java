package ch.bbt.uek223.ticketshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @Size(max = 255)
    private String email;

    @Column
    @Size(max = 255)
    private String password;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private Set<Event> events;

    @Enumerated(EnumType.ORDINAL)
    private Role assignedRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
