package com.mika.tasks.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tasks")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    @CreationTimestamp
    private Date date;
    @ManyToOne
    private User user;
    


}
