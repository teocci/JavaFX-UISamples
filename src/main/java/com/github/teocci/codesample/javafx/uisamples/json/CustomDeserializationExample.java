package com.github.teocci.codesample.javafx.uisamples.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class CustomDeserializationExample
{
    public static void main(String[] args) throws JsonGenerationException,
            JsonMappingException, IOException
    {

        // create the mapper
        ObjectMapper mapper = new ObjectMapper();
        // create a custom de-serializer module
        SimpleModule customDeSerializerModule = new SimpleModule();
        // add de-serializer for the Employee class
        customDeSerializerModule.addDeserializer(Employee.class, new EmployeeDeserializer());
        // register the de-serializer module
        mapper.registerModule(customDeSerializerModule);

        // serialize the object
        Employee employee = mapper.readValue(getEmployeeJson(), Employee.class);

        // print the de-serialized object
        System.out.println(employee);
    }

    static String getEmployeeJson()
    {
        return "{                                 " +
                "    \"id\" : 1001,                 " +
                "    \"name\" : \"Drona\",          " +
                "    \"age\" : 25,                  " +
                "    \"designation\" : \"Manager\", " +
                "    \"compensation\" : \"₹ 30000\" " +
                "}                                 ";
    }
}