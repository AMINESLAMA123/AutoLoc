package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
