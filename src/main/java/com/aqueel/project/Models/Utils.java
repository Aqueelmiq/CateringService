package com.aqueel.project.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/26/16.
 */
public class Utils {

    public static ArrayList convertList(List list) {
        ArrayList returnVal = new ArrayList<>();
        list.forEach(item -> {
            returnVal.add(item);
        });
        return returnVal;
    }

}
