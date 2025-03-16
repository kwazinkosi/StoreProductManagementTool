package service;

import org.apache.commons.lang3.StringUtils;   // For StringUtils

import exceptions.ValidationException;
import model.Product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class RequestParser {
	
    public Product parseProductRequest(HttpServletRequest request) throws ValidationException {
        Product product = new Product();
        
        String title = sanitizeInput(request.getParameter("title"));
        if (StringUtils.isBlank(title)) {
            throw new ValidationException("Title is required");
        }
        product.setTitle(title);
        
        try {
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            product.setSize(Integer.parseInt(request.getParameter("size")));
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for quantity or size");
        }
        
        return product;
    }

    public String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");
        for (String token : header.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }

    private String sanitizeInput(String input) {
        if (input == null) return null;
        return input.replaceAll("[<>]", "");
    }
}
