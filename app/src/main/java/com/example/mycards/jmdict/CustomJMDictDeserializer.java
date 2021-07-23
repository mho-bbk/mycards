package com.example.mycards.jmdict;

import com.example.mycards.data.entities.JMDictEntry;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Followed steps from:
 * https://www.baeldung.com/jackson-object-mapper-tutorial
 * https://www.baeldung.com/jackson-nested-values
 */

public class CustomJMDictDeserializer extends StdDeserializer<JMDictEntry> {

    //This calls the protected constructor below
    public CustomJMDictDeserializer() {
        this(null);
    }

    protected CustomJMDictDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JMDictEntry deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        JMDictEntry entry = new JMDictEntry();
        JsonNode node = p.getCodec().readTree(p);

        //try catch block with logic for desired features
        //TODO - investigate how to iterate through JSON nested levels
        entry.setWordID(node.get("words").get("id").textValue());

        return null;
    }
}
