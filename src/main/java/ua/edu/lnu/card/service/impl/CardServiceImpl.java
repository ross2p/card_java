package ua.edu.lnu.card.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.repository.CardRepository;
import ua.edu.lnu.card.service.CardService;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

}
