package com.app.ws.springboot.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Id
    private Long id;
    @Column(length = 100)
    private String country;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String code_postal;
    @Column(length = 100)
    private String street;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
