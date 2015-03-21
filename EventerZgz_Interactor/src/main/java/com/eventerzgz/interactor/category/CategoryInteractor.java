package com.eventerzgz.interactor.category;

import com.eventerzgz.model.exception.EventZgzException;
import com.eventerzgz.model.sparql.SparqlCategoryList;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.sparql.SparqlResultList;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
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
