package factory;

public class UserInputException extends Exception {
    
    public UserInputException(String msg){
        super(msg);
    }
    
    /*
    This exceptions is shown integrated in the Product class on line 262, at the method validateProductInput.
    Continue to BusinessController on 2 methods: createNewProduct (line 69) & editProduct (line 89).
    These go the the two command classes AddProductCommand on line 52, and EditProductCommand on line 45
    */
    
}
