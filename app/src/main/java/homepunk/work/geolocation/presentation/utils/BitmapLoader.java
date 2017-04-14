package homepunk.work.geolocation.presentation.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import homepunk.work.geolocation.presentation.utils.interfaces.IBitmapLoader;
import rx.Single;
import rx.SingleSubscriber;

public class BitmapLoader implements IBitmapLoader {
    private Context context;
    private Picasso picasso;

    public BitmapLoader(Context context) {
        this.context = context;
    }

    @Override
    public Single<Bitmap> getBitmap(String url) {
        return Single.create((singleSubscriber) -> loadImage(singleSubscriber, url));
    }

    private void loadImage(SingleSubscriber<? super Bitmap> singleSubscriber, String url) {
        if (picasso != null) {
            picasso.load(url).into(new PicassoTargerLoader(singleSubscriber));
        }
    }

    public void setPicassoInstance(Picasso picasso) {
            this.picasso = picasso;
    }
}
