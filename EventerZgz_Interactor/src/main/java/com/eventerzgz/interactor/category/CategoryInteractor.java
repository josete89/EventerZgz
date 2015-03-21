package com.eventerzgz.interactor.category;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;

import java.util.List;

/**
 * Created by Omar on 21/03/2015.
 */
public class CategoryInteractor {

    public static List<Category> getCategories() throws EventZgzException
    {
        return new CategoryRest().getAllCategories();
    }


    public static CategoryInteractor getInstance()
    {
        return new CategoryInteractor();
    }

}
