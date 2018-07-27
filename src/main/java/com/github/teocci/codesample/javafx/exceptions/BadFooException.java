package com.github.teocci.codesample.javafx.exceptions;

import java.util.Random;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class BadFooException extends Exception
{
    private static final Random random = new Random();

    public BadFooException()
    {
        super(random.nextInt(100) + "");
    }
}
