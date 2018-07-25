package com.github.teocci.codesample.javafx.uisamples.json;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class Employee
{
    private int id;
    private String name;
    private int age;
    private String designation;
    private Compensation compensation;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public Compensation getCompensation()
    {
        return this.compensation;
    }

    public void setCompensation(Compensation compensation)
    {
        this.compensation = compensation;
    }

    @Override
    public String toString()
    {

        return String
                .format("Employee: [id: %s, name: %s, age: %s, designation: %s, compensation: %s ]",
                        id, name, age, designation, compensation);
    }
}
