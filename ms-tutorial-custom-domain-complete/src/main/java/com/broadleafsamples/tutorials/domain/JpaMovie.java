package com.broadleafsamples.tutorials.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TUTORIAL_MOVIE")
@Data
public class JpaMovie {

    @Id
    private Long id;

    @Column
    private String title;

    public JpaMovie() {
    }

    public JpaMovie(String title) {
        this.title = title;
    }
}
