package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            resume.getContacts().putAll(fillMap(readCollection(dataInputStream, () -> Map.entry(dataInputStream.readUTF(), dataInputStream.readUTF()))));
            resume.getSections().putAll(fillMap(readCollection(dataInputStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                Section section = null;
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> section = new TextSection(dataInputStream.readUTF());
                    case ACHIEVEMENT, QUALIFICATION -> {
                        section = new ListSection();
                        ((ListSection) section).getDescriptions().addAll(readCollection(dataInputStream, dataInputStream::readUTF));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        section = new OrganizationSection();
                        ((OrganizationSection) section).getOrganizations().addAll(readCollection(dataInputStream, () -> {
                            Organization organization = new Organization(dataInputStream.readUTF(), dataInputStream.readUTF());
                            organization.getPeriods().addAll(readCollection(dataInputStream, () ->
                                    new Period(LocalDate.parse(dataInputStream.readUTF()), LocalDate.parse(dataInputStream.readUTF()), dataInputStream.readUTF(), dataInputStream.readUTF())));
                            return organization;
                        }));
                    }
                }
                return Map.entry(sectionType, section);
            })));
            return resume;
        }
    }


    @Override
    public void writeFile(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            writeCollection(resume.getContacts().entrySet(), dataOutputStream, entry -> {
                dataOutputStream.writeUTF(entry.getKey());
                dataOutputStream.writeUTF(entry.getValue());
            });
            writeCollection(resume.getSections().entrySet(), dataOutputStream, entry -> {
                dataOutputStream.writeUTF(entry.getKey().toString());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> dataOutputStream.writeUTF(entry.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATION -> writeCollection(((ListSection) entry.getValue()).getDescriptions(), dataOutputStream, dataOutputStream::writeUTF);
                    case EDUCATION, EXPERIENCE -> writeCollection((((OrganizationSection) entry.getValue()).getOrganizations()), dataOutputStream, org -> {
                        dataOutputStream.writeUTF(org.getName());
                        dataOutputStream.writeUTF(org.getLink());
                        writeCollection(org.getPeriods(), dataOutputStream, period -> {
                            dataOutputStream.writeUTF(period.getDateFrom().toString());
                            dataOutputStream.writeUTF(period.getDateTo().toString());
                            dataOutputStream.writeUTF(period.getTitle());
                            dataOutputStream.writeUTF(period.getDescription());
                        });
                    });
                }
            });
        }
    }

    private <K, V> Map<K, V> fillMap(Collection<Map.Entry<K, V>> collection) {
        Map<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> entry :
                collection) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }


    private interface ElementWriter<T> {
        void write(T t) throws IOException;

    }

    private <T> Collection<T> readCollection(DataInputStream dataInputStream, ElementReader<T> elementReader) throws
            IOException {
        int size = dataInputStream.readInt();
        Collection<T> collection = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            collection.add(elementReader.read());
        }
        return collection;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream
            dataOutputStream, ElementWriter<T> elementWriter) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T element :
                collection) {
            elementWriter.write(element);
        }
    }
}
