/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.mappers;

import businessLogic.Category;
import java.util.ArrayList;


/**
 *
 * @author Michael N. Korsgaard
 */
public interface CategoryMapperInterface {
    
    public int addNewCategory(String categoryName, String cateoryDescription);
    
    public ArrayList<Category> getCategories();
    
}
