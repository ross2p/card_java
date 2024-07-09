package ua.edu.lnu.card.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Column(name = "key", length = 100)
    private String key;

    @Column(name = "value", length = 100)
    private String value;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

}