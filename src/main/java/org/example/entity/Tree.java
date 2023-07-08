package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tree")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int left_key;
    private int right_key;
    private int level;

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

    public int getLeft_key() {
        return left_key;
    }

    public void setLeft_key(int left_key) {
        this.left_key = left_key;
    }

    public int getRight_key() {
        return right_key;
    }

    public void setRight_key(int right_key) {
        this.right_key = right_key;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
