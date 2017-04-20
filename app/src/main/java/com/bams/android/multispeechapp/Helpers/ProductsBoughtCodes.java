package com.bams.android.multispeechapp.Helpers;

/**
 * Created by bams on 4/16/17.
 */

public final class ProductsBoughtCodes {

    public static String[] codes = {"agregada","agregado","lista","listo","ya compre", "ya compré", "adquirido"};

    public static boolean haveCodes(String data) {
        boolean ContainsWors = false;
        for (String code : codes) {
            if (data.contains(code)) {
                ContainsWors = true;
                break;
            }
        }
        return ContainsWors;
    }

    public static String replaceCodes(String data, int index) {
        return index == codes.length - 1 ? data :
                replaceCodes(data.replace(codes[index], ""), ++index);
    }

}
