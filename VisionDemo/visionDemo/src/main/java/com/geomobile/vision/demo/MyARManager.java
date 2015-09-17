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

	private final int ACTIONBUTTON1 = 10;
	private final int OPTION1 = 11;
	private final int OPTION2 = 12;
	private final int OPTION3 = 13;
	private final int ACTIONCLOSE = 14;

	public Boolean map;
	public Boolean list;
	public Boolean teleport;

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

		VisionAction a1 = new VisionAction();
		a1.action = ACTIONBUTTON1;
		a1.title = "button1";
		a1.icon = null;
		topLeftAction = a1;

		VisionAction a2 = new VisionAction();
		a2.action = ACTION_SHOWOPTIONS;
		a2.title = "button2";
		a2.icon = null;
		bottomLeftAction = a2;
		
		VisionAction o1 = new VisionAction();
		o1.action = OPTION1;
		o1.title = "option1";
		o1.icon = null;
		a2.options.add(o1);
		
		VisionAction o2 = new VisionAction();
		o2.action = OPTION2;
		o2.title = "option2";
		o2.icon = null;
		a2.options.add(o2);
		
		VisionAction o3 = new VisionAction();
		o3.action = OPTION3;
		o3.title = "option3";
		o3.icon = null;
		a2.options.add(o3);
		
		VisionAction a3 = new VisionAction();
		a3.action = ACTIONCLOSE;
		a3.title = "close";
		a3.icon = null;
		topRightAction = a3;
		
		VisionAction a4 = new VisionAction();
		a4.action = VisionARManager.ACTION_SHOWCHANGEVIEW;
		a4.title = "close";
		a4.icon = null;
		bottomRightAction = a4;
		
		messageColor = Color.argb(187,0, 100, 0);
	}

	@Override
	protected boolean doAction(VisionAction action, int index, LinearLayout dropdown, boolean left, boolean top, int orientation, VisionCameraView camera, VisionARActivity activity) {

		switch (action.action) {
		case ACTIONBUTTON1:
			Toast.makeText(activity, "<--- "+VisionLanguage.getString("button1")+" --->", Toast.LENGTH_SHORT).show();
			return true;
		case OPTION1:
			Toast.makeText(activity, "<--- "+VisionLanguage.getString("option1")+" --->", Toast.LENGTH_SHORT).show();
			return true;
		case OPTION2:
			Toast.makeText(activity, "<--- "+VisionLanguage.getString("option2")+" --->", Toast.LENGTH_SHORT).show();
			return true;
		case OPTION3:
			Toast.makeText(activity, "<--- "+VisionLanguage.getString("option3")+" --->", Toast.LENGTH_SHORT).show();
			return true;
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
