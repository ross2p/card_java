package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.edu.lnu.card.dto.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entity.DeckRole;
import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.mapper.DeckRoleMapper;
import ua.edu.lnu.card.repository.DeckRoleRepository;
import ua.edu.lnu.card.service.impl.DeckRoleServiceImpl;
import ua.edu.lnu.card.utils.DefaultDeckRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeckRoleServiceImplTest {

    @Mock
    private DeckRoleRepository deckRoleRepository;

    @Mock
    private DeckRoleMapper deckRoleMapper;

    @InjectMocks
    private DeckRoleServiceImpl deckRoleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDefaultDeckRole_ShouldCreateRoles() {
        UUID deckId = UUID.randomUUID();
        List<DeckRoleCreationUpdateRequest> defaultRoles = DefaultDeckRoles.getDefaultDeckRoleList(deckId);

        DeckRole deckRole = new DeckRole();
        when(deckRoleMapper.toEntity(any())).thenReturn(deckRole);
        when(deckRoleRepository.saveAndFlush(any())).thenReturn(deckRole);

        List<DeckRole> result = deckRoleService.createDefaultDeckRole(deckId);

        assertThat(result).hasSize(defaultRoles.size());
        verify(deckRoleRepository, times(defaultRoles.size())).saveAndFlush(any());
    }

    @Test
    void createDeckRole_ShouldSaveAndReturnRole() {
        DeckRoleCreationUpdateRequest request = new DeckRoleCreationUpdateRequest();
        DeckRole deckRole = new DeckRole();

        when(deckRoleMapper.toEntity(request)).thenReturn(deckRole);
        when(deckRoleRepository.saveAndFlush(deckRole)).thenReturn(deckRole);

        DeckRole result = deckRoleService.createDeckRole(request);

        assertThat(result).isEqualTo(deckRole);
        verify(deckRoleRepository).saveAndFlush(deckRole);
    }

    @Test
    void getDeckRolesByDeckId_ShouldReturnListOfDeckRoles() {
        UUID deckId = UUID.randomUUID();
        List<DeckRole> expectedRoles = List.of(new DeckRole(), new DeckRole());

        when(deckRoleRepository.findAllByDeckId(deckId)).thenReturn(expectedRoles);

        List<DeckRole> result = deckRoleService.getDeckRolesByDeckId(deckId);

        assertThat(result).isEqualTo(expectedRoles);
        verify(deckRoleRepository).findAllByDeckId(deckId);
    }

    @Test
    void updateDeckRole_ShouldUpdateDeckRole() {
        UUID deckRoleId = UUID.randomUUID();
        DeckRoleCreationUpdateRequest request = new DeckRoleCreationUpdateRequest();
        DeckRole existingDeckRole = new DeckRole();
        DeckRole updatedDeckRole = new DeckRole();

        when(deckRoleRepository.findById(deckRoleId)).thenReturn(Optional.of(existingDeckRole));
        when(deckRoleMapper.partialUpdate(request, existingDeckRole)).thenReturn(updatedDeckRole);

        DeckRole result = deckRoleService.updateDeckRole(deckRoleId, request);

        assertThat(result).isEqualTo(updatedDeckRole);
        verify(deckRoleMapper).partialUpdate(request, existingDeckRole);
    }

    @Test
    void deleteDeckRole_ShouldThrowInternalServerError() {
        UUID deckRoleId = UUID.randomUUID();

        assertThatThrownBy(() -> deckRoleService.deleteDeckRole(deckRoleId))
                .isInstanceOf(InternalServerError.class)
                .hasMessageContaining("Not implemented yet");
    }

    @Test
    void getDeckRoleById_ShouldReturnDeckRole_WhenFound() {
        UUID deckRoleId = UUID.randomUUID();
        DeckRole deckRole = new DeckRole();

        when(deckRoleRepository.findById(deckRoleId)).thenReturn(Optional.of(deckRole));

        DeckRole result = deckRoleService.getDeckRoleById(deckRoleId);

        assertThat(result).isEqualTo(deckRole);
        verify(deckRoleRepository).findById(deckRoleId);
    }

    @Test
    void getDeckRoleById_ShouldThrowRuntimeException_WhenNotFound() {
        UUID deckRoleId = UUID.randomUUID();

        when(deckRoleRepository.findById(deckRoleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deckRoleService.getDeckRoleById(deckRoleId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(deckRoleId.toString());
    }

    @Test
    void getDeckRoleByDeckIdAndName_ShouldReturnDeckRole_WhenFound() {
        UUID deckId = UUID.randomUUID();
        String name = "TestRole";
        DeckRole deckRole = new DeckRole();

        when(deckRoleRepository.findByDeckIdAndName(deckId, name)).thenReturn(Optional.of(deckRole));

        DeckRole result = deckRoleService.getDeckRoleByDeckIdAndName(deckId, name);

        assertThat(result).isEqualTo(deckRole);
        verify(deckRoleRepository).findByDeckIdAndName(deckId, name);
    }

    @Test
    void getDeckRoleByDeckIdAndName_ShouldThrowRuntimeException_WhenNotFound() {
        UUID deckId = UUID.randomUUID();
        String name = "TestRole";

        when(deckRoleRepository.findByDeckIdAndName(deckId, name)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deckRoleService.getDeckRoleByDeckIdAndName(deckId, name))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(deckId.toString())
                .hasMessageContaining(name);
    }
}