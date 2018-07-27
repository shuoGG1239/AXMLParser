/*
 * Copyright 2008 Android4ME
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package util;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 封装了下AXMLPrinter
 * Created by shuoGG on 2018/7/27
 */
public class AXML2String {
    private StringBuilder result = new StringBuilder();

    public String parse(String filePath) throws IOException, XmlPullParserException {
        AXmlResourceParser parser = new AXmlResourceParser();
        parser.open(new FileInputStream(filePath));
        dealParser(parser);
        return result.toString();
    }

    public String parse(InputStream in) throws IOException, XmlPullParserException {
        AXmlResourceParser parser = new AXmlResourceParser();
        parser.open(in);
        dealParser(parser);
        return result.toString();
    }

    public String parse(byte[] in) throws IOException, XmlPullParserException {
        AXmlResourceParser parser = new AXmlResourceParser();
        parser.open(new ByteArrayInputStream(in));
        dealParser(parser);
        return result.toString();
    }

    private void dealParser(AXmlResourceParser parser) throws IOException, XmlPullParserException {
        StringBuilder indent = new StringBuilder(10);
        final String indentStep = "	";
        while (true) {
            int type = parser.next();
            if (type == XmlPullParser.END_DOCUMENT) {
                break;
            }
            switch (type) {
                case XmlPullParser.START_DOCUMENT: {
                    appendText("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    break;
                }
                case XmlPullParser.START_TAG: {
                    appendText("%s<%s%s", indent, getNamespacePrefix(parser.getPrefix()), parser.getName());
                    indent.append(indentStep);

                    int namespaceCountBefore = parser.getNamespaceCount(parser.getDepth() - 1);
                    int namespaceCount = parser.getNamespaceCount(parser.getDepth());
                    for (int i = namespaceCountBefore; i != namespaceCount; ++i) {
                        appendText("%sxmlns:%s=\"%s\"",
                                indent,
                                parser.getNamespacePrefix(i),
                                parser.getNamespaceUri(i));
                    }

                    for (int i = 0; i != parser.getAttributeCount(); ++i) {
                        appendText("%s%s%s=\"%s\"", indent,
                                getNamespacePrefix(parser.getAttributePrefix(i)),
                                parser.getAttributeName(i),
                                getAttributeValue(parser, i));
                    }
                    appendText("%s>", indent);
                    break;
                }
                case XmlPullParser.END_TAG: {
                    indent.setLength(indent.length() - indentStep.length());
                    appendText("%s</%s%s>", indent,
                            getNamespacePrefix(parser.getPrefix()),
                            parser.getName());
                    break;
                }
                case XmlPullParser.TEXT: {
                    appendText("%s%s", indent, parser.getText());
                    break;
                }
            }
        }
        System.out.println(result.toString());
    }

    private String getNamespacePrefix(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return "";
        }
        return prefix + ":";
    }

    private String getAttributeValue(AXmlResourceParser parser, int index) {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == TypedValue.TYPE_STRING) {
            return parser.getAttributeValue(index);
        }
        if (type == TypedValue.TYPE_ATTRIBUTE) {
            return String.format("?%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_REFERENCE) {
            return String.format("@%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_FLOAT) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == TypedValue.TYPE_INT_HEX) {
            return String.format("0x%08X", data);
        }
        if (type == TypedValue.TYPE_INT_BOOLEAN) {
            return data != 0 ? "true" : "false";
        }
        if (type == TypedValue.TYPE_DIMENSION) {
            return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type == TypedValue.TYPE_FRACTION) {
            return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return String.format("#%08X", data);
        }
        if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", data, type);
    }

    private String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    private void appendText(String format, Object... arguments) {
        String newStr = String.format(format, arguments);
        result.append(newStr);
        result.append("\n");
    }

    /////////////////////////////////// ILLEGAL STUFF, DONT LOOK :)

    private float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
    }

    private final float RADIX_MULTS[] = {
            0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F
    };
    private String DIMENSION_UNITS[] = {
            "px", "dip", "sp", "pt", "in", "mm", "", ""
    };
    private String FRACTION_UNITS[] = {
            "%", "%p", "", "", "", "", "", ""
    };
}