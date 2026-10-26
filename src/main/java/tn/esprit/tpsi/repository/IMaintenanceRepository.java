package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
