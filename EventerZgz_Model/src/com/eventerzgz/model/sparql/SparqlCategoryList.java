package com.eventerzgz.model.sparql;

import com.eventerzgz.model.commons.Category;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 21/03/2015.
 */
@Root(strict=false)
public class SparqlCategoryList extends SparqlResultList<Category> {
}
