package ua.edu.lnu.card.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "decks")
@Data
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Card> cards = new HashSet<>();

    @Column(name = "is_private", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isPrivate = true;



    @Column(name = "rating")
    private Double rating = 0.0;

//    @OneToMany(mappedBy = "deck", orphanRemoval = true)
//    private Set<DeckRating> deckRatings = new LinkedHashSet<>();


//    @OneToMany(mappedBy = "deck")
    @OneToMany(mappedBy = "deck", fetch = FetchType.EAGER)
    private Set<Collaborator> collaborators = new LinkedHashSet<>();


//    @OneToMany(mappedBy = "deck", orphanRemoval = true)
//    private Set<DeckRole> deckRoles = new LinkedHashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}