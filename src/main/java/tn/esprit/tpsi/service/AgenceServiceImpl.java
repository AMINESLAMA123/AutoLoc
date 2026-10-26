package tn.esprit.tpsi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpsi.domain.Agence;
import tn.esprit.tpsi.exception.ResourceNotFoundException;
import tn.esprit.tpsi.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        if (agence.getNom() == null || agence.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'agence ne peut pas être vide");
        }
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existant = findById(id);
        if (agence.getNom() == null || agence.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'agence ne peut pas être vide");
        }
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }
}
