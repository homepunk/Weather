package homepunk.work.geolocation.presentation.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import homepunk.work.geolocation.presentation.utils.interfaces.IBitmapLoader;
import rx.Single;
import rx.SingleSubscriber;
import timber.log.Timber;

public class BitmapLoader implements IBitmapLoader {
    private Picasso picasso;

    @Override
    public Single<Bitmap> getBitmap(String url) {
        return Single.create((singleSubscriber) -> loadImage(singleSubscriber, url));
    }

    private void loadImage(SingleSubscriber<? super Bitmap> singleSubscriber, String url) {
        if (picasso != null) {
            Timber.i("Url: " + url);
            picasso.load(url).into(new PicassoTargerLoader(singleSubscriber));
        }
    }

    public void setPicassoInstance(Picasso picasso) {
            this.picasso = picasso;
    }
}
