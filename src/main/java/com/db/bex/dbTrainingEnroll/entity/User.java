package com.db.bex.dbTrainingEnroll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String mail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @JsonIgnore
    @Column(nullable = false)
    private boolean enabled;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id")
    private User manager;

    @JsonIgnore
    @OneToMany(mappedBy="manager" , fetch = FetchType.EAGER)
    private Set<User> subordinates;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Enrollment> enrollments;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Set<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<User> subordinates) {
        this.subordinates = subordinates;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    //    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", mail='" + mail + '\'' +
//                ", type=" + type +
//                ", manager=" + manager +
//                '}';
//    }

}
