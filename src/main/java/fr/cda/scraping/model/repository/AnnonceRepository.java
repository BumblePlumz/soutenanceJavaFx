//package fr.cda.scraping.model.repository;
//
//import fr.cda.scraping.model.entities.Annonce;
//import fr.cda.scraping.utils.SEARCH;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//@Repository
//public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
//    Annonce findById(long id);
//    Annonce findByHref(String href);
//    List<Annonce> findByType(SEARCH type);
//    List<Annonce> findByCity(String city);
//    List<Annonce> findByPostcode(String postcode);
//    List<Annonce> findByDepartment(String department);
//}
//
