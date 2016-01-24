package fi.qvik.imagecompare.fresco;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.util.ImageUtil;

/**
 * Created by Tommy on 20/01/16.
 */
public class FrescoImageAdapter extends RecyclerView.Adapter<FrescoImageVH> {

    private ImageUtil imageUtil = ImageUtil.getInstance();

    private final List<String> list = new ArrayList<>();

    public FrescoImageAdapter() {
        super();
    }

    @Override
    public FrescoImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FrescoImageVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.fresco_image_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(FrescoImageVH holder, int position) {
        String url = list.get(position);
        holder.url = url;
        imageUtil.setFrescoImage(holder, url);
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
