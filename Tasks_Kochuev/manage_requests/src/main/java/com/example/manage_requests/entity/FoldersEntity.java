package com.example.manage_requests.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
@Getter
@Setter
public class FoldersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String folderName;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<RequestEntity> requestEntityList = new ArrayList<>();

}
