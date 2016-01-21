package fi.qvik.imagecompare.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fi.qvik.imagecompare.R;
import fi.qvik.imagecompare.util.ImageUtil;
import fi.qvik.imagecompare.util.ImageUtil.ImageServiceProvider;

public class MainActivity extends AppCompatActivity {

    private ImageUtil imageUtil = ImageUtil.getInstance();
    private ImageServiceProvider provider = ImageServiceProvider.PICASSO;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clearCache();

        updateContent();
    }

    private void clearCache() {
        imageUtil.clearCache();
    }

    private void updateContent() {
        if(adapter == null) {
            adapter = new ImageAdapter();
            recyclerView.setAdapter(adapter);
        }
        adapter.addAll(imageUtil.getImageList());
        adapter.notifyDataSetChanged();



    }

}
