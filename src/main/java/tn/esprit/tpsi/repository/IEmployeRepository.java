package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
