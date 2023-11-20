package com.example.livingbymyselfserver.attachment.entity;

import com.example.livingbymyselfserver.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Attachment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(length = 65535)
    protected String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
