package com.example.truthordare.utils;

import android.view.animation.Interpolator;

public class CustomBounceInterpolator implements Interpolator {
    private double mAmplitude = 1.0d;
    private double mFrequency = 10.0d;

    public CustomBounceInterpolator(double amplitude, double frequency) {
        this.mAmplitude = amplitude;
        this.mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (((-1.0d * Math.pow(2.718281828459045d, ((double) (-time)) / this.mAmplitude)) * Math.cos(this.mFrequency * ((double) time))) + 1.0d);
    }
}
