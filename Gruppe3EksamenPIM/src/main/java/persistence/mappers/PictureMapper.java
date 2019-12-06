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
    private static final String[] ALLOWED_FILE_TYPES = new String[]{"image/png", "image/jpeg"};
    private static final String[] ALLOWED_FILE_NAMES = new String[]{"png", "jpg"};
    private CloudinaryDatabase database;

    public PictureMapper(CloudinaryDatabase database) {
        this.database = database;
    }

    public String uploadProductPicture(List<Part> requestParts) {

        int countLoop = 0;
        int countInLoop = 0;
        String fileDir = "";

        try {
            //Make a folder for the picture to be stored while working with upload, if it's not already made.
            File uploadFolder = new File(database.getWorkingDirectory() + File.separator + UPLOAD_DIRECTORY);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }

            String pictureURL = null;

            for (Part part : requestParts) {
                countLoop++;
                countInLoop = 0;
                //Check each part to see if it is a part that could contain a file
                if (part != null && part.getSize() > 0) {
                    countInLoop++;
                    String fileName = part.getSubmittedFileName();
                    String fileContentType = part.getContentType();

                    countInLoop++;
                    if (isFileTypeAllowed(fileContentType)) {

                        //Make a local file of the picture
                        String filePlacement = database.getWorkingDirectory() + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;
                        fileDir = filePlacement;
                        countInLoop++;
                        part.write(filePlacement);
                        countInLoop++;
                        File file = new File(filePlacement);
                        countInLoop++;

                        //Upload the picture, and get the URL for the picture back
                        Cloudinary cloud = database.getCloudinaryConnection();
                        countInLoop++;
                        Map uploadResult = cloud.uploader().upload(file, ObjectUtils.emptyMap());
                        countInLoop++;
                        pictureURL = (String) uploadResult.get("url");
                        countInLoop++;

                        file.delete();
                        countInLoop++;
                        break;
                    }
                }
            }

            return pictureURL;

        } catch (IOException ex) {
            String allowedFileContentTypes = constructAllowedFileContentTypesString();
            throw new IllegalArgumentException("Picturefile could not be copied into the system. "
                    + "Please make sure the file is of any of the following types: "
                    + allowedFileContentTypes + "   :   " + countLoop + "," + countInLoop + "   :   " + database.getWorkingDirectory()
                    + "  :  " + fileDir + "  :  " + ex.getMessage());
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
        for (String allowedFileType : ALLOWED_FILE_NAMES) {
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
