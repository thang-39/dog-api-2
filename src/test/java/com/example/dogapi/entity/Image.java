package com.example.dogapi.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String imageName;
    private String type;
    private String filePath;
    private byte[] imageData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Dog dog;

}
