package com.tickets.models.routes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.tickets.models.routes.Place;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PlacesLoader {
    public static ArrayList<Place> readData(Context context) {
        ArrayList<Place> places = new ArrayList<>();

        try {
            try (InputStream file = context.getAssets().open("places_data.xml")) {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(file, null);
                parser.nextTag();

                parser.require(XmlPullParser.START_TAG, null, "places");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String tag = parser.getName();

                    if (!tag.equals("place")) {
                        skip(parser);
                        continue;
                    }

                    parser.require(XmlPullParser.START_TAG, null, "place");

                    String name = null;
                    String source = null;
                    String code = null;

                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }

                        tag = parser.getName();

                        if (tag.equals("name")) {
                            parser.require(XmlPullParser.START_TAG, null, "name");
                            name = readText(parser);
                            parser.require(XmlPullParser.END_TAG, null, "name");
                        } else if (tag.equals("source")) {
                            parser.require(XmlPullParser.START_TAG, null, "source");
                            source = readText(parser);
                            parser.require(XmlPullParser.END_TAG, null, "source");
                        } else if (tag.equals("code")) {
                            parser.require(XmlPullParser.START_TAG, null, "code");
                            code = readText(parser);
                            parser.require(XmlPullParser.END_TAG, null, "code");
                        } else {
                            skip(parser);
                        }
                    }

                    places.add(new Place(name, source, code));
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        
        return places;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}