package com.example.kuba.activitytracker;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.IActivity;
import com.example.kuba.activitytracker.core.Point;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.PathOverlay;


public class MapActivity extends AppCompatActivity implements IActivity {

    MapView mapView;
    MapController mapController;
    GPS gps;
    PathOverlay myPath;//zamienic na polyline w OSMbonusPACK
    Marker currentLocMarker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        gps = GPS.getGPS();
        gps.addActivity(this);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(16);
        GeoPoint punkt = new GeoPoint(gps.getLatitude(), gps.getLongitude());
        if (!gps.getHistory().isEmpty()) {

            myPath = new PathOverlay(Color.RED, this);
            myPath.getPaint().setStrokeWidth(5);
            for (Point it : gps.getHistory()) {
                myPath.addPoint(new GeoPoint(it.getLatitude(), it.getLongitude()));
            }
            mapView.getOverlays().add(myPath);
        }
        mapController.setCenter(punkt);

        currentLocMarker = new Marker(mapView);
        currentLocMarker.setPosition(punkt);
        currentLocMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentLocMarker);

        mapView.invalidate();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClickGPS(View v) {
        Intent myintent = new Intent(MapActivity.this, GPS_Activity.class);
        MapActivity.this.startActivity(myintent);

    }

    public void refresh() {
        GeoPoint punkt = new GeoPoint(gps.getLoc().getLatitude(), gps.getLoc().getLongitude());
        myPath.addPoint(punkt);
        mapController.animateTo(punkt);
        currentLocMarker.setPosition(punkt);
        currentLocMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentLocMarker);

        mapView.invalidate();
    }

    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kuba.activitytracker/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        gps.delActivity(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kuba.activitytracker/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
}
