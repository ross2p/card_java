package ua.edu.lnu.card.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardResponse;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.mapper.CardMapper;
import ua.edu.lnu.card.repository.CardRepository;
import ua.edu.lnu.card.service.CardService;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    private Card getCardById(Long id){
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card with id " + id + " not found"));
    }

    @Override
    public CardResponse readById(Long cardId) {
        Card card = getCardById(cardId);
        return cardMapper.toDto(card);
    }


    @Override
    public Page<CardResponse> getAllByDeckId(Long deckId, PageRequest pageRequest) {
        return cardRepository.findAllByDeckId(deckId, pageRequest)
                .map(cardMapper::toDto);
    }

    public CardResponse create(Long deckId, CardCreationUpdateRequest cardDto) {
        Card card = cardMapper.toEntity(cardDto, deckId);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }
    public CardResponse update(Long cardId, CardCreationUpdateRequest cardDto) {
        Card cardToUpdate = getCardById(cardId);
        Card updatedCard = cardMapper.partialUpdate(cardDto, cardToUpdate);
        return cardMapper.toDto(updatedCard);
    }
    public void delete(Long cardId) {
        Card card = getCardById(cardId);
        cardRepository.delete(card);
    }


}
