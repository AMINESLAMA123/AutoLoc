package tn.esprit.tpsi.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.tpsi.exception.ResourceNotFoundException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Contrat", 9999L);
        ResponseEntity<Map<String, Object>> response = handler.handleResourceNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("statut")).isEqualTo(404);
        assertThat(response.getBody().get("message")).isEqualTo("Contrat introuvable avec l'identifiant 9999");
        assertThat(response.getBody().get("horodatage")).isNotNull();
    }

    @Test
    void testHandleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Le montant total ne peut pas être négatif");
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("statut")).isEqualTo(400);
        assertThat(response.getBody().get("message")).isEqualTo("Le montant total ne peut pas être négatif");
        assertThat(response.getBody().get("horodatage")).isNotNull();
    }
}
