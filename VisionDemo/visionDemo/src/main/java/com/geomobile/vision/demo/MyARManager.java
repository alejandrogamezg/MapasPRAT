package com.geomobile.vision.demo;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.geomobile.arcore.ar.VisionARActivity;
import com.geomobile.arcore.ar.VisionARManager;
import com.geomobile.arcore.ar.VisionCameraView;
import com.geomobile.arcore.model.VisionAction;
import com.geomobile.arcore.model.VisionImage;
import com.geomobile.arcore.utils.VisionLanguage;

public class MyARManager extends VisionARManager {


	//VARIABLES DE INICIALIZACION DE LOS BOTONES DE LA VISTA PRINCIPAL

	private final int ACTIONCLOSE = 14;
	public Boolean map;
	public Boolean list;
	public Boolean teleport;

	//METODO QUE CONTIENE LOS ATRIBUTOS DE MAPAS, LISTA Y TELETRASPORTACION
	public MyARManager(Boolean map, Boolean list, Boolean teleport) {
		super();
		this.map = map;
		this.list = list;
		this.teleport = teleport;
		if (map && list) {
			int[] vertical = { FLOATING_PANEL, FLOATING_PANEL, MAP, POI_LIST };
			int[] horizontal = { FLOATING_ARROW, RADAR, MAP, POI_LIST };
			verticalViews = vertical;
			horizontalViews = horizontal;
			viewsNum = 4;
		} else if (map) {
			int[] vertical = { FLOATING_PANEL, FLOATING_PANEL, MAP };
			int[] horizontal = { FLOATING_ARROW, RADAR, MAP };
			verticalViews = vertical;
			horizontalViews = horizontal;
			viewsNum = 3;
		} else if (list) {
			int[] vertical = { FLOATING_PANEL, FLOATING_PANEL, POI_LIST };
			int[] horizontal = { FLOATING_ARROW, RADAR, POI_LIST };
			verticalViews = vertical;
			horizontalViews = horizontal;
			viewsNum = 3;
		} else {
			int[] vertical = { FLOATING_PANEL, FLOATING_PANEL };
			int[] horizontal = { FLOATING_ARROW, RADAR };
			verticalViews = vertical;
			horizontalViews = horizontal;
			viewsNum = 2;
		}


		//BOTON DE CERRAR LA VISTA
		VisionAction a3 = new VisionAction();
		a3.action = ACTIONCLOSE;
		a3.title = "close";
		a3.icon = null;
		topRightAction = a3;

		
		messageColor = Color.argb(187,0, 100, 0);
	}

	@Override
	protected boolean doAction(VisionAction action, int index, LinearLayout dropdown, boolean left, boolean top, int orientation, VisionCameraView camera, VisionARActivity activity) {

		switch (action.action) {
		case ACTIONCLOSE:
			activity.finish();
			return true;
		default:
			return super.doAction(action, index, dropdown, left, top, orientation, camera, activity);
		}

	}// doAction

	@Override
	public boolean canBeTeleported() {
		// TODO Auto-generated method stub
		return teleport;
	}
}
