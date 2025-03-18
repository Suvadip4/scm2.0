package com.scm.entities;




import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.*;

@Entity
public class contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    @Column(length = 1000)
    private String description;
    private boolean favourite=false;
    private String instagramLink;
    private String facebookLink;
    private String websiteLink;
    private String linkedInLink;
    //private List<String> sociallinks=new ArrayList<>();
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "Contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links=new ArrayList<>();
}
