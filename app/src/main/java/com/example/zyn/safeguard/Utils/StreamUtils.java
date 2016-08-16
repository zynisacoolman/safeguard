package com.example.zyn.safeguard.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zyn on 2016/6/30.
 */
public class StreamUtils {
    public static String readFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1000];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        String result = out.toString();
        in.close();
        out.close();
        return result;
    }
}
