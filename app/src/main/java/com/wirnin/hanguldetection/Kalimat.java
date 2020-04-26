package com.wirnin.hanguldetection;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Kalimat {
    public String id, arti, formal, lafalformal, informal, lafalinformal, suaraformal, suarainformal;

    public Kalimat(){

    }

    public Kalimat(String id, String arti, String formal, String lafalformal, String informal, String lafalinformal, String suaraformal, String suarainformal) {
        this.id = id;
        this.arti = arti;
        this.formal = formal;
        this.lafalformal = lafalformal;
        this.informal = informal;
        this.lafalinformal = lafalinformal;
        this.suaraformal = suaraformal;
        this.suarainformal = suarainformal;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("arti", arti);
        result.put("formal", formal);
        result.put("lafalformal", lafalformal);
        result.put("informal", informal);
        result.put("lafalinformal", lafalinformal);
        result.put("suaraformal", suaraformal);
        result.put("suarainformal", suarainformal);
        return result;
    }

}
