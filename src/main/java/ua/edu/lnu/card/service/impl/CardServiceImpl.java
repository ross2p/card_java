package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.exception.exception.client.NotFound;
import ua.edu.lnu.card.mapper.CardMapper;
import ua.edu.lnu.card.repository.CardRepository;
import ua.edu.lnu.card.service.CardService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Override
    public CardData getCardById(UUID cardId) {
        return cardRepository.findById(cardId).map(cardMapper::toDto).orElseThrow(
                () -> new NotFound("Card with id %s not found".formatted(cardId))
        );
    }

    @Override
    public List<CardData> getAllByDeckId(UUID deckId) {
        return cardRepository.findByDeck_IdOrderByCreatedAtAsc(deckId).stream().map(cardMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Card create( CardCreationUpdateRequest cardDto) {
        Card cardToUpdate = cardMapper.toEntity(cardDto);

        return cardRepository.save(cardToUpdate);
    }

    @Override
    public CardData update(UUID cardId, CardCreationUpdateRequest cardDto) {
        return null;
    }

    @Override
    public void delete(UUID cardId) {

    }
}
