package com.github.teocci.codesample.javafx.tasks;

import com.github.teocci.codesample.javafx.elements.ShapeMachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.shape.Shape;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class ParallelDimension extends Service<ObservableList<Shape>>
{
    private final ShapeMachine machine;
    private final int nShapes;

    public ParallelDimension(ShapeMachine machine, int nShapes)
    {
        this.machine = machine;
        this.nShapes = nShapes;
    }

    @Override
    protected Task<ObservableList<Shape>> createTask()
    {
        return new Task<ObservableList<Shape>>()
        {
            @Override
            protected ObservableList<Shape> call() throws Exception
            {
                ObservableList<Shape> shapes = FXCollections.observableArrayList();
                for (int i = 0; i < nShapes; i++) {
                    shapes.add(machine.randomShape());
                }

                return shapes;
            }
        };
    }
}
