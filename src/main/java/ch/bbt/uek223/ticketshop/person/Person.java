package ch.bbt.uek223.ticketshop.person;

import ch.bbt.uek223.ticketshop.role.Role;
import ch.bbt.uek223.ticketshop.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
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

    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany
    private Set<Role> assignedRoles = new HashSet<>();

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
