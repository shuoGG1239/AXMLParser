package util;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * fork from: https://github.com/rednaga/axmlprinter
 * <p>
 * 对加密的AndroidManifest.xml进行解密
 * Created by shuoGG on 2018/7/27
 */
public class AxmlUtil {
    public static String parse(String fileUrl) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(fileUrl);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parse(InputStream in) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(in);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parse(byte[] in) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(in);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}
