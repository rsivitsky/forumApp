package com.sivitsky.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
public class Topic {

    private long id;
    private String name;
    private String picpath;
    private Date created;
    private User user;
    private Set<Message> messageSet;
    private Section section;

    public Topic() {
    }

    public Topic(long id, String name, String picpath, Date created) {
        this.id = id;
        this.name = name;
        this.picpath = picpath;
        this.created = created;
    }

    public Topic(long id, String name, String picpath, Date created, User user, Set<Message> messageSet) {
        this.id = id;
        this.name = name;
        this.picpath = picpath;
        this.created = created;
        this.user = user;
        this.messageSet = messageSet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    public Set<Message> getMessages() {
        return messageSet;
    }

    public void setMessages(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    @ManyToOne
    @JoinColumn(name = "section_id")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
        return "(total " + messageSet.size() + " message(s))";
    }
}
