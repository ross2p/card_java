package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.lnu.card.dtos.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.services.CollaboratorService;

@Controller
@RequestMapping("/collaborator")
@RequiredArgsConstructor
public class CollaboratorController {
    private final CollaboratorService collaboratorService;

    @PostMapping
    public ResponseEntity<Collaborator> createCollaborator(
            @RequestBody CollaboratorCreationUpdateRequest newCollaboratorRequest) {
        System.out.println("CollaboratorController.createCollaborator" + newCollaboratorRequest);
        Collaborator collaborator = collaboratorService.createCollaborator(newCollaboratorRequest);
        return ResponseEntity.ok(collaborator);
    }
}
