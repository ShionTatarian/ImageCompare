package fi.qvik.imagecompare.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.fresco.FrescoImageVH;
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
    private final String PREFERENCE_PROVIDER = "provider";
    private final ExecutorService threadPool;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private final Context context;
    private ImageServiceProvider provider = ImageServiceProvider.GLIDE;

    private final Picasso picasso;
    private final RequestManager glide;
    private final ImageLoader universalImageLoader;
    private final SharedPreferences preferences;
    private final Ion ion;

    private int imageSampleSize = 1000;

    public enum ImageServiceProvider {

        PICASSO(R.id.settings_picasso_radio_button),
        GLIDE(R.id.settings_glide_radio_button),
        FRESCO(R.id.settings_fresco_radio_button),
        UNIVERSAL_IMAGE_LOADER(R.id.settings_universal_image_loader),
        ION(R.id.settings_ion),;

        public final int checkBoxID;

        ImageServiceProvider(int checkBoxID) {
            this.checkBoxID = checkBoxID;
        }

    }

    private ImageUtil(Context ctx) {
        this.context = ctx;
        threadPool = Executors.newFixedThreadPool(5);

        this.preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        provider = ImageServiceProvider.values()[preferences.getInt(PREFERENCE_PROVIDER, ImageServiceProvider.GLIDE.ordinal())];

        Fresco.initialize(context);
        picasso = Picasso.with(ctx);
        glide = Glide.with(ctx);
        universalImageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(ctx.getCacheDir())) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .writeDebugLogs()
                .build();
        universalImageLoader.init(config);
        ion = Ion.getDefault(context);
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

    public void setProvider(@NonNull ImageServiceProvider provider) {
        this.provider = provider;
        clearCache(null);
        QLog.d(TAG, "New provider: %s", provider);

        preferences.edit().putInt(PREFERENCE_PROVIDER, provider.ordinal()).apply();
    }

    public ImageServiceProvider getProvider() {
        return provider;
    }

    public void clearCache(final Runnable callback) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                clearPicassoCache();
                clearGlideCache(); // glide cache clear needs to be run outside UI thread
                clearUniversalImageLoaderCache();
                clearFrescoCache();
                clearIonCache();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide g = Glide.get(context);
                        g.clearMemory(); // memory needs to be cleared in main thread

                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
            }
        });
    }

    private void clearIonCache() {
        ion.getCache().clear();
        ion.getBitmapCache().clear();
    }

    private void clearFrescoCache() {
        Fresco.getImagePipeline().clearCaches();
    }

    public void clearGlideCache() {
        Glide g = Glide.get(context);
        g.clearDiskCache();
    }

    public void clearUniversalImageLoaderCache() {
        universalImageLoader.clearDiskCache();
        universalImageLoader.clearMemoryCache();
    }

    public List<String> getImageList() {
        return list;
    }

    public void clearPicassoCache() {
        for (String url : list) {
            picasso.invalidate(url);
        }
    }

    public void setImage(ImageVH holder, String url) {
        if (holder.image == null) {
            QLog.w(TAG, "ImageView is null");
            return;
        } else if (TextUtils.isEmpty(holder.url)) {
            QLog.w(TAG, "Url is null");
        }
        holder.image.setTag(R.color.color_primary, url);
        holder.text.setText("Loading...");

        QLog.d(TAG, "set[%s]Image[%s]", provider, url);
        switch (provider) {
            case PICASSO:
                setPicassoImage(holder, url);
                break;
            case GLIDE:
                setGlideImage(holder, url);
                break;
            case FRESCO:
                QLog.d(TAG, "Fresco does not support ImageView");
                holder.text.setText("Fresco not supported");
//                setFrescoImage(holder, url);
                break;
            case UNIVERSAL_IMAGE_LOADER:
                setUniversalImage(holder, url);
                break;
            case ION:
                setIonImage(holder, url);
                break;

        }
    }

    private void setIonImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        ion.build(context)
                .load(url)
                .withBitmap()
                .placeholder(android.R.color.transparent)
                .animateIn(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                .intoImageView(holder.image)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        onImageReady(holder.text, url, start);
                    }
                });
    }

    private void setUniversalImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(400))
                .build();

        universalImageLoader.displayImage(url, holder.image, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                onImageReady(holder.text, url, start);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//                QLog.d(TAG, "UIL[%s] progress: %d / %d", imageUri, current, total);
            }
        });
    }

    public void setFrescoImage(final FrescoImageVH holder, final String url) {
        holder.text.setText("Loading...");

        final long start = System.currentTimeMillis();
        ControllerListener listener = new BaseControllerListener<ImageInfo>() {

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                QLog.w(throwable, TAG, "Fresco onFailure");
                holder.text.setText("Fresco onFailure");
            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                onImageReady(holder.text, url, start);
            }
        };
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(imageSampleSize, imageSampleSize))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(listener)
                .setImageRequest(request)
//                .setUri(url)
                .build();

        holder.image.setController(controller);
//        Uri uri = Uri.parse(url);
//        holder.image.setImageURI(uri);
    }

    private void setGlideImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        glide.load(url)
                .crossFade(300)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        QLog.e(e, TAG, "Glide onException");
                        return false; // if false Glide automatically assign the fail image
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        QLog.i(TAG, "Glide onResourceReady");
                        onImageReady(holder.text, url, start);

                        return false; // if false Glide automatically assign the image
                    }
                })
                .into(holder.image);

    }

    private void onImageReady(TextView text, String url, long start) {
        QLog.i(TAG, "Image[%s] load success & correct place", url);
        text.setText(getData(start));
    }

    private String getData(long start) {
        int time = (int) (System.currentTimeMillis() - start);
        return String.format("%s time: %dms", provider, time);
    }

    private void setPicassoImage(final ImageVH holder, final String url) {
        final long start = System.currentTimeMillis();
        picasso.load(url)
                .resize(500, 500) // if image too large app crashes!
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onImageReady(holder.text, url, start);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        QLog.e(TAG, "Image[%s] load error", url);
                        holder.text.setText("Picasso Error loading");
                    }
                });

    }

    public void runOnUiThread(Runnable runnable) {
        uiHandler.post(runnable);
    }

}
