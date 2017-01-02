package com.sivitsky.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    private long id;
    private String login;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Message> messageSet;
    private Set<Topic> topics;

    public User() {
    }

    public User(String login, String password, Role role, String firstName, String lastName, String email) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(long id, String login, String password, Role role, String firstName, String lastName, String email, Set<Message> messageSet, Set<Topic> topics) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.messageSet = messageSet;
        this.topics = topics;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Message> getMessages() {
        return messageSet;
    }

    public void setMessages(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return login;
    }
}
