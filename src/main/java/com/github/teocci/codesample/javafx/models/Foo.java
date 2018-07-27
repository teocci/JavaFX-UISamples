package com.github.teocci.codesample.javafx.models;

import java.util.Random;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class Foo
{
    private final Random random = new Random();

    private final int answer;

    public Foo()
    {
        answer = random.nextInt(100);
    }

    public Foo(Foo input)
    {
        answer = input.getAnswer() + 42;
    }

    public int getAnswer()
    {
        return answer;
    }
}
