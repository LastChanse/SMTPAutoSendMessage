package com.example.smtpautosendmessage.Utils;

import java.nio.charset.StandardCharsets;

public class CharsetChangerUtil {
    public static String fromISOToUTF(String isoText) {
        return new String(isoText.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
    public static String fromUTFToISO(String utfText) {
        return new String(utfText.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
}
