package sda.catalogue.sdacataloguerestapi;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public CustomLocalDateDeserializer(DateTimeFormatter formatter){
        this.formatter=formatter;
    }
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        String dateString = jsonParser.getText();
        return LocalDate.parse(dateString, formatter);
    }
}
