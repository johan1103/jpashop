package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        //DB를 관리하는 하나의 엔티티매니저 팩토리, 항상 하나만 있는것이 특징
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //JPA를 활용하기 위한 기본 툴, 엔티티매니저 생성, 일회용이기 때문에, JPA를 쓸일이 있을 때 생성하고
        //볼일이 끝나면 close로 닫아주어야한다. **** 쓰레드간 공유 절대 안됨
        EntityManager em = emf.createEntityManager();
        //모든 JPA는 데이터의 변경가능성이 있기 때문에 트랜잭션 안에서 수행되어야 한다.
        //JPA는 트랜잭션이 종료될때 한번에 쿼리를 처리한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = new Member();
        try {
            //멤버 저장 CRUD중 C
            em.persist(member);
            //일이 정상처리 되었으면 여기까지 올테니, 완료된 정보 쿼리로 쏘기 위해 commit
            tx.commit();
        }catch (Exception e){
            //만약 에러가 나서 트랜잭션이 중단되어서 모든 일을 취소해야한다면 롤백
            tx.rollback();
        }finally {
            //일이 잘 처리 되었든 안되었든 끝나면 닫아주기
            em.close();
        }
        emf.close();
    }
}
