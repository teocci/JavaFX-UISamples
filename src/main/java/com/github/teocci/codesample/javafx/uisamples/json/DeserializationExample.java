package com.github.teocci.codesample.javafx.uisamples.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class DeserializationExample
{

    public static void main(String[] args) throws IOException
    {

        // create the mapper
        ObjectMapper mapper = new ObjectMapper();

        // de-serialize JSON to object
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
                "    \"compensation\" : {           " +
                "        \"currency\" : \"₹\",      " +
                "        \"salary\" : 30000         " +
                "    }                              " +
                "}                                 ";
    }
}