package com.github.teocci.codesample.javafx.tasks.foo;

import com.github.teocci.codesample.javafx.models.Foo;
import javafx.concurrent.Task;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FooModifierTask extends Task<Foo>
{
    private final Foo fooInput;

    public FooModifierTask(Foo fooInput)
    {
        this.fooInput = fooInput;
    }

    @Override
    protected Foo call() throws Exception
    {
        Thread.currentThread().sleep(1000);
        return new Foo(fooInput);
    }
}
