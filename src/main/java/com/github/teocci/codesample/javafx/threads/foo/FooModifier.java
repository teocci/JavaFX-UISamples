package com.github.teocci.codesample.javafx.threads.foo;

import com.github.teocci.codesample.javafx.models.Foo;
import com.github.teocci.codesample.javafx.tasks.foo.FooModifierTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FooModifier extends Service<Foo>
{
    private Foo foo;

    public void setFoo(Foo foo)
    {
        this.foo = foo;
    }

    @Override
    protected Task createTask()
    {
        return new FooModifierTask(foo);
    }
}
