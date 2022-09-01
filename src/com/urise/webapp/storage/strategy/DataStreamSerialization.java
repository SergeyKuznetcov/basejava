package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements SerializationStrategy{

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)){
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid,fullName);
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(dataInputStream.readUTF(),dataInputStream.readUTF());
            }
            size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType){
                    case PERSONAL, OBJECTIVE -> resume.getSections().put(sectionType, new TextSection(dataInputStream.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        ListSection section = new ListSection();
                        section.getDescriptions().addAll(readList(dataInputStream, () -> dataInputStream.readUTF()));
                        resume.getSections().put(sectionType, section);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection section = new OrganizationSection();
                        resume.getSections().put(sectionType, section);
                        section.getOrganizations().addAll(readList(dataInputStream, () -> {
                            Organization organization = new Organization(dataInputStream.readUTF(), dataInputStream.readUTF());
                            organization.getPeriods().addAll(readList(dataInputStream, () ->{
                                Period period = new Period(LocalDate.parse(dataInputStream.readUTF()), LocalDate.parse(dataInputStream.readUTF()),dataInputStream.readUTF(),dataInputStream.readUTF());
                                return period;
                            }));
                            return organization;
                        }));
                    }
                }
            }
            return resume;
        }
    }

    @Override
    public void writeFile(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)){
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<String,String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<String,String> entry : contacts.entrySet()){
                dataOutputStream.writeUTF(entry.getKey());
                dataOutputStream.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry:
                    sections.entrySet()){
                dataOutputStream.writeUTF(entry.getKey().toString());
                switch (entry.getKey()){
                    case PERSONAL, OBJECTIVE -> dataOutputStream.writeUTF(entry.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATION -> writeList(((ListSection)entry.getValue()).getDescriptions(), dataOutputStream, dataOutputStream::writeUTF);
                    case EDUCATION, EXPERIENCE -> writeList((((OrganizationSection) entry.getValue()).getOrganizations()), dataOutputStream, org ->{
                        dataOutputStream.writeUTF(org.getName());
                        dataOutputStream.writeUTF(org.getLink());
                        writeList(org.getPeriods(), dataOutputStream, period -> {
                            dataOutputStream.writeUTF(period.getDateFrom().toString());
                            dataOutputStream.writeUTF(period.getDateTo().toString());
                            dataOutputStream.writeUTF(period.getTitle());
                            dataOutputStream.writeUTF(period.getDescription());
                        });
                    });
                }
            }
        }
    }

    private interface ElementReader<T>{
        T read() throws IOException;
    }

    private interface ElementWriter<T>{
        void write(T t) throws IOException;
    }

    private <T> List<T> readList(DataInputStream dataInputStream, ElementReader<T> elementReader) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(elementReader.read());
        }
        return list;
    }

    private <T> void writeList(List<T> list, DataOutputStream dataOutputStream, ElementWriter<T> elementWriter) throws IOException {
        dataOutputStream.writeInt(list.size());
        for (T element :
                list) {
            elementWriter.write(element);
        }
    }
}
