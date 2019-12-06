package persistence.Json;

import businessLogic.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonHandler {

    Gson gson;
    File file;
    String filePath;

    public JsonHandler() {
        this.gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        this.file = new File("productOutput.json");
        this.filePath = file.getAbsolutePath();
    }

    public String makeProductJson(TreeSet<Product> productList) {
        String result = "[\n";
        List<Product> list = new ArrayList();
        for (Product product : productList) {
            result += gson.toJson(product) + "\n";
        }
        result += "]";

        return result;
    }

    public File createJsonFileFromProductList(TreeSet<Product> productList) {

        try {
            file = new File("productOutput.json");
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

    public String getFilePath() {
        return filePath;
    }

}
