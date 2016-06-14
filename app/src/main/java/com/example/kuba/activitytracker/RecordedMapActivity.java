package com.example.kuba.activitytracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.Point;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.PathOverlay;

public class RecordedMapActivity extends AppCompatActivity {

    MapView mapView;
    MapController mapController;
    GPS gps;
    PathOverlay myPath;//zamienic na polyline w OSMbonusPACK
    Marker currentLocMarker;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_map);
        gps = GPS.getGPS();

        mapView = (MapView) findViewById(R.id.mapvieww);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(16);

        gps.getHistoryActivity().getHistory().get(gps.getHistoryActivity().getChoosed()).getLog().getFirst();

        GeoPoint punkt = new GeoPoint(gps.getLatitude(), gps.getLongitude());
        if (!gps.getHistoryActivity().getHistory().get(gps.getHistoryActivity().getChoosed()).getLog().isEmpty()) {

            myPath = new PathOverlay(Color.RED, this);
            myPath.getPaint().setStrokeWidth(5);
            for (Point it : gps.getHistoryActivity().getHistory().get(gps.getHistoryActivity().getChoosed()).getLog()) {
                myPath.addPoint(new GeoPoint(it.getLatitude(), it.getLongitude()));
                punkt = new GeoPoint(it.getLatitude(), it.getLongitude());
            }
            mapView.getOverlays().add(myPath);
        }
        mapController.setCenter(punkt);

        currentLocMarker = new Marker(mapView);
        currentLocMarker.setPosition(punkt);
        currentLocMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentLocMarker);
        punkt = new GeoPoint(gps.getHistoryActivity().getHistory().get(gps.getHistoryActivity().getChoosed()).getLog().getFirst().getLatitude(), gps.getHistoryActivity().getHistory().get(gps.getHistoryActivity().getChoosed()).getLog().getFirst().getLongitude());
        currentLocMarker = new Marker(mapView);
        currentLocMarker.setPosition(punkt);
        currentLocMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentLocMarker);

        mapView.invalidate();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }
}
