package ua.edu.lnu.card.utils;

import ua.edu.lnu.card.dto.deckRole.DeckRoleCreationUpdateRequest;

import java.util.List;
import java.util.UUID;

public class DefaultDeckRoles {
    public static final DeckRoleCreationUpdateRequest OWNER = createDefaultDeckRole("OWNER", true, true, true);
    public static final DeckRoleCreationUpdateRequest VIEWER = createDefaultDeckRole("VIEWER", true, false, false);
    public static final DeckRoleCreationUpdateRequest EDITOR = createDefaultDeckRole("EDITOR", true, true, false) ;
    public static final DeckRoleCreationUpdateRequest ADMIN = createDefaultDeckRole("ADMIN", true, true, true);

    public static List<DeckRoleCreationUpdateRequest> getDefaultDeckRoleList(UUID deckId) {
        return List.of(OWNER, VIEWER, EDITOR, ADMIN);

    }

    private static DeckRoleCreationUpdateRequest createDefaultDeckRole(String name, boolean isViewed, boolean isEditable, boolean isEditRoleUser){
        if (isEditable) {
            isViewed = true;
        }


        DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest = new DeckRoleCreationUpdateRequest();
        deckRoleCreationUpdateRequest.setName(name);
        deckRoleCreationUpdateRequest.setIsViewed(isViewed);
        deckRoleCreationUpdateRequest.setIsEditable(isEditable);
        deckRoleCreationUpdateRequest.setIsEditRoleUser(isEditRoleUser);
        return deckRoleCreationUpdateRequest;
    }
}
