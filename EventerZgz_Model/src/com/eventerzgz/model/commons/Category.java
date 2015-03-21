package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.exception.EventZgzException;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

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
        return null;
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
