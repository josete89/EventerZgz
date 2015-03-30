package com.eventerzgz.interactor.category;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;


public class CategoryInteractor {

    public static List<Category> getCategories() throws EventZgzException
    {
        List<Category> categoriesList;

        if(CategoryMem.getInstance().getAllCategories() != null && CategoryMem.getInstance().getAllCategories().size() > 0){
            categoriesList = CategoryMem.getInstance().getAllCategories();
        }else{
            categoriesList = new CategoryRest().getAllCategories();
            CategoryMem.setCategoriesCached(categoriesList);
        }

        return categoriesList;
    }


}
