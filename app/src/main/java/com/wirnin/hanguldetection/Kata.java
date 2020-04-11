package com.wirnin.hanguldetection;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Kata {
    public String id, arti, hangeul, gambar, lafal, suara;

    public Kata() {
    }

    public Kata(String id, String arti, String hangeul, String gambar, String lafal, String suara) {
        this.id = id;
        this.arti = arti;
        this.hangeul = hangeul;
        this.gambar = gambar;
        this.lafal = lafal;
        this.suara = suara;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("arti", arti);
        result.put("hangeul", hangeul);
        result.put("gambar", gambar);
        result.put("lafal", lafal);
        result.put("suara", suara);
        return result;
    }
}
