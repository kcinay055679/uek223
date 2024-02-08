package ch.bbt.uek223.ticketshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Date;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date date;

    @Column(nullable = false)
    @Size(max = 255)
    private String description;

    @Column(nullable = false)
    @Size(max = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private Set<Ticket> tickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
