import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.entity.Tree;

import java.util.List;

public class TreeApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            TypedQuery<Tree> treeTypedQuery = manager.createQuery(
                    "select t from Tree t order by t.left_key", Tree.class
            );
            List<Tree> treeList = treeTypedQuery.getResultList();
            for (Tree t:treeList) {
                System.out.println("- ".repeat(t.getLevel()) + t.getName());
                }
            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
