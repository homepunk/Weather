package homepunk.work.geolocation.presentation.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import homepunk.work.geolocation.R;
import timber.log.Timber;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    @Bind(R.id.popup_weather_icon)
    ImageView popUpWeatherIcon;
    @Bind(R.id.popup_weather_textview)
    TextView popUpWeatherInfo;

    private View view;
    private Picasso picasso;

    public CustomInfoWindowAdapter(Context context) {
        picasso = Picasso.with(context);
        view = ((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_popup, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Timber.i("Inflating " + marker.getTitle());
        popUpWeatherIcon = (ImageView) view.findViewById(R.id.popup_weather_icon);
        popUpWeatherInfo = (TextView) view.findViewById(R.id.popup_weather_textview);

        popUpWeatherInfo.setText(marker.getTitle());

        picasso.load(marker.getSnippet())
               .into(popUpWeatherIcon);

        return view;
    }
}
