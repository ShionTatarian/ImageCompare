package fi.qvik.imagecompare.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.util.ImageUtil;
import fi.qvik.imagecompare.util.ImageUtil.ImageServiceProvider;

/**
 * Created by Tommy on 20/01/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageVH> {

    private ImageUtil imageUtil = ImageUtil.getInstance();

    private final List<String> list = new ArrayList<>();

    public ImageAdapter() {
        super();
    }

    @Override
    public ImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageVH holder, int position) {
        String url = list.get(position);
        holder.url = url;
        imageUtil.setImage(holder, url);

//        holder.text.setText(holder.url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(String url) {
        list.add(url);
    }

    public void addAll(Collection<String> list) {
        this.list.addAll(list);
    }

}
