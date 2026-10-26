package tn.esprit.tpsi.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpsi.domain.Contrat;
import tn.esprit.tpsi.service.IContratService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratControllerTest {

    @Mock
    private IContratService contratService;

    @InjectMocks
    private ContratController contratController;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        contrat = new Contrat();
        contrat.setIdContrat(1L);
        contrat.setDateSignature(LocalDate.of(2026, 11, 2));
        contrat.setMontantTotal(new BigDecimal("480.00"));
        contrat.setValide(true);
    }

    @Test
    void testFindAll() {
        when(contratService.findAll()).thenReturn(List.of(contrat));

        List<Contrat> result = contratController.findAll();

        assertThat(result).hasSize(1);
        verify(contratService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(contratService.findById(1L)).thenReturn(contrat);

        Contrat result = contratController.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getIdContrat()).isEqualTo(1L);
        verify(contratService, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        when(contratService.create(any(Contrat.class))).thenReturn(contrat);

        Contrat result = contratController.create(contrat);

        assertThat(result).isNotNull();
        assertThat(result.getIdContrat()).isEqualTo(1L);
        verify(contratService, times(1)).create(any(Contrat.class));
    }

    @Test
    void testUpdate() {
        when(contratService.update(eq(1L), any(Contrat.class))).thenReturn(contrat);

        Contrat result = contratController.update(1L, contrat);

        assertThat(result).isNotNull();
        verify(contratService, times(1)).update(eq(1L), any(Contrat.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(contratService).deleteById(1L);

        contratController.deleteById(1L);

        verify(contratService, times(1)).deleteById(1L);
    }
}
