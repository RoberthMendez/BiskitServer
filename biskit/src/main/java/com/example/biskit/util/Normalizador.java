package com.example.biskit.util;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Normalizador {

  private Normalizador() {}

    public static String normalizar(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return s;
        // Normaliza cada palabra: "JOHN DOE" -> "John Doe"
        return Stream.of(s.split("\\s+"))
                .map(Normalizador::normalizarPalabra)
                .collect(Collectors.joining(" "));
    }

    private static String normalizarPalabra(String w) {
        if (w == null || w.isEmpty()) return w;
        w = w.toLowerCase(Locale.ROOT);
        return Character.toUpperCase(w.charAt(0)) + w.substring(1);
    }


  
}
