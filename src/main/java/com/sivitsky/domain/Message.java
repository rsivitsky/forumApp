package com.sivitsky.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Message {

    private long id;
    private String header;
    private String content;
    private Date created;
    private User user;
    private Topic topic;
    private Set<Attache> attacheSet;

    public Message() {
    }

    public Message(User user) {
        this.user = user;
    }

    public Message(Topic topic, User user) {
        this.topic = topic;
        this.user = user;
    }

    public Message(User user, Topic topic, String header, String content, Date created) {
        this.id = id;
        this.user = user;
        this.header = header;
        this.content = content;
        this.created = created;
        this.topic = topic;
    }

    public Message(long id, String header, String content, Date created, User user, Topic topic, Set<Attache> attacheSet) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.created = created;
        this.user = user;
        this.topic = topic;
        this.attacheSet = attacheSet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @ManyToOne
    @JoinColumn(name = "topic_id")
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<Attache> getAttacheSet() {
        return attacheSet;
    }

    public void setAttacheSet(Set<Attache> attacheSet) {
        this.attacheSet = attacheSet;
    }
}
