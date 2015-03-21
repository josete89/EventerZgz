package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;

/**
 * Created by joseluis on 21/3/15.
 */
public class ExtraInfo extends Base
{
    private String sCodAnexo;
    private String sImage;
    private String sDocument;
    private String sDocumentName;


    public static ExtraInfo doParse(String sRawObj){
        return new ExtraInfo();
    }


    //GETTERS & SETTERS
    public String getsCodAnexo() {
        return sCodAnexo;
    }

    public void setsCodAnexo(String sCodAnexo) {
        this.sCodAnexo = sCodAnexo;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getsDocument() {
        return sDocument;
    }

    public void setsDocument(String sDocument) {
        this.sDocument = sDocument;
    }

    public String getsDocumentName() {
        return sDocumentName;
    }

    public void setsDocumentName(String sDocumentName) {
        this.sDocumentName = sDocumentName;
    }


}
