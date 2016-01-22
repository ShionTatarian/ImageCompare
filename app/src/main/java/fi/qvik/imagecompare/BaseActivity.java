package fi.qvik.imagecompare;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import fi.qvik.imagecompare.gif.GifActivity;
import fi.qvik.imagecompare.settings.SettingsActivity;
import fi.qvik.imagecompare.util.ImageUtil;

/**
 * Created by Tommy on 21/01/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected final ImageUtil imageUtil = ImageUtil.getInstance();

    @Nullable
    protected Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

        finish();
    }

    public void onGifClick(View view) {
        Intent intent = new Intent(this, GifActivity.class);
        startActivity(intent);
    }

    public void onClearCacheClick(View view) {
        Glide g = Glide.get(this);
        g.clearMemory(); // memory needs to be cleared in main thread

        imageUtil.clearCache(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, "CacheCleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
