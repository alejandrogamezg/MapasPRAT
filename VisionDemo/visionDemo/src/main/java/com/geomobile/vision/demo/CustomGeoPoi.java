package com.geomobile.vision.demo;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geomobile.arcore.VisionCore;
import com.geomobile.arcore.model.VisionCategory;
import com.geomobile.arcore.model.VisionGeoPoi;
import com.geomobile.arcore.utils.VisionUtils;

public class CustomGeoPoi extends VisionGeoPoi {

	private String poiRating;

	@Override
	public LinearLayout getPanelTextureLayout(Context ctx) {
		// TODO Auto-generated method stub
		float density = VisionCore.core.configuration.getScreenDensity();

		//VISTA DE CARTELES FLOTANTES
		LinearLayout lay = new LinearLayout(ctx);
		lay.setOrientation(LinearLayout.HORIZONTAL);
		lay.setLayoutParams(new LinearLayout.LayoutParams(256, 128));
		lay.setPadding(10, 10, 10, 10);
		lay.setBackgroundDrawable(this.getPanelBackground(ctx));
		lay.layout(0, 0, 256, 128);



		//Atributos de titulo del cartel flotante
		TextView vista = new TextView(ctx);
		vista.setLayoutParams(new LinearLayout.LayoutParams(180, LayoutParams.WRAP_CONTENT));
		vista.setTextColor(0xffffffff);
		vista.setTextSize((int) (15 / density));
		vista.setText(this.title);
		vista.setGravity(Gravity.CENTER);
		vista.setMaxLines(5);
		vista.layout(2, 10, 250, 200);
		lay.addView(vista);

		LinearLayout top = new LinearLayout(ctx);
		top.setOrientation(LinearLayout.HORIZONTAL);
		top.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 56));
		top.layout(45, 60, 210, 155);
		lay.addView(top);


		vista = new TextView(ctx);
		vista.setText(VisionUtils.getDistanceToString(this.distance));
		vista.layout(0, 0, 105, 40);
		vista.setTextColor(0xffffffff);
		vista.setTextSize((int) (20 / density));
		vista.setGravity(Gravity.CENTER);
		top.addView(vista);


		if (categories != null && categories.size() > 0) {
			ImageView iv;
			iv = new ImageView(ctx);
			iv.setLayoutParams(new LayoutParams((int) (20 / density), (int) (20 / density)));
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.layout(0, 0, 20, 20);
			int j = 0;
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).getIcon() != null && categories.get(i).getIcon().getImage(null, true, false, true) != null) {
					iv = new ImageView(ctx);
					iv.setLayoutParams(new LayoutParams((int) (30 / density), (int) (30 / density)));
					iv.setScaleType(ScaleType.CENTER_INSIDE);
					iv.setImageDrawable(categories.get(i).getIcon().getImage(null, true, false, true));
					iv.layout(30 * (j + 1), 0, 35 * (j + 2) - 5, 30);
					j++;
				}
			}
		}// if
		return lay;
	}

	@Override

	public LinearLayout getArrowTextureLayout(Context ctx) {
		// TODO Auto-generated method stub
		float density = VisionCore.core.configuration.getScreenDensity();

		//Vista de la flecha la cual contiene las dimenciones de ella
		LinearLayout lay = new LinearLayout(ctx);
		lay.setOrientation(LinearLayout.VERTICAL);
		lay.setLayoutParams(new LinearLayout.LayoutParams(256, 128));
		lay.setPadding(16, 16, 16, 8);
		lay.setBackgroundDrawable(this.getArrowBackground(ctx));
		lay.layout(0, 0, 256, 128);

		LinearLayout top = new LinearLayout(ctx);
		top.setOrientation(LinearLayout.HORIZONTAL);
		top.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 40));
		top.layout(20, 20, 300, 100);
		lay.addView(top);

		ImageView iv = new ImageView(ctx);
		iv.setLayoutParams(new LayoutParams(25, 25));
		iv.setScaleType(ScaleType.CENTER_CROP);
		iv.setImageDrawable(ctx.getResources().getDrawable(R.drawable.logo_h));
		top.addView(iv);
		iv.layout(0, 0, 35, 35);

		TextView vista = new TextView(ctx);
		vista.setLayoutParams(new LinearLayout.LayoutParams(180, LayoutParams.WRAP_CONTENT));
		vista.setTextColor(0xffffffff);
		vista.setTextSize((int) (13 / density));
		vista.setGravity(Gravity.CENTER);
		vista.setMaxLines(2);
		vista.setText(this.title);
		vista.layout(32, 57, 170, 120);
		lay.addView(vista);

		vista = new TextView(ctx);
		vista.setText(VisionUtils.getDistanceToString(this.distance));
		vista.layout(100, 0, 240, 40);
		vista.setTextColor(0xffffffff);
		vista.setTextSize((int) (15 / density));
		top.addView(vista);

		return lay;
	}

	/*-----------------------------------------------------------*/
	@Override
	public View drawInfoPanel(Context ctx) {
		RelativeLayout ll = new RelativeLayout(ctx);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(p);
		ll.setGravity(Gravity.CENTER);
		ll.setPadding(VisionUtils.scalePixels(50), 0, VisionUtils.scalePixels(50), VisionUtils.scalePixels(40));
		ll.setBackgroundDrawable(this.getInfoPanelBackground(ctx));

		TextView rating = new TextView(ctx);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
		params.setMargins(VisionUtils.scalePixels(5), 0, 0, 0);
		rating.setLayoutParams(params);
		rating.setTextColor(0xffffffff);
		rating.setTypeface(Typeface.DEFAULT_BOLD);
		rating.setText(poiRating);
		ll.addView(rating);

		LinearLayout ll2 = new LinearLayout(ctx);
		params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll2.setLayoutParams(params);
		ll2.setOrientation(LinearLayout.VERTICAL);
		ll2.setGravity(Gravity.CENTER);
		ll.addView(ll2);

		TextView distance = new TextView(ctx);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		distance.setLayoutParams(params2);
		distance.setTextColor(0xffffffff);
		distance.setTextSize(15);
		distance.setTypeface(Typeface.MONOSPACE);
		distance.setText(VisionUtils.getDistanceToString(this.distance));
		ll2.addView(distance);

		TextView title = new TextView(ctx);
		params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		title.setLayoutParams(params2);
		title.setTextColor(0xff000000);
		title.setTextSize(15);
		title.setTypeface(Typeface.DEFAULT_BOLD);
		title.setSingleLine(true);
		title.setEllipsize(TruncateAt.END);
		title.setText(Html.fromHtml(this.title));
		ll2.addView(title);

		ImageView miniImage = new ImageView(ctx);
		params = new RelativeLayout.LayoutParams(VisionUtils.scalePixels(1), VisionUtils.scalePixels(1));
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		params.setMargins(0, 0, VisionUtils.scalePixels(5), 0);
		miniImage.setLayoutParams(params);
		miniImage.setScaleType(ScaleType.CENTER_INSIDE);
		miniImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.logo_g));
		ll.addView(miniImage);

		return ll;

	}
	//MENSAJE POP DEL MAPA
	@Override
	public View drawMapPopup(Context ctx) {
		LinearLayout pop =(LinearLayout) View.inflate(ctx,R.layout.custom_info_window, null);
		TextView title=(TextView)pop.findViewById(R.id.title);
		title.setText(this.getTitle());
		title.setTextColor(0xffffffff);
		TextView subtitle=(TextView)pop.findViewById(R.id.subtitle);
		subtitle.setText(this.getSubtitle());
		ImageView cat=(ImageView)pop.findViewById(R.id.cat);
		Vector<VisionCategory> cats = this.getCategories();
		if (cats != null && cats.size() > 0) {
			int i = 0;
			Drawable im = null;
			while (i < cats.size() && im == null) {
				if (cats.get(i).getIcon() != null) {
					im = cats.get(i).getIcon().getImage(ctx, true, false, true);
				}
				i++;
			}
			cat.setImageDrawable(im);
		}// if
		return pop;
	}

	@Override
	public View drawListItem(Context ctx, View ll) {
		ll = new LinearLayout(ctx);
		ListView.LayoutParams listparams = new ListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(listparams);
		ll.setPadding(VisionUtils.scalePixels(3), VisionUtils.scalePixels(3), VisionUtils.scalePixels(3), VisionUtils.scalePixels(3));
		ll.setBackgroundColor(VisionCore.core.ar.backgroundColor);

		LinearLayout lay = new LinearLayout(ctx);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lay.setLayoutParams(params);
		lay.setOrientation(LinearLayout.HORIZONTAL);
		lay.setPadding(VisionUtils.scalePixels(10), 0, VisionUtils.scalePixels(30), 0);
		lay.setGravity(Gravity.CENTER_VERTICAL);
		((LinearLayout) ll).addView(lay);

		ImageView remove = new ImageView(ctx);
		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, VisionUtils.scalePixels(5), 0);
		remove.setLayoutParams(params);
		remove.setScaleType(ScaleType.FIT_CENTER);
		remove.setImageResource(VisionUtils.getResourceIdentifier("vision_icon_remove", "drawable"));
		remove.setVisibility(View.GONE);
		lay.addView(remove);

		ImageView miniImage = new ImageView(ctx);
		params = new LinearLayout.LayoutParams(VisionUtils.scalePixels(25), VisionUtils.scalePixels(25));
		miniImage.setLayoutParams(params);
		miniImage.setScaleType(ScaleType.FIT_CENTER);
		lay.addView(miniImage);

		LinearLayout lay2 = new LinearLayout(ctx);
		params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		lay2.setLayoutParams(params);
		lay2.setOrientation(LinearLayout.VERTICAL);
		lay2.setGravity(Gravity.CENTER_VERTICAL);
		lay2.setPadding(VisionUtils.scalePixels(10), 0, 0, 0);
		lay.addView(lay2);
		
		LinearLayout lay3 = new LinearLayout(ctx);
		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, VisionUtils.scalePixels(5), 0);
		lay3.setLayoutParams(params);
		lay3.setOrientation(LinearLayout.VERTICAL);
		lay3.setGravity(Gravity.CENTER);
		lay3.setPadding(VisionUtils.scalePixels(10), 0, 0, 0);
		lay.addView(lay3);

		TextView title = new TextView(ctx);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		title.setLayoutParams(params);
		title.setTextColor(0xff000000);
		title.setTextSize(15);
		title.setSingleLine(true);
		title.setEllipsize(TruncateAt.END);
		lay2.addView(title);

		TextView text = new TextView(ctx);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		text.setLayoutParams(params);
		text.setTextColor(0xff333333);
		text.setTextSize(12);
		text.setSingleLine(true);
		text.setEllipsize(TruncateAt.END);
		lay2.addView(text);

		TextView distance = new TextView(ctx);
		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		distance.setLayoutParams(params);
		distance.setTextColor(0xff333333);
		lay3.addView(distance);
		
		ImageView catImage = new ImageView(ctx);
		params = new LinearLayout.LayoutParams(VisionUtils.scalePixels(15), VisionUtils.scalePixels(15));
		catImage.setLayoutParams(params);
		catImage.setScaleType(ScaleType.FIT_CENTER);
		lay3.addView(catImage);

		// set data
		lay.setBackgroundDrawable(this.getListItemBackground(ctx));

		miniImage.setImageResource(R.drawable.logo_g);
		
		title.setText(Html.fromHtml(this.title));
		title.setTextColor(0xff000000);

		text.setText(Html.fromHtml(this.subtitle));
		text.setTextColor(0xff000000);

		distance.setText(VisionUtils.getDistanceToString(this.distance));
		distance.setTextColor(0xff000000);

		
		Vector<VisionCategory> cats = this.getCategories();
		if (cats != null && cats.size() > 0) {
			int i = 0;
			Drawable im = null;
			while (i < cats.size() && im == null) {
				if (cats.get(i).getIcon() != null) {
					im = cats.get(i).getIcon().getImage(ctx, true, false, true);
				}
				i++;
			}
			catImage.setImageDrawable(im);
		}// if

		return ll;

	}
	
	@Override
	public void onClick(Activity activity) {
		// TODO Auto-generated method stub
		super.onClick(activity);
		final Intent intent = new Intent(activity, CustomGeoPoiClickActivity.class);
		activity.startActivity(intent);
	}

	public String getPoiRating() {
		return poiRating;
	}

	public void setPoiRating(String poiRating) {
		this.poiRating = poiRating;
	}

}
