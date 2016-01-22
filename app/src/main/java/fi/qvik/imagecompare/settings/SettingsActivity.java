package fi.qvik.imagecompare.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import fi.qvik.imagecompare.BaseActivity;
import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.list.MainActivity;
import fi.qvik.imagecompare.util.ImageUtil;
import fi.qvik.imagecompare.util.ImageUtil.ImageServiceProvider;
import fi.qvik.imagecompare.util.QLog;

/**
 * Created by Tommy on 21/01/16.
 */
public class SettingsActivity extends BaseActivity {

    private final String TAG = "SettingsActivity";
    private final ImageUtil imageUtil = ImageUtil.getInstance();

    private RadioGroup radioGroup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        radioGroup = (RadioGroup) findViewById(R.id.settings_radio_group);
        ImageServiceProvider provider = imageUtil.getProvider();
        QLog.d(TAG, "Current provider: %s", provider);
        radioGroup.check(provider.checkBoxID);

        radioGroup.setOnCheckedChangeListener(onProviderChange);

    }

    private OnCheckedChangeListener onProviderChange = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for(ImageServiceProvider provider : ImageServiceProvider.values()) {
                if(provider.checkBoxID == checkedId) {
                    imageUtil.setProvider(provider);
                    return;
                }
            }

        }
    };

    public void onStartClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
