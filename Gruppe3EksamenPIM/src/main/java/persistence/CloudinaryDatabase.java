/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import factory.SystemMode;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CloudinaryDatabase {

    private static final String PROPERTIESFILEPATH = "/cloudinary.properties";
    private Cloudinary cloudinary = null;
    private SystemMode systemMode;

    public CloudinaryDatabase(SystemMode systemMode) {
        this.systemMode = systemMode;
    }

    public Cloudinary getCloudinaryConnection() {
        if (cloudinary == null) {
            createCloudinary(PROPERTIESFILEPATH);
        }
        return cloudinary;
    }

    void createCloudinary(String propertiesFilePath) {
        try {
            InputStream input = CloudinaryDatabase.class.getResourceAsStream(propertiesFilePath);

            Properties pros = new Properties();
            pros.load(input);

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", pros.getProperty("cloud_name"),
                    "api_key", pros.getProperty("api_key"),
                    "api_secret", pros.getProperty("api_secret")));
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not make connection to the Picture Database");
        }
    }

}
