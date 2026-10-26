package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Paiement;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}
