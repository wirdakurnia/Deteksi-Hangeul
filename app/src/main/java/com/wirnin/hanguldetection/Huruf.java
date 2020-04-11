package com.wirnin.hanguldetection;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Huruf {
    public String id, hangeul, huruf, lafal, suara;

    public Huruf() {
    }

    public Huruf(String id, String hangeul, String huruf, String lafal, String suara) {
        this.id = id;
        this.hangeul = hangeul;
        this.huruf = huruf;
        this.lafal = lafal;
        this.suara = suara;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("hangeul", hangeul);
        result.put("huruf", huruf);
        result.put("lafal", lafal);
        result.put("suara", suara);
        return result;
    }
}
