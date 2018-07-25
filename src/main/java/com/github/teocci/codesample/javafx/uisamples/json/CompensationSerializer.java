package com.github.teocci.codesample.javafx.uisamples.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Nov-23
 */
public class CompensationSerializer extends JsonSerializer<Compensation>
{

    @Override
    public void serialize(Compensation value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException
    {

        jgen.writeString(value.getCurrency() + " " + value.getSalary());
    }
}
