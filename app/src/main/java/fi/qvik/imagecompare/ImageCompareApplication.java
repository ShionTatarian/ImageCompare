package fi.qvik.imagecompare;

import android.app.Application;

import fi.qvik.imagecompare.util.ImageUtil;
import fi.qvik.imagecompare.util.QLog;

/**
 * Created by Tommy on 20/01/16.
 */
public class ImageCompareApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QLog.setDebug(BuildConfig.DEBUG);
        ImageUtil.init(this);
    }
}
