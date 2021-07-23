package com.example.mycards.jmdict;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//Below is based of Android Dev documentation:
// https://developer.android.com/training/basics/network-ops/xml?authuser=1
public class JMDictXMLParser {
    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readJMDict(parser);
        } finally {
            in.close();
        }
    }

    //Processes the feed
    //Looks for elements with the specified tag otherwise skips
    //Returns list of extracted data
    //Helper method skip(parser) should decrease amount of time needed to go through xml file
    //TODO - ...but the XML file is v large. Keep review.
    private List readJMDict(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "JMdict");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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


    //Inner class
    public static class Entry {
        //'Gloss' contains English definitions
        public final String gloss;
        //'Keb' contains kanji
        public final String keb;
        //'Reb' contains kana readings
        public final String reb;

        private Entry(String gloss, String keb, String reb) {
            this.gloss = gloss;
            this.keb = keb;
            this.reb = reb;
        }
    }

    // Parses the contents of an entry. If it encounters a gloss, keb, or link red, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String gloss = null;
        String keb = null;
        String reb = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("gloss")) {
                gloss = readGloss(parser);
            } else if (name.equals("keb")) {
                keb = readKeb(parser);
            } else if (name.equals("reb")) {
                reb = readReb(parser);
            } else {
                skip(parser);
            }
        }
        return new Entry(gloss, keb, reb);
    }

    // Processes title tags in the feed.
    private String readGloss(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "gloss");
        String gloss = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "gloss");
        return gloss;
    }

    // Processes summary tags in the feed.
    private String readKeb(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "keb");
        String keb = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "keb");
        return keb;
    }

    // Processes summary tags in the feed.
    private String readReb(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "reb");
        String reb = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "reb");
        return reb;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
