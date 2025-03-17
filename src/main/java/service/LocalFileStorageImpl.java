package service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.io.IOUtils;

import intefaces.IFileStorage;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class LocalFileStorageImpl implements IFileStorage {
    
    private static final String UPLOAD_DIRECTORY = "uploads/product-images";
    private final Path basePath;
    
    public LocalFileStorageImpl(ServletContext servletContext) {
        this.basePath = Paths.get(servletContext.getRealPath("/"), UPLOAD_DIRECTORY);
        createUploadDirectory();
    }

    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {
        
    	String uniqueFileName = generateUniqueFileName(fileName);
        Path filePath = basePath.resolve(uniqueFileName);
        
        try (OutputStream output = Files.newOutputStream(filePath)) {
            IOUtils.copy(inputStream, output);
        }
        
        return uniqueFileName; // Return only the filename
    }

    private String generateUniqueFileName(String fileName) {
        
    	String extension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
       
    	Path filePath = basePath.resolve(fileName);
        Files.deleteIfExists(filePath);
    }

    @Override
    public boolean isValidFileType(Part filePart) throws IOException {
       
    	String[] allowedMimeTypes = {"image/jpeg", "image/png", "image/gif"};
        String mimeType = filePart.getContentType();
        return Arrays.stream(allowedMimeTypes).anyMatch(mimeType::equalsIgnoreCase);
    }

    private void createUploadDirectory() {
        try {
            Files.createDirectories(basePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory at " + basePath, e);
        }
    }
}