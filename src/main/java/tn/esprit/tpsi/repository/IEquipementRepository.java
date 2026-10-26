package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
