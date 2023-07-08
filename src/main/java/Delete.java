import jakarta.persistence.*;
import org.example.entity.Tree;
import java.util.Scanner;

public class Delete {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите id родительской категории: ");
            Integer id = Integer.parseInt(sc.next());

            Tree tree = manager.find(Tree.class, id);

            int l = tree.getLeft_key();
            int r = tree.getRight_key();

            Query query = manager.createQuery(
                    "delete from Tree t where t.left_key >= ?1 and t.left_key <= ?2 or" +
                            " t.right_key >= ?1 and t.right_key <= ?2"
            );
            query.setParameter(1,l);
            query.setParameter(2,r);
            query.executeUpdate();

            int a = tree.getRight_key() - tree.getLeft_key() + 1;

            Query queryLeft = manager.createQuery(
                    "update Tree t set t.left_key = t.left_key - ?2  where t.left_key >= ?1"
            );
            queryLeft.setParameter(1,l);
            queryLeft.setParameter(2,a);
            queryLeft.executeUpdate();

            Query queryRight = manager.createQuery(
                    "update Tree t set t.right_key = t.right_key - ?2  where t.right_key >= ?1"
            );
            queryRight.setParameter(1,l);
            queryRight.setParameter(2,a);
            queryRight.executeUpdate();

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
