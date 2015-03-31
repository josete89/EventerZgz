package com.eventerzgz.interactor.category;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;


public class CategoryMem implements CategoryDataSource {

    private static CategoryMem instance;
    private List<Category> eventCached;

    private CategoryMem(){

    }

    public static CategoryMem getInstance(){
        if(instance == null){
            synchronized (CategoryMem.class){
                if(instance == null){
                    instance  = new CategoryMem();
                }
            }
        }
        return instance;
    }

    public static void setCategoriesCached(List<Category> eventCached) {
        getInstance().eventCached = eventCached;
    }

    @Override
    public List<Category> getAllCategories() throws EventZgzException {
        return eventCached;
    }
}
