package com.oop.socials.lib;

import java.util.HashMap;

public class Errors {
    public static HashMap<String, String> error(String err) {
        HashMap<String, String> mpp = new HashMap<>();
        mpp.put("Error", err);
        return mpp;
    }
}
