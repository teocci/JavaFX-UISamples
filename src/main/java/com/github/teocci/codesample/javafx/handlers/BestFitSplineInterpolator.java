package com.github.teocci.codesample.javafx.handlers;

import javafx.animation.Interpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class BestFitSplineInterpolator extends Interpolator
{
    private final PolynomialSplineFunction f;

    public BestFitSplineInterpolator(double[] x, double[] y)
    {
        f = new SplineInterpolator().interpolate(x, y);
    }

    @Override
    protected double curve(double t)
    {
        return f.value(t);
    }
}
