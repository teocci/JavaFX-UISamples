package com.github.teocci.codesample.javafx.managers;

import com.github.teocci.codesample.javafx.enums.Role;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.*;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class RoleManager
{
    private final Map<Node, List<Role>> nodeRoles = new HashMap<>();
    private ObservableList<Role> activeRoles;

    public final ListChangeListener<Role> ACTIVE_ROLE_LISTENER = c -> showActiveNodes();

    public void setActiveRoles(ObservableList<Role> activeRoles)
    {
        if (this.activeRoles != null) {
            this.activeRoles.removeListener(ACTIVE_ROLE_LISTENER);
        }
        this.activeRoles = activeRoles;
        this.activeRoles.addListener(ACTIVE_ROLE_LISTENER);
    }

    public void showActiveNodes()
    {
        for (Node node : nodeRoles.keySet()) {
            node.setVisible(isActive(node));
        }
    }

    public void assignRole(Node node, Role... roles)
    {
        if (roles.length == 0) {
            nodeRoles.remove(node);
            return;
        }

        nodeRoles.put(node, Arrays.asList(roles));
    }

    private boolean isActive(Node node)
    {
        if (activeRoles == null) {
            return false;
        }

        for (Role role : nodeRoles.get(node)) {
            if (activeRoles.contains(role)) {
                return true;
            }
        }

        return false;
    }

    public void assignRolesToNodeTree(Node node)
    {
        Object userData = node.getUserData();
        if (userData instanceof ObservableList) {
            List<Role> roles = new ArrayList<>();
            for (Object object : (ObservableList) userData) {
                if (object instanceof Role) {
                    roles.add((Role) object);
                }
            }

            Role[] roleArray = new Role[roles.size()];
            int i = 0;
            for (Role role : roles) {
                roleArray[i++] = role;
            }
            assignRole(node, roleArray);
        }

        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                assignRolesToNodeTree(child);
            }
        }
    }
}
