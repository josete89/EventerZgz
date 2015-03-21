package com.eventerzgz.presenter.tutorial;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.presenter.BasePresenterIface;

/**
 * Created by joseluis on 21/3/15.
 */
public interface TutorialIface extends BasePresenterIface {

    public void fechedCategories(List<Category> categoryList);

}
