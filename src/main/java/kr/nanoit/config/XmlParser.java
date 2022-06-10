package kr.nanoit.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.jaxb.XmlJaxbAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

public class XmlParser {
    public <T> String write(final T object) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setAnnotationIntrospector(XmlAnnotationIntrospector.Pair
                .instance(new XmlJaxbAnnotationIntrospector(), new JacksonAnnotationIntrospector()));
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);



        String xml = null;
        try {
            xml = xmlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
