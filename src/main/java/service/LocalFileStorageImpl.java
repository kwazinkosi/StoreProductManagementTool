package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.IOUtils;         

import intefaces.IFileStorage;
//import utils.LoggingManager;

public class LocalFileStorageImpl implements IFileStorage {
	
    private static final String UPLOAD_DIRECTORY = "product-images";
    private final String basePath;

    public LocalFileStorageImpl(String basePath) {
        this.basePath = basePath;
        createUploadDirectory();
    }

    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {
        String uniqueFileName = generateUniqueFileName(fileName);
        String filePath = getFullPath(uniqueFileName);
        
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            IOUtils.copy(inputStream, output);
        }
        return UPLOAD_DIRECTORY + "/" + uniqueFileName;
    }

    private String generateUniqueFileName(String fileName) {
       
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

	@Override
    public void deleteFile(String filePath) throws IOException {
        
		File file = new File(getFullPath(filePath));
        if (!file.delete() && file.exists()) {
            throw new IOException("Failed to delete file: " + filePath);
        }
    }

    @Override
    public boolean isValidFileType(String fileName) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        return Arrays.stream(allowedExtensions)
                    .anyMatch(ext -> fileName.toLowerCase().endsWith(ext));
    }

    private String getFullPath(String fileName) {
        return basePath + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;
    }

    private void createUploadDirectory() {
        File uploadDir = new File(basePath + File.separator + UPLOAD_DIRECTORY);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
//        	LoggingManager.error("Failed to create upload directory");
            throw new RuntimeException("Failed to create upload directory");
        }
    }

}