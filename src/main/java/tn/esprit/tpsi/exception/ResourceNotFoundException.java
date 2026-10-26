package tn.esprit.tpsi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " introuvable avec l'identifiant " + id);
    }
}
