package com.example.biskit.entities;

import com.example.biskit.util.NoNormalizar;
import com.example.biskit.util.Normalizador;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@MappedSuperclass
public abstract class EntidadBase {

    @PrePersist
    @PreUpdate
    protected void normalizeAllStrings() {
        Class<?> cls = this.getClass();
        while (cls != null && cls != Object.class) {
            for (Field f : cls.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())) continue;
                if (!String.class.equals(f.getType())) continue;
                if (f.getAnnotation(NoNormalizar.class) != null) continue;
                try {
                    boolean accessible = f.canAccess(this);
                    f.setAccessible(true);
                    String current = (String) f.get(this);
                    String normalized = Normalizador.normalizar(current);
                    if (normalized != current) {
                        f.set(this, normalized);
                    }
                    f.setAccessible(accessible);
                } catch (IllegalAccessException ignored) {
                }
            }
            cls = cls.getSuperclass();
        }
    }
}