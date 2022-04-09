package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //jpa를 사용하기 위한 인식용 어노테이션
@Getter
@Setter
public class Member {
    @Id
    private Long id;
    private String name;

}
