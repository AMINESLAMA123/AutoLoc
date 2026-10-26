package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Equipement;

public interface IEquipementService {
    Equipement create(Equipement equipement);
    Equipement findById(Long id);
    List<Equipement> findAll();
    Equipement update(Long id, Equipement equipement);
    void deleteById(Long id);
}
