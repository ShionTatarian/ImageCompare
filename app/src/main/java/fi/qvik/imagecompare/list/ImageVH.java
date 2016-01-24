package fi.qvik.imagecompare.list;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import fi.qvik.imagecompare.R;

/**
 * Created by Tommy on 20/01/16.
 */
public class ImageVH extends RecyclerView.ViewHolder {

    public String url;
    public ImageView image;
    public TextView text;

    ImageVH(View v) {
        super(v);
        image = (ImageView) v.findViewById(R.id.image_list_row_image);
        text = (TextView) v.findViewById(R.id.image_list_row_text);
        v.setOnLongClickListener(onBackgroundClick);
    }

    private OnLongClickListener onBackgroundClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(TextUtils.isEmpty(url)) {
                return false;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(browserIntent);

            return true;
        }

    };

}
