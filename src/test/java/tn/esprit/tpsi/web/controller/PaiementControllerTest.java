package tn.esprit.tpsi.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpsi.domain.Paiement;
import tn.esprit.tpsi.service.IPaiementService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaiementControllerTest {

    @Mock
    private IPaiementService paiementService;

    @InjectMocks
    private PaiementController paiementController;

    @Test
    void testFindAll() {
        Paiement p = new Paiement();
        p.setIdPaiement(1L);
        when(paiementService.findAll()).thenReturn(List.of(p));

        List<Paiement> result = paiementController.findAll();

        assertThat(result).hasSize(1);
        verify(paiementService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Paiement p = new Paiement();
        p.setIdPaiement(1L);
        when(paiementService.findById(1L)).thenReturn(p);

        Paiement result = paiementController.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getIdPaiement()).isEqualTo(1L);
        verify(paiementService, times(1)).findById(1L);
    }
}
