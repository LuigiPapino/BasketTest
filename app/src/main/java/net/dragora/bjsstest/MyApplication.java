package net.dragora.bjsstest;

import android.app.Application;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import net.dragora.bjsstest.injection.Graph;

import org.androidannotations.annotations.EApplication;

/**
 * Created by nietzsche on 27/12/15.
 */
@EApplication
public class MyApplication extends Application {

    private static MyApplication instance;
    private Graph graph;
    private RefWatcher refWatcher;

    @NonNull
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        graph = Graph.Initializer.init(this);
    }

    public Graph getGraph() {
        return graph;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
