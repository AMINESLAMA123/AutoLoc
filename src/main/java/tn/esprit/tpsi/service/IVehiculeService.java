package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Vehicule;

public interface IVehiculeService {
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);
}
