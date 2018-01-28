package com.apps.vados.notes.model;

public class Note {
    private long id;
    private String description;

    public Note(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        if (getDescription().length() > 25) return getDescription().substring(0,24) + "...";
        else return getDescription();
    }
}
