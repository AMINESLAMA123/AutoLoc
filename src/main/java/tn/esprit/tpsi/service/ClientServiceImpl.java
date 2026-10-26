package tn.esprit.tpsi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpsi.domain.Client;
import tn.esprit.tpsi.exception.ResourceNotFoundException;
import tn.esprit.tpsi.repository.IClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        if (client.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        }
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du client ne peut pas être vide");
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'e-mail du client ne peut pas être vide");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {
        Client existant = findById(id);
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du client ne peut pas être vide");
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'e-mail du client ne peut pas être vide");
        }
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());
        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }
}
