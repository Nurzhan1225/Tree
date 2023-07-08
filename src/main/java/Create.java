import jakarta.persistence.*;
import org.example.entity.Tree;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Create {

    public static void main(String[] args) {
        // Введите id родительской категории: 2
        // Введите название категории: МЦСТ
        // Данный код должен привети к создании новой категории МЦСТ внутри категории Процессоры.

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите id родительской категории: ");
            Integer id = Integer.parseInt(sc.next());
            System.out.print("Введите название категории: ");
            String newName = sc.next();

            if(id == 0 ){
                TypedQuery<Integer> typedQuery = manager.createQuery(
                        "select max (t.right_key) from Tree t",Integer.class
                );
                int maxKey = typedQuery.getSingleResult();
                Tree tree1 = new Tree();
                tree1.setName(newName);
                tree1.setLevel(id);
                tree1.setLeft_key(maxKey+1);
                tree1.setRight_key(maxKey+2);
                manager.persist(tree1);
            }
            else {
                Tree tree = manager.find(Tree.class, id);
                int a = tree.getRight_key();
                // update ??? set ??? = ??? where ???
                Query queryLeft = manager.createQuery(
                        "update Tree t set t.left_key = t.left_key + 2 where t.left_key >= ?1"
                );
                queryLeft.setParameter(1, a);
                queryLeft.executeUpdate();

                Query queryRight = manager.createQuery(
                        "update Tree t set t.right_key = right_key + 2 where t.right_key >= ?1"
                );
                queryRight.setParameter(1, a);
                queryRight.executeUpdate();

                Tree tree1 = new Tree();
                tree1.setName(newName);
                tree1.setLevel(tree.getLevel() + 1);
                tree1.setLeft_key(tree.getRight_key());
                tree1.setRight_key(tree.getRight_key() + 1);
                manager.persist(tree1);
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
    }
}}
