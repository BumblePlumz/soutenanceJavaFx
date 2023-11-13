package fr.cda.scraping.service;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.xml.v0_6.XmlReader;

import java.io.File;
import java.util.Map;

public class OpenStreetMap {
    public void init(){
        String osmPbfFilePath = "fr/cda/scraping/files/france-latest.osm.pbf";
        try {
            File osmFile = new File(osmPbfFilePath);
            XmlReader reader = new XmlReader(osmFile, true, CompressionMethod.None);
            reader.setSink(new OSMDataSink());
            reader.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static class OSMDataSink implements Sink {
        @Override
        public void process(EntityContainer entityContainer) {
            // Traitement des entités (nœuds, chemins, relations)
            // Vous pouvez extraire des informations de l'entité ici
            System.out.println(entityContainer.getEntity().toString());
        }

        @Override
        public void initialize(Map<String, Object> metaData) {
            // Initialisation
        }

        @Override
        public void complete() {
            // Fin du traitement
        }

        @Override
        public void close() {
            // Fermeture des ressources
        }
    }
}
