package com.example.dogapi.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    private String filePath;
//    private byte[] imageData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

}
