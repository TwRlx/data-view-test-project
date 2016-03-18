package com.twrlx.dataviewtestproject.annotations;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Scope
@Retention(RUNTIME)
public @interface PhotosListFragmentScope {

}
