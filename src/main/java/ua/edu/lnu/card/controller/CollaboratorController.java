package ua.edu.lnu.card.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.service.CollaboratorService;

@Controller
@RequestMapping("/collaborator")
@RequiredArgsConstructor
public class CollaboratorController {
    private final CollaboratorService collaboratorService;

}
