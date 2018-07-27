package com.github.teocci.codesample.javafx.threads.foo;

import com.github.teocci.codesample.javafx.exceptions.BadFooException;
import com.github.teocci.codesample.javafx.models.Foo;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class BadFooGenerator extends Service<Foo>
{
    @Override
    protected Task createTask()
    {
        return new Task<Foo>()
        {
            @Override
            protected Foo call() throws Exception
            {
                Thread.currentThread().sleep(1000);
                throw new BadFooException();
            }
        };
    }
}
