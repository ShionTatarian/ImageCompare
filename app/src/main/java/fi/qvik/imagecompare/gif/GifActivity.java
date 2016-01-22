package fi.qvik.imagecompare.gif;

import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
    //    private final String GIF_URL = "http://kuvaton.com/kuvei/finland_pwnz_russia.gif";
//    private final String GIF_URL = "https://media.giphy.com/media/7MZ0v9KynmiSA/giphy.gif";
    private final String GIF_URL = "https://media.giphy.com/media/cE02lboc8JPO/giphy.gif";

    private ImageView image;
    private TextView text;
    private CheckBox glideCheck, frescoCheck;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_activity);

        image = (ImageView) findViewById(R.id.gif_image);
        text = (TextView) findViewById(R.id.gif_text);
        glideCheck = (CheckBox) findViewById(R.id.gif_glide_check);
        glideCheck.setOnClickListener(onProviderClick);
        frescoCheck = (CheckBox) findViewById(R.id.gif_fresco_check);
        frescoCheck.setOnClickListener(onProviderClick);
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
                    loadGif(ImageServiceProvider.GLIDE);
                    break;
                case R.id.gif_fresco_check:
                    frescoCheck.setChecked(true);
                    glideCheck.setChecked(false);
                    loadGif(ImageServiceProvider.FRESCO);
                    break;

            }

        }
    };

    private void loadGif(ImageServiceProvider provider) {
        loadingSpinner.setVisibility(View.VISIBLE);
        image.setImageResource(android.R.color.white);
        text.setText("Loading GIF with: " + provider.toString());

        QLog.d(TAG, "Start GIF load: %s", GIF_URL);
        final long start = System.currentTimeMillis();

        switch (provider) {
            default:
            case GLIDE:
                Glide.with(this)
                        .load(GIF_URL)
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
                                text.setText(String.format("Glide image load ready in[%dms]", time));
                                return false;
                            }
                        })
                        .into(image);
                break;

        }

    }

    public void onClearCacheClick(View view) {
        image.setImageResource(android.R.color.white);
        glideCheck.setChecked(false);
        frescoCheck.setChecked(false);
        text.setText("Clearing cache");

        Glide g = Glide.get(this);
        g.clearMemory(); // memory needs to be cleared in main thread

        imageUtil.clearCache(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GifActivity.this, "CacheCleared", Toast.LENGTH_SHORT).show();
                text.setText("Select service");
            }
        });

    }

}
