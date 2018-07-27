package com.github.teocci.codesample.javafx.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class Person
{
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleBooleanProperty married;

    public Person(String firstName, Boolean isMarried)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.married = new SimpleBooleanProperty(isMarried);
    }

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty("");
        this.married = new SimpleBooleanProperty(false);
    }


    public Person(String firstName, String lastName, String email)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.married = new SimpleBooleanProperty(false);
    }

    public SimpleStringProperty firstNameProperty() { return firstName; }

    public SimpleBooleanProperty marriedProperty() { return married; }


    public String getFirstName()
    {
        return firstName.get();
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public String getEmail()
    {
        return email.get();
    }

    public Boolean getMarried() { return married.get(); }


    public void setFirstName(String fName)
    {
        firstName.set(fName);
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    public void setEmail(String email)
    {
        this.email.set(email);
    }

    public void setMarried(Boolean isMarried) { this.married.set(isMarried); }

    @Override
    public String toString() { return firstName.getValue() + ": " + married.getValue(); }
}