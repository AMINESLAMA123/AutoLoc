package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
