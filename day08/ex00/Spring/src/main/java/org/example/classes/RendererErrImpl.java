package org.example.classes;

import org.example.interfaces.PreProcessor;
import org.example.interfaces.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String str) {
        System.err.println(preProcessor.processor(str));
    }
}
