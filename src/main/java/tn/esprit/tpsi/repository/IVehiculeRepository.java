package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}
