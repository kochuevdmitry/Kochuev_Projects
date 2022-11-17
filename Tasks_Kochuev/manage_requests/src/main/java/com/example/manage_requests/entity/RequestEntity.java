package com.example.manage_requests.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "request")
@Getter
@Setter
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private long modifiedDate;
    private long length;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private FoldersEntity folder;

    @ManyToMany
    @JoinTable(name = "request_to_tags",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagsEntity> tagsEntityList = new ArrayList<>();

}
