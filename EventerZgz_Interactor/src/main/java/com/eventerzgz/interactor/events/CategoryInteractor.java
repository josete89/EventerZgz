package com.eventerzgz.interactor.events;

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

    public static List<Category> getCategories(File file) {
        List<Category> result = new ArrayList<>(0);
        try {
            Persister persister = new Persister();
            SparqlResultList<Category> categories = persister.read(SparqlCategoryList.class, file);
            result = categories.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] list) throws Exception {
        File file = new File("C:/temp/categories.xml");
        List<Category> categories = getCategories(file);
        System.out.println(categories);
    }

}
