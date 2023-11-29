package fr.cda.immobilier.utils.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Type;
import fr.cda.immobilier.utils.adapter.AddressAdapter;
import fr.cda.immobilier.utils.adapter.LocalDateTimeAdapter;
import fr.cda.immobilier.utils.adapter.TypeAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

/**
 * Gestion des fichiers
 */
public class FilesTools {
    public static String fileTxtPath = "src/main/resources/fr/cda/immobilier/files/annonces.txt";

    /**
     * Sauvegarde au format json
     * @param file fichier
     * @param collection données
     */
    public static void saveFile(File file, Collection<?> collection){
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(Type.class, new TypeAdapter())
                    .registerTypeAdapter(Address.class, new AddressAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .create();

            gson.toJson(collection, writer);
        } catch (IOException e) {
            LoggerTools.logError("Class FilesTools : erreur dans saveFile", e.getCause());
        }
    }

    /**
     * Sauvegarde au format text
     * @param collection
     */
    public static void saveTxtFile(Collection<?> collection) {
        try (FileWriter writer = new FileWriter(fileTxtPath)) {
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object item = iterator.next();
                writer.write(item.toString());

                if (iterator.hasNext()) {
                    writer.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            LoggerTools.logError("Class FilesTools : erreur dans saveWithoutBrackets", e.getCause());
        }
    }
}
