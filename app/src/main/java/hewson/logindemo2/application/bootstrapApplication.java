package hewson.logindemo2.application;


import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class bootstrapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
