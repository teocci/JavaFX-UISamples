package com.github.teocci.codesample.javafx.utils;

import java.util.function.Consumer;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FXUtilHelper
{
    public static <T> T build(T node, Consumer<T> initializer)
    {
        initializer.accept(node);
        return node;
    }
}
