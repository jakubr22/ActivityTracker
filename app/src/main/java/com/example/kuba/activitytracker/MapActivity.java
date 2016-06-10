package com.example.kuba.activitytracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kuba.activitytracker.core.GPS;
import com.example.kuba.activitytracker.core.IActivity;
import com.example.kuba.activitytracker.core.Point;

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

    }
    public void onClickGPS(View v) {
        Intent myintent = new Intent(MapActivity.this, GPS_Activity.class);
        MapActivity.this.startActivity(myintent);

    }
    public void refresh() {
        GeoPoint punkt = new GeoPoint(gps.getLoc().getLatitude(), gps.getLoc().getLongitude());
        myPath.addPoint(punkt);
        mapController.setCenter(punkt);
        currentLocMarker.setPosition(punkt);
        currentLocMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentLocMarker);

        mapView.invalidate();
    }
    public void onStop(){
        super.onStop();
        gps.delActivity(this);
    }


}
