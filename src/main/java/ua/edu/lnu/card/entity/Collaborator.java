package ua.edu.lnu.card.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;


@Data
@Entity
@Table(name = "collaborator")
public class Collaborator {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "deck_role_id", nullable = false)
    private DeckRole deckRole;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}