package fi.qvik.imagecompare.gif;

import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import fi.qvik.imagecompare.BaseActivity;
import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.util.ImageUtil;
import fi.qvik.imagecompare.util.ImageUtil.ImageServiceProvider;
import fi.qvik.imagecompare.util.QLog;

/**
 * Created by Tommy on 21/01/16.
 */
public class GifActivity extends BaseActivity {

    private final String TAG = "GifActivity";
//        private final String GIF_URL = "http://kuvaton.com/kuvei/finland_pwnz_russia.gif";
//    private final String GIF_URL = "https://media.giphy.com/media/7MZ0v9KynmiSA/giphy.gif";
//    private final String GIF_URL = "https://media.giphy.com/media/cE02lboc8JPO/giphy.gif";

    private String[] gifArray = new String[] {
            "https://media.giphy.com/media/cE02lboc8JPO/giphy.gif",
            "http://kuvaton.com/kuvei/finland_pwnz_russia.gif",
            "https://media.giphy.com/media/7MZ0v9KynmiSA/giphy.gif"
    };

    private WebView webView;
    private ImageView image;
    private SimpleDraweeView frescoImage;
    private TextView text;
    private CheckBox glideCheck, frescoCheck, webCheck;
    private ProgressBar loadingSpinner;
    private int index = 0;

    private void next() {
        index += 1;
        if(index >= gifArray.length) {
            index = 0;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_activity);

        webView = (WebView) findViewById(R.id.gif_web_view);
        image = (ImageView) findViewById(R.id.gif_image);
        frescoImage = (SimpleDraweeView) findViewById(R.id.fresco_gif_image);
        text = (TextView) findViewById(R.id.gif_text);
        glideCheck = (CheckBox) findViewById(R.id.gif_glide_check);
        glideCheck.setOnClickListener(onProviderClick);
        frescoCheck = (CheckBox) findViewById(R.id.gif_fresco_check);
        frescoCheck.setOnClickListener(onProviderClick);
        webCheck = (CheckBox) findViewById(R.id.gif_web_view_check);
        webCheck.setOnClickListener(onProviderClick);
        loadingSpinner = (ProgressBar) findViewById(R.id.gif_loading_spinner);
        loadingSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.color_primary), Mode.SRC_IN);
        loadingSpinner.setVisibility(View.GONE);

    }

    private OnClickListener onProviderClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                case R.id.gif_glide_check:
                    glideCheck.setChecked(true);
                    frescoCheck.setChecked(false);
                    webCheck.setChecked(false);
                    loadGif(ImageServiceProvider.GLIDE);
                    break;
                case R.id.gif_fresco_check:
                    frescoCheck.setChecked(true);
                    glideCheck.setChecked(false);
                    webCheck.setChecked(false);
                    loadGif(ImageServiceProvider.FRESCO);
                    break;
                case R.id.gif_web_view_check:
                    frescoCheck.setChecked(false);
                    glideCheck.setChecked(false);
                    webCheck.setChecked(true);
                    loadWebView();
                    break;

            }

        }
    };

    private void loadWebView() {
        frescoImage.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(gifArray[index]);
        // TODO: test this from file as well
    }

    private void loadGif(ImageServiceProvider provider) {
        loadingSpinner.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        image.setImageResource(android.R.color.white);
        text.setText("Loading GIF with: " + provider.toString());

        QLog.d(TAG, "Start GIF load: %s", gifArray[index]);
        final long start = System.currentTimeMillis();

        switch (provider) {
            default:
            case GLIDE:
                frescoImage.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(gifArray[index])
                        .asGif()
                        .crossFade()
                        .listener(new RequestListener<String, GifDrawable>() {

                            @Override
                            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                                QLog.e(e, TAG, "Glide onException");

                                loadingSpinner.setVisibility(View.GONE);
                                text.setText("Glide load error");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                int time = (int) (System.currentTimeMillis() - start);
                                QLog.i(TAG, "Glide image load ready in[%dms]", time);

                                loadingSpinner.setVisibility(View.GONE);
                                text.setText(String.format("Glide image ready in[%dms]", time));
                                return false;
                            }
                        })
                        .into(image);
                break;
            case FRESCO:
                frescoImage.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);

                ControllerListener listener = new BaseControllerListener<ImageInfo>() {

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        QLog.w(throwable, TAG, "Fresco onFailure");
                        loadingSpinner.setVisibility(View.GONE);
                        text.setText("Fresco onFailure");
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        int time = (int) (System.currentTimeMillis() - start);
                        QLog.i(TAG, "Fresco image load ready in[%dms]", time);

                        loadingSpinner.setVisibility(View.GONE);
                        text.setText(String.format("Fresco image ready in[%dms]", time));
                    }
                };

                Uri uri = Uri.parse(gifArray[index]);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setControllerListener(listener)
                        .setAutoPlayAnimations(true)
                .build();
                frescoImage.setController(controller);
                break;

        }

    }

    public void onClearCacheClick(View view) {
        next();

        image.setImageResource(android.R.color.white);
        webView.loadUrl(""); // so that previous image would not show on next load
        webView.setVisibility(View.GONE);
        frescoImage.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);

        webCheck.setChecked(false);
        glideCheck.setChecked(false);
        frescoCheck.setChecked(false);
        text.setText("Clearing cache");

        imageUtil.clearCache(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GifActivity.this, "CacheCleared", Toast.LENGTH_SHORT).show();
                text.setText("Select service");
            }
        });

    }

}
