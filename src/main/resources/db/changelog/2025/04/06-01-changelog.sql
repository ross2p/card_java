-- liquibase formatted sql

-- changeset rostyslavpasternak:1743960998355-1
CREATE TABLE card
(
    id          UUID NOT NULL,
    deck_id     UUID,
    key         VARCHAR(100),
    value       VARCHAR(100),
    description TEXT,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_card PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-2
CREATE TABLE collaborator
(
    id           UUID NOT NULL,
    deck_id      UUID NOT NULL,
    user_id      UUID NOT NULL,
    deck_role_id UUID NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_collaborator PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-3
CREATE TABLE deck_rating
(
    id         UUID NOT NULL,
    deck_id    UUID NOT NULL,
    user_id    UUID NOT NULL,
    rating     DOUBLE PRECISION,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_deck_rating PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-4
CREATE TABLE deck_role
(
    id                UUID                  NOT NULL,
    name              VARCHAR(50)           NOT NULL,
    deck_id           UUID,
    is_viewed         BOOLEAN DEFAULT FALSE NOT NULL,
    is_editable       BOOLEAN DEFAULT FALSE NOT NULL,
    is_edit_role_user BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT pk_deck_role PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-5
CREATE TABLE decks
(
    id          UUID                 NOT NULL,
    name        VARCHAR(100)         NOT NULL,
    description TEXT,
    owner_id    UUID,
    is_private  BOOLEAN DEFAULT TRUE NOT NULL,
    rating      DOUBLE PRECISION,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_decks PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-6
CREATE TABLE roles
(
    id          UUID         NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-7
CREATE TABLE users
(
    id         UUID         NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    birthdate  date,
    role_id    UUID         NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset rostyslavpasternak:1743960998355-8
ALTER TABLE deck_rating
    ADD CONSTRAINT uc_46548c032111be4c6260816ed UNIQUE (deck_id, user_id);

-- changeset rostyslavpasternak:1743960998355-9
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset rostyslavpasternak:1743960998355-10
ALTER TABLE card
    ADD CONSTRAINT FK_CARD_ON_DECK FOREIGN KEY (deck_id) REFERENCES decks (id);

-- changeset rostyslavpasternak:1743960998355-11
ALTER TABLE collaborator
    ADD CONSTRAINT FK_COLLABORATOR_ON_DECK FOREIGN KEY (deck_id) REFERENCES decks (id);

-- changeset rostyslavpasternak:1743960998355-12
ALTER TABLE collaborator
    ADD CONSTRAINT FK_COLLABORATOR_ON_DECK_ROLE FOREIGN KEY (deck_role_id) REFERENCES deck_role (id);

-- changeset rostyslavpasternak:1743960998355-13
ALTER TABLE collaborator
    ADD CONSTRAINT FK_COLLABORATOR_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset rostyslavpasternak:1743960998355-14
ALTER TABLE decks
    ADD CONSTRAINT FK_DECKS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

-- changeset rostyslavpasternak:1743960998355-15
ALTER TABLE deck_rating
    ADD CONSTRAINT FK_DECK_RATING_ON_DECK FOREIGN KEY (deck_id) REFERENCES decks (id);

-- changeset rostyslavpasternak:1743960998355-16
ALTER TABLE deck_rating
    ADD CONSTRAINT FK_DECK_RATING_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset rostyslavpasternak:1743960998355-17
ALTER TABLE deck_role
    ADD CONSTRAINT FK_DECK_ROLE_ON_DECK FOREIGN KEY (deck_id) REFERENCES decks (id);

-- changeset rostyslavpasternak:1743960998355-18
ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

