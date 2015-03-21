package com.eventerzgz.model.commons;

import java.util.List;

import com.eventerzgz.model.Base;

import com.eventerzgz.model.exception.EventZgzException;
import com.eventerzgz.model.sparql.SparqlCategoryList;
import com.eventerzgz.model.sparql.SparqlResultList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persister;

/**
 * Created by joseluis on 21/3/15.
 */
@Root(strict=false, name = "result")
public class Category extends Base {

    @Element
    @Path("binding")
    private int id;

    @Element
    @Path("result/binding[name='tema']/literal")
    private String sTitle;

    private String sImage;

    public static List<Category> doParse(String sRawObj) throws EventZgzException{
        
        Persister persister = new Persister();
        SparqlResultList<Category> categories = null;

        try {
            categories = persister.read(SparqlCategoryList.class, sRawObj,false);
        } catch (Exception e) {
            throw new EventZgzException(e);
        }
        return categories.getList();
    }


    //GETTERS & SETTERS
    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getImageWithUri(){
        return "http://"+sImage;
    }

}
