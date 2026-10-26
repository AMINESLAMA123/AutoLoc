package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Reservation;

public interface IReservationService {
    Reservation create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    void deleteById(Long id);
}
