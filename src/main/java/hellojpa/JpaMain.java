package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // src/main/resources/META-INF/persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 위의 엔티티 매니저는 최초에만 만들어주면 된다.
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("Hello JPA");

            // DB에서 트랜잭션이란 단위는 중요하다
            //em.persist(findMember);

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member: result) {
                System.out.println("member.getName() = " + member.getName());
            }
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // code
        emf.close();

        // close를 해줘야 리소스들이 반환된다.

    }
}
