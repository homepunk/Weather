package homepunk.work.geolocation.presentation.utils.loaders.interfaces;

import android.graphics.Bitmap;

import rx.Single;

public interface IBitmapLoader  {
    Single<Bitmap> getBitmap(String url);
}
