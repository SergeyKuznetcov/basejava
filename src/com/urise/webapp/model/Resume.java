package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<String, String> contacts = new HashMap<>();
    private Map<SectionType, Section> sections = new HashMap<>();

    // Constructors
    //-----------------------------------------


    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }
    /*-----------------------------------------------------
    Methods
     ------------------------------------------------------*/

    public void addContact(String contactType, String info) {
        contacts.put(contactType, info);
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    private String contactsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry :
                contacts.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String organizationsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<SectionType, Section> entry :
                sections.entrySet()) {
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append("\n");
            stringBuilder.append(entry.getValue().toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return uuid + " " + fullName + "\n" + contactsToString() + organizationsToString();
    }

    @Override
    public int compareTo(Resume o) {
        return this.getUuid().compareTo(o.getUuid());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && contacts.equals(resume.contacts) && sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
