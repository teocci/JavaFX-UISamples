package com.github.teocci.codesample.javafx.handlers;

import com.github.teocci.codesample.javafx.transitions.MoveXTransition;
import com.github.teocci.codesample.javafx.transitions.MoveYTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Animates an object when its position is changed. For instance, when
 * additional items are added to a Region, and the layout has changed, then the
 * layout animator makes the transition by sliding each item into its final
 * place.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class LayoutAnimator implements ChangeListener<Number>, ListChangeListener<Node>
{
    private Map<Node, MoveXTransition> nodeXTransitions = new HashMap<>();
    private Map<Node, MoveYTransition> nodeYTransitions = new HashMap<>();

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue)
    {
        final double delta = newValue.doubleValue() - oldValue.doubleValue();
        final DoubleProperty doubleProperty = (DoubleProperty) ov;
        final Node node = (Node) doubleProperty.getBean();

        switch (doubleProperty.getName()) {
            case "layoutX":
                MoveXTransition tx = nodeXTransitions.get(node);
                if (tx == null) {
                    tx = new MoveXTransition(node);
                    nodeXTransitions.put(node, tx);
                }
                tx.setFromX(tx.getTranslateX() - delta);

                tx.playFromStart();
                break;

            default: // "layoutY"
                MoveYTransition ty = nodeYTransitions.get(node);
                if (ty == null) {
                    ty = new MoveYTransition(node);
                    nodeYTransitions.put(node, ty);
                }
                ty.setFromY(ty.getTranslateY() - delta);

                ty.playFromStart();
        }
    }

    @Override
    public void onChanged(Change change)
    {
        while (change.next()) {
            if (change.wasAdded()) {
                ((List<Node>) change.getAddedSubList()).forEach(this::observe);
            } else if (change.wasRemoved()) {
                ((List<Node>) change.getRemoved()).forEach(this::unobserve);
            }
        }
    }

    /**
     * Animates all the children of a Region.
     * <code>
     * VBox myVbox = new VBox();
     * LayoutAnimator animator = new LayoutAnimator();
     * animator.observe(myVbox.getChildren());
     * </code>
     *
     * @param nodes
     */
    public void observe(ObservableList<Node> nodes)
    {
        for (Node node : nodes) {
            this.observe(node);
        }
        nodes.addListener(this);
    }

    public void unobserve(ObservableList<Node> nodes)
    {
        nodes.removeListener(this);
    }

    public void observe(Node n)
    {
        n.layoutXProperty().addListener(this);
        n.layoutYProperty().addListener(this);
    }

    public void unobserve(Node n)
    {
        n.layoutXProperty().removeListener(this);
        n.layoutYProperty().removeListener(this);
    }

    // TODO: unobserving nodes should cleanup any intermediate transitions they may have and ensure they
    // are removed from transition cache to prevent memory leaks.
}