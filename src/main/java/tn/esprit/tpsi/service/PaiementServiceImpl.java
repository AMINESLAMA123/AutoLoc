package tn.esprit.tpsi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpsi.domain.Paiement;
import tn.esprit.tpsi.exception.ResourceNotFoundException;
import tn.esprit.tpsi.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}
