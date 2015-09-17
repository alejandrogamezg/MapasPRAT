package com.geomobile.vision.demo;

import android.content.Context;
import android.location.Location;

import com.geomobile.arcore.model.VisionModelManager;

public class MyModelManagerPreload extends VisionModelManager {
		
	
	@Override
	public void loadPois(Context ctx, String url, boolean update) {
		loadingPois = true;
		try {

			new CustomGeoPoiParser(ctx, url).parse();
			for (int i=0; i < pois.size(); i++) {
				pois.get(i).setVisionGeoPoiClickListener(this);
			}
			if (update) {
				updateNearestPois(ctx);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loadingPois = false;
		}
	}
	

}
