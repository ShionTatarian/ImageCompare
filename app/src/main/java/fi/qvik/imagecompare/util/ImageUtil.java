package fi.qvik.imagecompare.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fi.qvik.imagecompare.list.ImageVH;

/**
 * Created by Tommy on 20/01/16.
 */
public class ImageUtil {

    private static ImageUtil instance;

    private final List<String> list = new ArrayList<String>() {{
        add("https://images.unsplash.com/photo-1450101215322-bf5cd27642fc?crop=entropy&fit=crop&fm=jpg&h=950&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=1675");
        add("https://images.unsplash.com/photo-1442406964439-e46ab8eff7c4?crop=entropy&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=1675");
        add("https://images.unsplash.com/photo-1432821596592-e2c18b78144f?crop=entropy&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=1675");
        add("https://images.unsplash.com/photo-1453283860688-aed20ba9894c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max&s=235f09482b817a02e27081e5b099b77b");
        add("https://images.unsplash.com/photo-1453283780484-5de619b4a341?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453282696013-7eb6efcf9325?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453282716202-de94e528067c?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453280339213-efb07f95531b?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453261945531-9740d44bf1a4?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453259119614-6f02e8d5b25a?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453234570968-3ccc4228c867?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453216845922-910290232551?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1453195644148-563910c91cf7?crop=entropy&dpr=2&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1675");
        add("https://images.unsplash.com/photo-1432821596592-e2c18b78144f?crop=entropy&fit=crop&fm=jpg&h=1100&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=1675");
        add("https://images.encyclopediadramatica.se/thumb/2/28/Transparent_Trollface2.png/120px-Transparent_Trollface2.png");
        add("http://www.digitalbuzzblog.com/wp-content/uploads/2013/09/Infographic-2013-Mobile-Growth-Statistics-Large.jpg");
//
    }};

    private final String TAG = "ImageUtil";
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private final Context context;
    private ImageServiceProvider provider = ImageServiceProvider.GLIDE;

    private final Picasso picasso;
    private final RequestManager glide;

    public enum ImageServiceProvider {

        PICASSO,

        GLIDE,;

    }

    private ImageUtil(Context ctx) {
        this.context = ctx;
        picasso = Picasso.with(ctx);
        glide = Glide.with(ctx);
    }

    public static void init(Context ctx) {
        if (instance != null) {
            throw new IllegalStateException("ImageUtil already initialized");
        }
        instance = new ImageUtil(ctx);
    }

    public static ImageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ImageUtil not yet initialized");
        }

        return instance;
    }

    public void clearCache() {
        switch (provider) {
            case PICASSO:
                clearPicassoCache(context);
                break;
        }
    }

    public List<String> getImageList() {
        return list;
    }

    private void clearPicassoCache(Context ctx) {
        Picasso p = Picasso.with(ctx);
        for (String url : list) {
            p.invalidate(url);
        }
    }

    public void setImage(ImageVH holder, String url) {
        if (holder.image == null) {
            QLog.w(TAG, "ImageView is null");
            return;
        } else if (TextUtils.isEmpty(holder.url)) {
            QLog.w(TAG, "Url is null");
        }

        holder.text.setText("Loading...");

        QLog.d(TAG, "set[%s]Image[%s]", provider, url);
        switch (provider) {
            case PICASSO:
                setPicassoImage(holder, url);
                break;
            case GLIDE:
                setGlideImage(holder, url);
                break;

        }


    }

    private void setGlideImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        glide.load(url)
                .crossFade(300)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        QLog.e(e, TAG, "Glide onException");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        QLog.i(TAG, "Glide onResourceReady");
                        onImageReady(holder, url, start);

                        return false;
                    }
                })
                .into(holder.image);

    }

    private void onImageReady(ImageVH holder, String url, long start) {
        if (url.equals(holder.url)) {
            QLog.i(TAG, "Image[%s] load success & correct place", url);
            holder.text.setText(getData(start));
        } else {
            QLog.w(TAG, "Image[%s] load success, WRONG place", url);
            holder.text.setText("Wrong url!?");
        }
    }

    private String getData(long start) {
        return String.format("time: %sms", Long.toString(System.currentTimeMillis() - start));
    }

    private void setPicassoImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        picasso.load(url)
                .resize(500, 500)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onImageReady(holder, url, start);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        QLog.e(TAG, "Image[%s] load error", url);
                        holder.text.setText("Error loading");
                    }
                });

    }

    public void runOnUiThread(Runnable runnable) {
        uiHandler.post(runnable);
    }

}
