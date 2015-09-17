package com.geomobile.vision.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;

import com.geomobile.arcore.VisionCore;
import com.geomobile.arcore.model.VisionCategory;
import com.geomobile.arcore.model.VisionGeoPoi;
import com.geomobile.arcore.model.VisionImage;
import com.geomobile.arcore.model.VisionModelManager;

public class MyModelManager extends VisionModelManager {

	VisionCategory turistCategory;

	@Override
	public void loadCategories(Context ctx, String url) {
		// TODO Auto-generated method stub
		VisionImage iconCat1 = new VisionImage();
		iconCat1.setImageURL("icon_cat");
		VisionImage iconCat2 = new VisionImage();
		iconCat2.setImageURL("icon_mapa");
		VisionImage iconCat3 = new VisionImage();
		iconCat3.setImageURL("icon_mapa_no_sel");

		turistCategory = new VisionCategory();
		turistCategory.setTitle("Tourist places");
		turistCategory.setIcon(iconCat1);
		turistCategory.setSelectedIcon(iconCat2);
		turistCategory.setNoSelectedIcon(iconCat3);
		VisionCore.getCategories().add(turistCategory);

	}

	@Override
	public void loadPois(Context ctx, String url, boolean update) {
		// TODO Auto-generated method stub
		try {
			loadingPois = true;
			generateRandomPois();
			if (update) {
				updateNearestPois(ctx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loadingPois = false;
		}
	}

	private void generateRandomPois() {
		Location currentLocation = VisionCore.core.model.getLocation();
		List<VisionGeoPoi> newPois = new ArrayList<VisionGeoPoi>();
		int poiID = 1;
		for (int i = 0; i < 2; i++) {
			double latitudeChange = Math.random() * 0.005;
			double longitudeChange = Math.random() * 0.005;
			CustomGeoPoi poi = new CustomGeoPoi();
			poi.setId(String.format("000%d", poiID));
			poi.setTitle(String.format("Punto de Interes %d", poiID));
			poi.setSubtitle(String.format("Subtitulo para el POI %d", poiID));
			poi.setText(String.format("Texto para el POI %d", i));
			poi.setLatitude(currentLocation.getLatitude() - latitudeChange);
			poi.setLongitude(currentLocation.getLongitude() - longitudeChange);
			poi.setWeb(String.format("URL for POI %d", poiID));
			poi.getCategories().add(turistCategory);
			poi.setPoiRating(""+Math.round(Math.random()*10));
			poiID++;
			newPois.add(poi);
		}
		for (int i = 0; i < 2; i++) {
			double latitudeChange = Math.random() * 0.005;
			double longitudeChange = Math.random() * 0.005;
			CustomGeoPoi poi = new CustomGeoPoi();
			poi.setId(String.format("000%d", poiID));
			poi.setTitle(String.format("Point of Interest %d", poiID));
			poi.setSubtitle(String.format("Subtitle for POI %d", poiID));
			poi.setText(String.format("Text for POI %d", i));
			poi.setLatitude(currentLocation.getLatitude() + latitudeChange);
			poi.setLongitude(currentLocation.getLongitude() + longitudeChange);
			poi.setWeb(String.format("URL for POI %d", poiID));
			poi.getCategories().add(turistCategory);
			poi.setPoiRating(""+Math.round(Math.random()*10));
			poiID++;
			newPois.add(poi);
		}
		for (int i = 0; i < 2; i++) {
			double latitudeChange = Math.random() * 0.005;
			double longitudeChange = Math.random() * 0.005;
			CustomGeoPoi poi = new CustomGeoPoi();
			poi.setId(String.format("000%d", poiID));
			poi.setTitle(String.format("Point of Interest %d", poiID));
			poi.setSubtitle(String.format("Subtitle for POI %d", poiID));
			poi.setText(String.format("Text for POI %d", i));
			poi.setLatitude(currentLocation.getLatitude() + latitudeChange);
			poi.setLongitude(currentLocation.getLongitude() - longitudeChange);
			poi.setWeb(String.format("URL for POI %d", poiID));
			poi.getCategories().add(turistCategory);
			poi.setPoiRating(""+Math.round(Math.random()*10));
			poiID++;
			newPois.add(poi);
		}
		for (int i = 0; i < 2; i++) {
			double latitudeChange = Math.random() * 0.005;
			double longitudeChange = Math.random() * 0.005;
			CustomGeoPoi poi = new CustomGeoPoi();
			poi.setId(String.format("000%d", poiID));
			poi.setTitle(String.format("Point of Interest %d", poiID));
			poi.setSubtitle(String.format("Subtitle for POI %d", poiID));
			poi.setText(String.format("Text for POI %d", i));
			poi.setLatitude(currentLocation.getLatitude() - latitudeChange);
			poi.setLongitude(currentLocation.getLongitude() + longitudeChange);
			poi.setWeb(String.format("URL for POI %d", poiID));
			poi.getCategories().add(turistCategory);
			poi.setPoiRating(""+Math.round(Math.random()*10));
			poiID++;
			newPois.add(poi);
		}
		pois = newPois;
	}

}
