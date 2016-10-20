package com.sivitsky.domain;

import javax.persistence.*;

@Entity
public class Attache {
    private long id;
    private String filepath;
    private Message message;

    public Attache() {
    }

    public Attache(long id, String filepath, Message message) {
        this.id = id;
        this.filepath = filepath;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @ManyToOne
    @JoinColumn(name = "message_id")
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
