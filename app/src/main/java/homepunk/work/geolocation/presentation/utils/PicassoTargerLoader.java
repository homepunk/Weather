package homepunk.work.geolocation.presentation.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import rx.SingleSubscriber;

/**
 * Created by Homepunk on 14.04.2017.
 */

public class PicassoTargerLoader implements Target {
    SingleSubscriber<? super Bitmap> singleSubscriber;

    public PicassoTargerLoader(SingleSubscriber<? super Bitmap> singleSubscriber) {
        this.singleSubscriber = singleSubscriber;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        singleSubscriber.onSuccess(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
