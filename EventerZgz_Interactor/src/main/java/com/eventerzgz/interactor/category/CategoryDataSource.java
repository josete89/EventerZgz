package com.eventerzgz.interactor.category;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;


public interface CategoryDataSource {

     List<Category> getAllCategories() throws EventZgzException;

}
