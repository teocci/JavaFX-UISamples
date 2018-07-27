package com.github.teocci.codesample.javafx.threads;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class FirstLineService extends Service<String>
{
    private StringProperty url = new SimpleStringProperty(this, "url");

    public final void setUrl(String value) { url.set(value); }

    public final String getUrl() { return url.get(); }

    public final StringProperty urlProperty() { return url; }

    protected Task createTask()
    {
        final String _url = getUrl();
        return new Task<String>()
        {
            protected String call() throws Exception
            {
                updateMessage("Called on thread: " + Thread.currentThread().getName());
                URL u = new URL(_url);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(u.openStream()));
                String result = in.readLine();
                in.close();

                // pause just so that it really takes some time to run the task
                // so that parallel execution behaviour can be observed.
                for (int i = 0; i < 100; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(50);
                }

                return result;
            }
        };
    }
}
