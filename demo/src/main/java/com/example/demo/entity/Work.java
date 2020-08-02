package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "works")
public class Work extends AuditModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2798874336035309798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startingDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endingDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
