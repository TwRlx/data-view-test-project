package com.twrlx.dataviewtestproject.annotations;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ClientCache {

}
