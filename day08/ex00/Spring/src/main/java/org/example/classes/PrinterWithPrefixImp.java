package org.example.classes;

import org.example.interfaces.Printer;
import org.example.interfaces.Renderer;

public class PrinterWithPrefixImp implements Printer {
    private final Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImp(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String s) {
        if (!prefix.isEmpty()){
            renderer.render(prefix + s);
        }
        else
            renderer.render(s);
    }
}
