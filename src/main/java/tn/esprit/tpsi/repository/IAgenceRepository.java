package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Agence;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {
}
