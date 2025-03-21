package intefaces;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.Part;

public interface IFileStorage {
	
    String saveFile(InputStream inputStream, String fileName) throws IOException;
    void deleteFile(String filePath) throws IOException;
	boolean isValidFileType(Part filePart) throws IOException;
}