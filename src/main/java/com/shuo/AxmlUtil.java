package com.shuo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shuo.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * fork from: https://github.com/rednaga/axmlprinter
 * <p>
 * 对加密的AndroidManifest.xml进行解密
 * Created by shuoGG on 2018/7/27
 */
public class AxmlUtil {

    private static final Log logger = LogFactory.getLog(AxmlUtil.class);

    public static String parse(String fileUrl) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(fileUrl);
        } catch (IOException | XmlPullParserException e) {
            logger.error(e);
        }
        return null;
    }

    public static String parse(InputStream in) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(in);
        } catch (IOException | XmlPullParserException e) {
            logger.error(e);
        }
        return null;
    }

    public static String parse(byte[] in) {
        AXML2String axml = new AXML2String();
        try {
            return axml.parse(in);
        } catch (IOException | XmlPullParserException e) {
            logger.error(e);
        }
        return null;
    }
}
