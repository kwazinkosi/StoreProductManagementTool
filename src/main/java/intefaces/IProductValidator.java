package intefaces;



import exceptions.ValidationException;
import model.Product;

public interface IProductValidator {
    void validate(Product product) throws ValidationException;
}
