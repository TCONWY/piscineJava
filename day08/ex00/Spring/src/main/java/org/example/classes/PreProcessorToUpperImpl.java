package org.example.classes;

import org.example.interfaces.PreProcessor;

import java.util.Locale;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String processor(String str) {
        return str.toUpperCase(Locale.ROOT);
    }
}
