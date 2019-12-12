/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CloudinaryDatabase {

    private static final String PROPERTIESFILEPATH = "/cloudinary.properties";
    private static final String workingDirectory = System.getProperty("catalina.base");
    private Cloudinary cloudinary;

    public CloudinaryDatabase() {
        createCloudinary(PROPERTIESFILEPATH);
    }

    public Cloudinary getCloudinaryConnection() {
        if (cloudinary == null) {
            createCloudinary(PROPERTIESFILEPATH);
        }
        return cloudinary;
    }

    protected void createCloudinary(String propertiesFilePath) {
        try {
            InputStream input = CloudinaryDatabase.class.getResourceAsStream(propertiesFilePath);

            Properties pros = new Properties();
            pros.load(input);
            
            String cloud_name = pros.getProperty("cloud_name");
            String api_key = pros.getProperty("api_key");
            String api_secret = pros.getProperty("api_secret");
            
            if(cloud_name == null || api_key == null || api_secret == null){
                throw new IOException();
            }

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloud_name,
                    "api_key", api_key,
                    "api_secret", api_secret));
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not read from properties-file");
        }
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    protected void setCloudinary(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    
    
    
    
}
