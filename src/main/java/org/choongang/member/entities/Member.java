package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entities.Base;

@Data
@Entity
public class Member extends Base {
    @Id
    @GeneratedValue
    private Long seq;

    // id로 사용할것
    @Column(length = 80, nullable = false, unique = true)
    private String email;

    // id2
    @Column(length = 80, nullable = false, unique = true)
    private String userId;

    @Column(length = 65, nullable = false)
    private String password;

    // 회원명
    @Column(length = 40, nullable = false)
    private String name;

}
