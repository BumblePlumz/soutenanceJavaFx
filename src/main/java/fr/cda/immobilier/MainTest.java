package fr.cda.immobilier;

import fr.cda.immobilier.controller.DatabaseFormController;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.model.entity.Type;
import fr.cda.immobilier.model.repository.AddressRepository;
import fr.cda.immobilier.model.repository.AnnonceRepository;
import fr.cda.immobilier.model.repository.TypeRepository;
import fr.cda.immobilier.service.OuestFrance;
import fr.cda.immobilier.service.SeLoger;
import fr.cda.immobilier.utils.Config;
import fr.cda.immobilier.utils.tools.FilesTools;
import fr.cda.immobilier.utils.tools.JPATools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MainTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        AnnonceRepository ar = new AnnonceRepository();
        AddressRepository adr = new AddressRepository();

//        Annonce annonce = ar.findById((long)1);
//        System.out.println(annonce);

//        List<Annonce> l = ar.findAll();
//        for (Annonce a : l) {
//            System.out.println(a);
//        }

//        FilesTools.saveTxtFile(l);

//        JPATools jpa = new JPATools();
//        jpa.start();

//      TEST scrapingSeLoger
        SeLoger s = new SeLoger();

        Type type = new TypeRepository().findById((long)1);

        List<Annonce> liste = s.scrapSeLoger(type, "Vannes", null, null, null, null);
        for (Annonce a : liste) {
            System.out.println(a);
        }
//        ar.updateCollection(liste);
//        ar.dropData();

        // Créez une instance de votre annonce
//        Annonce annonce = ar.findById((long)190);
//        System.out.println(annonce.getType());
        // Initialisez les attributs...

//        // Créez un objet Gson
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//                .registerTypeAdapter(Type.class, new TypeAdapter())
//                .registerTypeAdapter(Address.class, new AddressAdapter())
//                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
//                .create();
//
//        // Convertissez l'annonce en JSON
//        String jsonAnnonce = gson.toJson(annonce);
//
//        // Affichez le JSON résultant
//        System.out.println(jsonAnnonce);


        // OuestFranceApi
        OuestFrance of = new OuestFrance();
//        of.scrapOuestFrance(type, "vannes", null, null, null,null);
//        Address a = of.ouestFranceApi("vann");
//        System.out.println(a);

      List<Annonce> annonceList = of.scrapOuestFrance(type, "vannes", null,null,null,null);
      for (Annonce a : annonceList) {
          System.out.println("========================");
          System.out.println(a);
      }
//      ar.updateCollection(annonceList);

//        InputStream input = new FileInputStream("src/main/resources/config.properties");
//        Properties properties = new Properties();
//        properties.load(input);
//        System.out.println(properties.getProperty("database.driver"));
//
//        System.out.println(Config.getProperty("database.driver"));
//        System.out.println(Config.getProperty("database.url"));
//        System.out.println(Config.getProperty("database.user"));
//        System.out.println(Config.getProperty("database.password"));
//        System.out.println(Config.getProperty("database.hbm2ddl"));
//        System.out.println(Config.getProperty("database.show_sql"));
//        DatabaseFormController d = new DatabaseFormController();
//        d.register();

    }
}
