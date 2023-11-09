package fr.cda.scraping.service;

import fr.cda.scraping.model.entities.Annonce;
import fr.cda.scraping.model.repository.AnnonceRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    public List<Annonce> getAnnonceByCity(String city){
        return annonceRepository.findByCity(city);
    }
}
