package fr.cda.scraping.model.repository;

import fr.cda.scraping.model.entities.Annonce;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findById(String title);
    List<Annonce> findByType(String title);
    List<Annonce> findByCity(String city);

}

