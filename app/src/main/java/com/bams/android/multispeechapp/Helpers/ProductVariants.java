package com.bams.android.multispeechapp.Helpers;

import java.util.Arrays;

/**
 * Created by bams on 4/17/17.
 */

public class ProductVariants {

    public static String[] lettlerNumbers =
            {"un","uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez"};
    public static String[] pureNumbers = {"1","1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public static String parseNumberToLetter(String data, int index) {
        return index == lettlerNumbers.length - 1 ? data :
                parseNumberToLetter(data.replace(pureNumbers[index], lettlerNumbers[index]),
                        ++index);
    }

}
