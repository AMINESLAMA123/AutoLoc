package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
