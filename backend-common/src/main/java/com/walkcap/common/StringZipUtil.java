package com.walkcap.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

/**
 * 字符串压缩/解压
 * 
 * @author shoen
 *
 */
public class StringZipUtil {

	/**
	 * 
	 * @param encdata
	 * @return
	 */
    public static String decompressData(String encdata) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InflaterOutputStream zos = new InflaterOutputStream(bos);
            zos.write(convertFromBase64(encdata));
            zos.close();
            return new String(bos.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param data
     * @return
     */
    public static String compressData(String data) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);
            zos.write(data.getBytes());
            zos.close();
            //return null;
            return convertToBase64(bos.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] convertFromBase64(String encdata) {
        byte[] compressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(encdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressed;
    }

    private static String convertToBase64(byte[] byteArray) {
        return new sun.misc.BASE64Encoder().encode(byteArray);
    }
}

