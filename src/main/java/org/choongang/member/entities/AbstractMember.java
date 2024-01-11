package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="member")
@DiscriminatorColumn(name="MTYPE")
public abstract class AbstractMember extends Base {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 65, nullable = false)
    private String gid;

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
    private String username;

    @Column(length = 40, nullable = false)
    private String nickname;

    @Column(length = 40, nullable = false)
    private String tel;

    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Address> address = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Authorities> authorities = new ArrayList<>();
    
    @Transient
    private FileInfo profileImage;
}
