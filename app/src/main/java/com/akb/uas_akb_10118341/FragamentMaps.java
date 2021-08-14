package com.akb.uas_akb_10118341;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.util.zip.Inflater;

public class FragamentMaps extends Fragment implements  OnMapReadyCallback, PermissionsListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(this.getContext(), getString(R.string.mapbox_access_token));

        View view = inflater.inflate(R.layout.maps, container, false);

        mapView = view.findViewById(R.id.mapView);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                FragamentMaps.this.map = mapboxMap;

                // Enable style maps
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        setMarker("Taman Hutan Raya Ir. H. Djuanda", "Kompleks Tahura, Jl. Ir. H. Juanda No.99, Ciburial, Kec. Cimenyan, Bandung, Jawa Barat 40198", -6.858456270305096, 107.63059816788858);
                        setMarker("Curug Cimahi", "Kertawangi, Kec. Cisarua, Kabupaten Bandung Barat, Jawa Barat 40551", -6.833322616948656, 107.66370093068144);
                        setMarker("Tebing Keraton", "Ciburial, Kec. Cimenyan, Kabupaten Bandung Barat", -6.833710892131754, 107.6637346053155);
                        setMarker("Bukit Moko", "Cimenyan, Kec. Cimenyan, Bandung, Jawa Barat 40197", -6.842071061074732, 107.67676661021811);

                        // Enable user location marker
                        enableLocationComponent(style);
                    }
                });
            }
        });

        return view;
    }

    private void setMarker(String judul, String keterangan, Double latitude, Double longitude) {
        // Initialize the MarkerViewManager
        MarkerViewManager markerViewManager = new MarkerViewManager(mapView, map);

        if(getActivity() != null) {
            // Use an XML layout to create a View object
            View customView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.marker_view_bubble, null);
            customView.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

            // Set the View's TextViews with content
            TextView titleTextView = customView.findViewById(R.id.marker_window_title);
            titleTextView.setText(judul);

            TextView snippetTextView = customView.findViewById(R.id.marker_window_snippet);
            snippetTextView.setText(keterangan);

            // Use the View to create a MarkerView which will eventually be given to
            // the plugin's MarkerViewManager class
            MarkerView markerView = new MarkerView(new LatLng(latitude, longitude), customView);
            markerViewManager.addMarker(markerView);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        try {
            // Check if permissions are enabled and if not request
            if (PermissionsManager.areLocationPermissionsGranted(this.getContext())) {
                // Get an instance of the component
                LocationComponent locationComponent = map.getLocationComponent();

                // Activate with options
                locationComponent.activateLocationComponent(
                        LocationComponentActivationOptions.builder(this.getContext(), loadedMapStyle).build());

                // Enable to make component visible
                locationComponent.setLocationComponentEnabled(true);

                // Set the component's camera mode
                locationComponent.setCameraMode(CameraMode.TRACKING);

                // Set the component's render mode
                locationComponent.setRenderMode(RenderMode.COMPASS);
            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(getActivity());
            }
        }catch(Exception e) {
            Toast.makeText(this.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this.getContext(), "Izinkan Aplikasi Ini untuk Mengakses Lokasi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }
}
/**
 Nama       : Muhammad Rijal Sulaeman
 Kelas      : IF-8
 NIM        : 10118341
 Tanggal    : 14 Agustus 2021
 **/