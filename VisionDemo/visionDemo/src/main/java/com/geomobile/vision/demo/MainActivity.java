package com.geomobile.vision.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geomobile.arcore.VisionConfiguration;
import com.geomobile.arcore.VisionCore;
import com.geomobile.arcore.model.VisionCategory;
import com.geomobile.arcore.model.VisionGeoPoi;
import com.geomobile.arcore.model.VisionGeoPoiClickListener;
import com.geomobile.arcore.model.VisionImage;

public class MainActivity extends Activity implements OnClickListener {

	private ProgressBar progress;
	public Boolean custom = true;
	private DataSource data = DataSource.DEFAULT_FILES;
	private Boolean preload=true;
	private Boolean map = true;
	private Boolean list = true;
	private Boolean teleport = true;
	private Language lan=Language.ES;
	private Button bt_launch;
	private Button btn_mapas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progress=(ProgressBar)this.findViewById(R.id.progressBar);
		bt_launch = (Button) this.findViewById(R.id.launch);
		bt_launch.setOnClickListener(this);

		btn_mapas = (Button)this.findViewById(R.id.map);
		btn_mapas.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == bt_launch) {
			if (custom) {
				VisionCore vs = new VisionCore(this.getApplicationContext(), false);
				VisionCore.core.ar=new MyARManager(map,list,teleport);
				VisionCore.core.ar.setPrefixForImages("demo_");
				VisionCore.core.configuration=new VisionConfiguration();
				VisionCore.core.configuration.setRadarPosition(VisionConfiguration.RADAR_POSITION_RIGHT);
				VisionCore.core.configuration.showAppLogo(false);
				switch(lan){
					case EN:
						VisionCore.core.configuration.setLanguage("_en");
						break;
					case ES:
						VisionCore.core.configuration.setLanguage("_es");
						break;
				}
				new LoadCustomDataTask().execute();
			}else {
				VisionCore vs = new VisionCore(this.getApplicationContext(), true);
				VisionCore.core.ar.setPrefixForImages("");
				VisionCore.core.configuration.setRadarPosition(VisionConfiguration.RADAR_POSITION_LEFT);
				VisionCore.core.configuration.showAppLogo(true);
				switch(lan) {
					case EN:
						VisionCore.core.configuration.setLanguage("_en");
						break;
					case ES:
						VisionCore.core.configuration.setLanguage("_es");
						break;
				}
			}
		}else if (v== btn_mapas){
			Intent i = new Intent(this, Activity_Maps.class );
			startActivity(i);
		}
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		public LoadDataTask() {

		}

		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Void response) {
			VisionCore.startAR(MainActivity.this);
			progress.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			switch (data) {
				case DEFAULT_FILES:
					VisionCore.core.loadData(MainActivity.this);
					break;
				case DEMO_FILES:
					VisionCore.core.model.categoriesFrom = "file_categories";
					VisionCore.core.model.poisFrom = "file_pois";
					VisionCore.core.loadData(MainActivity.this);
					break;
				case WEB_FILES:
					VisionCore.core.model.categoriesFrom = "http://dl.dropbox.com/u/2251236/vision/file_categories";
					VisionCore.core.model.poisFrom = "https://dl.dropbox.com/u/2251236/vision/file_pois";
					VisionCore.core.loadData(MainActivity.this);
					break;
				case GENERATED_DATA:
					generateData();
					break;
			}
			return null;
		}
		private void generateData() {

//nueva modificacion
		}
	}// LoadDataTask

	private class LoadCustomDataTask extends AsyncTask<Void, Void, Void> {

		public LoadCustomDataTask() {

		}

		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Void response) {
			VisionCore.startAR(MainActivity.this);
			progress.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			if(preload){
				VisionCore.core.model=new MyModelManagerPreload();
				VisionCore.core.model.categoriesFrom = "my_custom_categories";
				VisionCore.core.model.poisFrom = "my_custom_pois";
				VisionCore.core.model.loadPoisContinouosly = false;
				VisionCore.core.loadData(MainActivity.this);
			}
			else{
				VisionCore.core.model=new MyModelManager();
				VisionCore.core.model.loadPoisContinouosly = true;
				VisionCore.core.loadData(MainActivity.this);
			}

			return null;
		}

	}// LoadCustomDataTask

}
