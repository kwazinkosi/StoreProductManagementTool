package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;   // For StringUtils

import model.Product;

import exceptions.ValidationException;
import intefaces.IProductValidator;

public class ProductValidatorImpl implements IProductValidator {
    
	@Override
    public void validate(Product product) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (product == null) {
            throw new ValidationException("Product cannot be null");
        }

        if (StringUtils.isBlank(product.getTitle())) {
            errors.add("Product title is required");
        }

        if (product.getQuantity() < 0) {
            errors.add("Product quantity cannot be negative");
        }

        if (product.getSize() <= 0) {
            errors.add("Product size must be positive");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
