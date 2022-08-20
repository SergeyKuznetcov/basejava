package com.urise.webapp.model;

import com.urise.webapp.exception.StorageException;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<String, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

    // Constructors
    //-----------------------------------------
    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }
    /*-------------------------------------------------------------
    Methods for contacts
    --------------------------------------------------------------*/
    public void addContact(String type, String info){
        contacts.put(getNotExistingKey(type), info);
    }

    public String getContact(String type){
        return contacts.get(getExistingKey(type));
    }

    public void deleteContact(String type){
        contacts.remove(getExistingKey(type));
    }

    public void updateContact(String type, String contact){
        contacts.replace(getExistingKey(type), contact);
    }

    public Set<Map.Entry<String, String>> getAllContacts() {
        return contacts.entrySet();
    }
    /*-----------------------------------------------------
    Method for Sections
     ------------------------------------------------------*/
    public void addSection(SectionType type, Section section){
        sections.put(getNotExistingKey(type), section);
    }

    public Section getSection(SectionType type){
        return sections.get(getExistingKey(type));
    }

    public void deleteSection(SectionType type){
        sections.remove(getExistingKey(type));
    }

    public void updateSection(SectionType type, Section section){
        sections.replace(getExistingKey(type), section);
    }

    public Set<Map.Entry<SectionType, Section>> getAllSections() {
        return sections.entrySet();
    }

    /*-----------------------------------------------------
    Generic method
     ------------------------------------------------------*/
    private <K> K getExistingKey(K key){
        if (!isContactExist(key)){
            throw new StorageException(key + " doesn't exist");
        }
        return key;
    }

    private <K> K getNotExistingKey(K key){
        if (isContactExist(key)){
            throw new StorageException(key + " already exist");
        }
        return key;
    }

    private <K> boolean isContactExist(K key){
        return contacts.containsKey(key);
    }

    /*-----------------------------------------------------
    Method for other fields
     ------------------------------------------------------*/

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    private String contactsToString(){
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

    private String organizationsToString(){
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
        return fullName + "\n" + contactsToString() + organizationsToString();
    }

    @Override
    public int compareTo(Resume o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
