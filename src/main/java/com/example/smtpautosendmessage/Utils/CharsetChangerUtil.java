package com.example.smtpautosendmessage.Utils;

import java.nio.charset.StandardCharsets;

public class CharsetChangerUtil {
    public static String fromISOToUTF(String isoText) {
        if (isoText == null) {return "";}
        return new String(isoText.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
    public static String fromUTFToISO(String utfText) {
        if (utfText == null) {return "";}
        return new String(utfText.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
    public static String toUTF(String text) {
        if (text == null) {return "";}
        String str = text.split(" ")[0];
        str = str.replace("\\","");
        String[] arr = str.split("u");
        String text2 = "";
        for(int i = 1; i < arr.length; i++){
            int hexVal = Integer.parseInt(arr[i], 16);
            text2 += (char)hexVal;
        }
        return text2;
    }
}
