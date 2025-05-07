package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.entities.Card;
import ua.edu.lnu.card.exceptions.exception.client.NotFound;
import ua.edu.lnu.card.mappers.CardMapper;
import ua.edu.lnu.card.repositories.CardRepository;
import ua.edu.lnu.card.services.CardService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Override
    public CardData getCardDataById(UUID cardId) {
        return cardMapper.toDto(getCardById(cardId));
    }

    private Card getCardById(UUID cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new NotFound("Card with id %s not found".formatted(cardId)));
    }

    @Override
    public List<CardData> getAllByDeckId(UUID deckId) {
        return cardRepository.findByDeck_IdOrderByCreatedAtAsc(deckId).stream().map(cardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Card create(CardCreationUpdateRequest cardDto) {
        Card cardToUpdate = cardMapper.toEntity(cardDto);

        return cardRepository.save(cardToUpdate);
    }

    @Override
    public CardData update(UUID cardId, CardCreationUpdateRequest cardDto) {
        Card existingCard = getCardById(cardId);
        Card updatedCard = cardMapper.partialUpdate(cardDto, existingCard);
        Card savedCard = cardRepository.save(updatedCard);
        return cardMapper.toDto(savedCard);
    }

    @Override
    public void delete(UUID cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new NotFound("Card with id %s not found".formatted(cardId));
        }
        cardRepository.deleteById(cardId);
    }
}
