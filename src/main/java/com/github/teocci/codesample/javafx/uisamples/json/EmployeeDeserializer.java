package com.github.teocci.codesample.javafx.uisamples.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class EmployeeDeserializer extends JsonDeserializer<Employee>
{
    @Override
    public Employee deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
    {
        JsonNode node = jp.getCodec().readTree(jp);

        Employee employee = new Employee();
        employee.setId(node.get("id").asInt());
        employee.setName(node.get("name").textValue());
        employee.setAge(node.get("age").asInt());
        employee.setDesignation(node.get("designation").textValue());

        String salary = node.get("compensation").textValue();

        Compensation compensation = new Compensation();
        compensation.setCurrency(salary.charAt(0));
        compensation.setSalary(Long.parseLong(salary.substring(2)));
        employee.setCompensation(compensation);

        return employee;
    }
}
