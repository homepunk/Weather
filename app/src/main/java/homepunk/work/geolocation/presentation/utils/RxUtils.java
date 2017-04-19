package homepunk.work.geolocation.presentation.utils;

import android.app.Activity;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Homepunk on 19.04.2017.
 */

public class RxUtils {
    private static RxPermissions rxPermissions;

    public static <T> Single.Transformer<T, T> applySchedulers() {
        return single -> single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void verifyPermissionsGranted(Activity activity) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(activity);
        }

        if (!rxPermissions.isGranted(ACCESS_FINE_LOCATION)) {
            rxPermissions.request(ACCESS_FINE_LOCATION)
                    .subscribe(aBoolean -> {
                        Timber.i("permission granted:  " + String.valueOf(aBoolean));
                    });
        }
    }
}
