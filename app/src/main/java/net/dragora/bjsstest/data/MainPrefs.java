package net.dragora.bjsstest.data;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by nietzsche on 26/12/15.
 */
@SharedPref
public interface MainPrefs {

    @DefaultString("")
    String basket();


}
