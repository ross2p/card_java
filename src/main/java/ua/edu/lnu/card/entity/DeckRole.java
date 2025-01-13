package ua.edu.lnu.card.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "deck_role")
public class DeckRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;


    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deck_id")
    private Deck deck;


    @Column(name = "is_viewed", nullable = false, columnDefinition = "boolean default false")
    private boolean isViewed;

    @Column(name = "is_editable", nullable = false, columnDefinition = "boolean default false")
    private boolean isEditable;

    @Column(name = "is_edit_role_user", nullable = false, columnDefinition = "boolean default false")
    private boolean isEditRoleUser;

}