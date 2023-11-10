//package fr.cda.scraping.service;
//
//import fr.cda.scraping.model.entities.Annonce;
//import fr.cda.scraping.model.repository.AnnonceRepository;
//import fr.cda.scraping.utils.SEARCH;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.util.List;
//
//@Service
//public class AnnonceService {
//    @Autowired
//    private AnnonceRepository annonceRepository;
//
//    public Annonce getAnnonceById(long id){
//        return annonceRepository.findById(id);
//    }
//    public Annonce getAnnonceByHref(String href){
//        return annonceRepository.findByHref(href);
//    }
//    public List<Annonce> getAnnonceByCity(String city){
//        return annonceRepository.findByCity(city);
//    }
//    public List<Annonce> getAnnonceByType(SEARCH type){
//        return annonceRepository.findByType(type);
//    }
//    public List<Annonce> getAnnonceByPostcode(String postcode){
//        return annonceRepository.findByPostcode(postcode);
//    }
//    public List<Annonce> getAnnonceByDepartment(String department){
//        return annonceRepository.findByDepartment(department);
//    }
//
//}
