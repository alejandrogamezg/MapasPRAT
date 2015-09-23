package com.geomobile.vision.demo;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.geomobile.arcore.VisionCore;
import com.geomobile.arcore.model.VisionAudio;
import com.geomobile.arcore.model.VisionCategory;
import com.geomobile.arcore.model.VisionImage;
import com.geomobile.arcore.model.VisionModelManager;
import com.geomobile.arcore.source.VisionGeoPoiParser;
import com.geomobile.arcore.utils.VisionUtils;

public class CustomGeoPoiParser extends VisionGeoPoiParser {

	public CustomGeoPoiParser(Context ctx, String f) {
		super(ctx, f);
	}

	public CustomGeoPoiParser(Context ctx, String f, List<VisionCategory> categories) {
		super(ctx, f, categories);
	}

	@Override
	public void parse() {

		try {
			// Get document root
			InputStream in;
			if (from.startsWith("http://")) {
				in = VisionUtils.getInputFromURL(from);
			} else {
				in = VisionUtils.getInputFromRawFile(from, context);
			}

			//Get a SAXParser from the SAXPArserFactory.
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			//Get the XMLReader of the SAXParser we created.
			XMLReader xr = sp.getXMLReader();
			// Create a new ContentHandler and apply it to the XML-Reader
			CustomGeoPoiHandler myExampleHandler = new CustomGeoPoiHandler();
			xr.setContentHandler(myExampleHandler);

			// Parse the xml-data from our URL.
			xr.parse(new InputSource(in));
			// Parsing has finished.
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class CustomGeoPoiHandler extends VisionGeoPoiHandler {
		CustomGeoPoi p;

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}

		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
			if (localName.equals(POI)) {
				p = new CustomGeoPoi();

				try {
					p.setId(getStringAttribute(atts, "poiIDStr"));
					p.setLatitude(Double.parseDouble(getStringAttribute(atts, "poiLatitudeDouble")));
					p.setLongitude(Double.parseDouble(getStringAttribute(atts, "poiLongitudeDouble")));
					p.setTitle(getStringAttribute(atts, "poiTitleStr"));
					p.setSubtitle(getStringAttribute(atts, "poiSubtitleStr"));
					p.setText(getStringAttribute(atts, "poiTextStr"));
					p.setWeb(getStringAttribute(atts, "poiWebStr"));
					p.setEmail(getStringAttribute(atts, "poiEmailStr"));
					p.setPhone(getStringAttribute(atts, "poiPhoneStr"));
					p.setVideo(getStringAttribute(atts, "poiVideoStr"));
					//User new values
					p.setPoiRating(getStringAttribute(atts, "poiRating"));
				} catch (Exception e) {
					e.printStackTrace();
					p = null;
				}
			} else if (localName.equals(CATEGORY)) {
				try {
					if (p != null) {
						String catId = getStringAttribute(atts, "poiCategoryIDStr");
						VisionCategory cat = VisionModelManager.searchCategoryById(myCategories, catId, true);
						if (cat != null) {
							p.getCategories().add(cat);
							cat.getPois().add(p);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (localName.equals(ICON)) {
				try {
					if (p == null) { // general icons
						VisionImage icon = new VisionImage();
						icon.setId(getStringAttribute(atts, "poiIconIDStr"));
						if (VisionModelManager.searchImageById(VisionCore.core.model.getIcons(), icon.getId()) == null) { // unknown icon
							String url = getStringAttribute(atts, "poiIconStr");
							icon.setImageURL(getImageUrl(url));
							VisionCore.core.model.getIcons().add(icon);
						}
					} else { // poi icon
						String iconId = getStringAttribute(atts, "poiIconIDStr");
						VisionImage icon = VisionModelManager.searchImageById(VisionCore.core.model.getIcons(), iconId);
						if (icon != null) {
							p.getIcons().add(icon);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (localName.equals(AUDIO)) {
				try {
					if (p != null) {
						VisionAudio audio = new VisionAudio();
						audio.setAudioURL(getStringAttribute(atts, "poiAudioStr"));
						audio.setText(getStringAttribute(atts, "poiAudioTextStr"));
						audio.setTitle(getStringAttribute(atts, "poiAudioTitleStr"));
						p.getAudios().add(audio);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (localName.equals(IMAGE)) {
				try {
					if (p != null) {
						VisionImage draw = new VisionImage();
						String url = getStringAttribute(atts, "poiImageStr");
						draw.setImageURL(getImageUrl(url));
						p.getImages().add(draw);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// else
		} // startElement

		@Override
		public void characters(char ch[], int start, int length) {
			;
		}

		@Override
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
	        if (localName.equals("poi")) {
	        	if( p != null){
	       			pois.add(p);
				}
	        }
		}// endElement

	}// VisionGeoPoiHandler

}