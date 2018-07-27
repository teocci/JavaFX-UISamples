package com.github.teocci.codesample.javafx.threads;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-27
 */
public class AddNodeLater implements Runnable
{
    final Pane container;
    final Node node;
    final int idx;

    public AddNodeLater(final Pane container, final int idx, final Node node)
    {
        this.container = container;
        this.node = node;
        this.idx = idx;
    }

    @Override
    public void run()
    {
        container.getChildren().add(idx, node);
    }
}
