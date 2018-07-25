package com.github.teocci.codesample.javafx.uisamples.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class CustomSerializerExample
{

    public static void main(String[] args) throws
            IOException
    {

        // create the mapper
        ObjectMapper mapper = new ObjectMapper();

        // enable pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // create a custom serializer module
        SimpleModule customSerializerModule = new SimpleModule();
        // add serializer for the Compensation class
        customSerializerModule.addSerializer(Compensation.class, new CompensationSerializer());
        // register the serializer module
        mapper.registerModule(customSerializerModule);

        // serialize the object
        mapper.writeValue(System.out, getEmployee());
    }

    static Employee getEmployee()
    {
        Employee employee = new Employee();
        employee.setId(1001);
        employee.setName("Drona");
        employee.setAge(25);
        employee.setDesignation("Manager");

        Compensation compensation = new Compensation();
        compensation.setCurrency('₹');
        compensation.setSalary(30000);

        employee.setCompensation(compensation);
        return employee;
    }
}
