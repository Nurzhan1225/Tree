import jakarta.persistence.*;
import org.example.entity.Tree;
import java.util.Scanner;

public class Move {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            boolean bol = true;
            Integer id1 = null, id2 = null;

while (bol) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Введите id родительской перемещаюмой категории: ");
    id1 = Integer.parseInt(sc.next());
    System.out.print("Введите id новой родительской категории: ");
    id2 = Integer.parseInt(sc.next());

    Tree tree1 = manager.find(Tree.class, id1);
    int r1 = tree1.getRight_key();
    int l1 = tree1.getLeft_key();
    if (id2 != 0) {
        Tree tree2 = manager.find(Tree.class, id2);
        int r2 = tree2.getRight_key();
        bol = r2 < r1 && r2 > l1;
    } else {
        bol = false;
    }
}
            Tree tree1 = manager.find(Tree.class, id1);
            int r1 = tree1.getRight_key();
            int l1 = tree1.getLeft_key();
            int a = r1 - l1 + 1;//разница ключей
            int level1 = tree1.getLevel();
            //Сделать ключей отрицательным
            Query query = manager.createQuery(
                    "update Tree t set t.left_key =  - t.left_key, t.right_key =  - t.right_key " +
                            "where t.left_key >= ?1 and t.left_key < ?2"
            );
            query.setParameter(1,l1);
            query.setParameter(2,r1);
            query.executeUpdate();

            //Убрать образовавшийся промежуток между ключей
            Query queryLeft = manager.createQuery(
                    "update Tree t set t.left_key = t.left_key - ?2  where t.left_key >= ?1"
            );
            queryLeft.setParameter(1,l1);
            queryLeft.setParameter(2,a);
            queryLeft.executeUpdate();

            Query queryRight = manager.createQuery(
                    "update Tree t set t.right_key = t.right_key - ?2  where t.right_key >= ?1"
            );
            queryRight.setParameter(1,l1);
            queryRight.setParameter(2,a);
            queryRight.executeUpdate();

            if(id2 == 0){
                TypedQuery<Integer> typedQuery = manager.createQuery(
                        "select max (t.right_key) from Tree t",Integer.class
                );
                int maxKey = typedQuery.getSingleResult(); //новый max ключ
                int key = maxKey - l1 + 1;

                Query query0 = manager.createQuery(
                        "update Tree t set t.left_key = ?1 - t.left_key, t.right_key = ?1 - right_key, t.level = t.level - ?2 " +
                                "where t.left_key < 0"
                );
                query0.setParameter(1, key);
                query0.setParameter(2, level1);
                query0.executeUpdate();

            } else {
                //Выделить место под вставку категории
                Tree tree2 = manager.find(Tree.class, id2);
                manager.refresh(tree2);
                int r2 = tree2.getRight_key();
                int level2 = tree2.getLevel();
                System.out.println(a + " " + r2 + " " + level2);

                Query queryL2 = manager.createQuery(
                        "update Tree t set t.left_key = t.left_key + ?2  where t.left_key >= ?1"
                );
                queryL2.setParameter(1, r2);
                queryL2.setParameter(2, a);
                queryL2.executeUpdate();

                Query queryR2 = manager.createQuery(
                        "update Tree t set t.right_key = t.right_key + ?2  where t.right_key >= ?1"
                );
                queryR2.setParameter(1, r2);
                queryR2.setParameter(2, a);
                queryR2.executeUpdate();

                //Отрицательные ключи сделать положительным
                int R = r2 + a; //новый правый родительский ключ
                int b = R - r1 - 1; //разница между новым родительским ключом и перемещаемым ключом
                int c = level2 - level1 + 1; // разница уровней новых и перемещаемых категории

                Query query3 = manager.createQuery(
                        "update Tree t set t.left_key = ?1 - t.left_key, t.right_key = ?1 - t.right_key, t.level = t.level + ?2" +
                                "where t.left_key < 0"
                );
                query3.setParameter(1, b);
                query3.setParameter(2, c);
                query3.executeUpdate();
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
