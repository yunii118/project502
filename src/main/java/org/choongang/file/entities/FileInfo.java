package org.choongang.file.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.BaseMember;

import java.util.UUID;

@Data
//@Builder
//@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name="idx_fileInfo_gid", columnList = "gid"),
        @Index(name = "idx_fInfo_gid_loc", columnList = "gid,location")
})
public class FileInfo extends BaseMember {
    @Id
    @GeneratedValue
    private Long seq; // 파일 등록 번호, 서버에 등록되는 파일명

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 중복되지 않는 random 숫자를 만들 수 있는 기능 중 하나

    @Column(length = 65)
    private String location;

    @Column(length = 80)
    private String fileName;

    @Column(length = 30)
    private String extension;

    private boolean done;
}
