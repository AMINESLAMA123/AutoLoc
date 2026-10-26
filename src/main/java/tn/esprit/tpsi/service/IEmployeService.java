package tn.esprit.tpsi.service;

import java.util.List;
import tn.esprit.tpsi.domain.Employe;

public interface IEmployeService {
    Employe create(Employe employe);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe employe);
    void deleteById(Long id);
}
