package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerialization implements SerializationStrategy{
    private XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Period.class, ListSection.class,
                OrganizationSection.class, TextSection.class
        );
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public void writeFile(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)){
            xmlParser.marshall(resume,writer);
        }
    }
}
