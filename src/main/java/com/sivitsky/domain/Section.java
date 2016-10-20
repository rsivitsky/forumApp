package com.sivitsky.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Section {

    private long id;
    private String name;
    private Set<Topic> topics;

    public Section() {
    }

    public Section(long id, String name) {
        this.id = id;
        this.name = name;
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

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}
