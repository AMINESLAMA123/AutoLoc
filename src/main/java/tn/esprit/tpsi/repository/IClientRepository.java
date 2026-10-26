package tn.esprit.tpsi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpsi.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}
