import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class test1 {
    public static void main(String[] args) {
        /*for (int i = 1; i <= 100; i=i+2) {
            int a = 0;
            for (int j = 1; j <= i; j++) {
                if ((i % j) == 0){
                    a++;
                }
            }
            if (a <= 2)
            {
                System.out.print(i + ", ");
            }
        }*/

        Integer [] a = {5,78,2,6,95,32,54,62,83,62,1,88,45};
        Arrays.sort(a, Collections.reverseOrder());
        for (int b : a) {
            System.out.print(b + ", ");
        }

    }
}
