package ua.edu.lnu.card.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "deck_role")
public class DeckRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Column(name = "is_viewed", nullable = false, columnDefinition = "boolean default false")
    private boolean isViewed;

    @Column(name = "is_editable", nullable = false, columnDefinition = "boolean default false")
    private boolean isEditable;

    @Column(name = "is_edit_role_user", nullable = false, columnDefinition = "boolean default false")
    private boolean isEditRoleUser;

}