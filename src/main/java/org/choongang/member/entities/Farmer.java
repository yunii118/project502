package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

@Data
@Entity
@DiscriminatorValue("F")
public class Farmer extends AbstractMember{
    @Column(length = 90, nullable = false)
    private String farmTitle;

    @Column(length = 15, nullable = false)
    private String businessPermitNum;

    @Transient
    private FileInfo businessPermit;


}
