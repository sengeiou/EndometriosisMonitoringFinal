package com.benlefevre.endometriosismonitoring.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private final LatLng mLatLng;
    private final String mTitle;

    public ClusterMarker(double lat,double lng, String title) {
        mLatLng = new LatLng(lat,lng);
        mTitle = title;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
