package persistence.Json;

import businessLogic.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonHandler {

    private static final Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    private static final String WORKING_DIRECTORY = System.getProperty("catalina.base");
    private static final String FILE_FOLDER = File.separator + "json";
    private String filePath;

    public JsonHandler() {

        File uploadFolder = new File(WORKING_DIRECTORY + FILE_FOLDER);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }
        this.filePath = WORKING_DIRECTORY + FILE_FOLDER + File.separator;
    }

    public String makeProductJson(TreeSet<Product> productList) {
        String result = gson.toJson(productList);
        return result;
    }

    public File createJsonFileFromProductList(TreeSet<Product> productList) {

        try {
            File file = new File(filePath + "productOutput.json");
            FileWriter fw;
            fw = new FileWriter(file, false);
            BufferedWriter bufWriter = new BufferedWriter(fw);

            bufWriter.write(makeProductJson(productList));
            bufWriter.newLine();
            bufWriter.flush();

            return file;
        } catch (IOException ex) {
            Logger.getLogger(JsonHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Could not produce the json file");
        }
    }
}
