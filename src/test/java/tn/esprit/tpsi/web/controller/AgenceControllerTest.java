package tn.esprit.tpsi.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpsi.domain.Agence;
import tn.esprit.tpsi.service.IAgenceService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgenceControllerTest {

    @Mock
    private IAgenceService agenceService;

    @InjectMocks
    private AgenceController agenceController;

    private Agence agence;

    @BeforeEach
    void setUp() {
        agence = new Agence();
        agence.setIdAgence(1L);
        agence.setNom("Agence Centrale");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("+21671000000");
    }

    @Test
    void testCreate() {
        when(agenceService.create(any(Agence.class))).thenReturn(agence);

        Agence response = agenceController.create(agence);

        assertThat(response).isNotNull();
        assertThat(response.getIdAgence()).isEqualTo(1L);
        assertThat(response.getNom()).isEqualTo("Agence Centrale");
        verify(agenceService, times(1)).create(any(Agence.class));
    }

    @Test
    void testFindById() {
        when(agenceService.findById(1L)).thenReturn(agence);

        Agence response = agenceController.findById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getIdAgence()).isEqualTo(1L);
        verify(agenceService, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        when(agenceService.findAll()).thenReturn(List.of(agence));

        List<Agence> response = agenceController.findAll();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        verify(agenceService, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(agenceService.update(eq(1L), any(Agence.class))).thenReturn(agence);

        Agence response = agenceController.update(1L, agence);

        assertThat(response).isNotNull();
        assertThat(response.getNom()).isEqualTo("Agence Centrale");
        verify(agenceService, times(1)).update(eq(1L), any(Agence.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(agenceService).deleteById(1L);

        agenceController.deleteById(1L);

        verify(agenceService, times(1)).deleteById(1L);
    }
}
