package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Contrat;

public interface IContratService {
    Contrat create(Contrat contrat);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat contrat);
    void deleteById(Long id);
}
