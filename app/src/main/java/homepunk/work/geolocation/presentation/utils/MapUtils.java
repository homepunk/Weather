package homepunk.work.geolocation.presentation.utils;

import com.google.android.gms.maps.GoogleMap;

public class MapUtils {
    public static GoogleMap getCusomizedMap(GoogleMap map){
        if (map != null) {
            map.getUiSettings().setZoomGesturesEnabled(true);

            return map;
        } else {
            return null;
        }
    }
}
