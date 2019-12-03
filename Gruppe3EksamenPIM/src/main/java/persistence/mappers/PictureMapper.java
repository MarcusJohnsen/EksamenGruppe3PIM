/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Part;
import persistence.CloudinaryDatabase;

/**
 *
 * @author Michael N. Korsgaard
 */
public class PictureMapper {

    private static final String UPLOAD_DIRECTORY = "pictures";
    private static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    private static final String[] ALLOWED_FILE_TYPES = new String[]{"image/png", "image/jpeg"};
    private CloudinaryDatabase database;

    public PictureMapper(CloudinaryDatabase database) {
        this.database = database;
    }

    public String uploadProductPicture(List<Part> requestParts) {
        try {
            //Make a folder for the picture to be stored while working with upload, if it's not already made.
            File uploadFolder = new File(WORKING_DIRECTORY + File.separator + UPLOAD_DIRECTORY);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            String pictureURL = null;

            for (Part part : requestParts) {
                //Check each part to see if it is a part that could contain a file
                if (part != null && part.getSize() > 0) {
                    String fileName = part.getSubmittedFileName();
                    String fileContentType = part.getContentType();

                    if (isFileTypeAllowed(fileContentType)) {

                        //Make a local file of the picture
                        String filePlacement = WORKING_DIRECTORY + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;
                        part.write(filePlacement);
                        File file = new File(filePlacement);

                        //Upload the picture, and get the URL for the picture back
                        Cloudinary cloud = database.getCloudinaryConnection();
                        Map uploadResult = cloud.uploader().upload(file, ObjectUtils.emptyMap());
                        pictureURL = (String) uploadResult.get("url"); //  HELP ME UNDERSTAND THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                        //Do we need this ????????????????????????????????????
//                        Boolean booleanResult = null;
//                        if (part.getSubmittedFileName().equals(picturePath.replaceAll("\\s+", ""))) {
//                            booleanResult = true;
//                        }
                        file.delete();
                        break;
                    }
                }
            }

            return pictureURL;

        } catch (IOException ex) {
            String allowedFileContentTypes = constructAllowedFileContentTypesString();
            throw new IllegalArgumentException("Picturefile could not be copied into the system. "
                    + "Please make sure the file is of any of the following types: "
                    + allowedFileContentTypes);
        }

    }

    /**
     * Check if the content of the file is of any allowed types, as defined in the static variable allowedFileType.
     *
     * @param fileContentType String taken from a part of size larger then 0, taken with the Part.getContentType method.
     * @return a boolean value of whether or not the contentType is allowed as defined in the static variable allowedFileType.
     */
    private boolean isFileTypeAllowed(String fileContentType) {
        if (fileContentType != null) {
            for (String allowedFileType : ALLOWED_FILE_TYPES) {
                if (fileContentType.equalsIgnoreCase(allowedFileType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String constructAllowedFileContentTypesString() {
        String result = "";
        boolean firstLine = true;
        for (String allowedFileType : ALLOWED_FILE_TYPES) {
            if (firstLine) {
                result += allowedFileType;
                firstLine = false;
            } else {
                result += ", " + allowedFileType;
            }
        }
        return result;
    }

}
