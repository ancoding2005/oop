import java.util.*;

public class Appearances {
    public static <T> int sameCount(Collection<T> a, Collection<T> b) {
        Map<T, Integer> countMapA = solve(a);
        Map<T, Integer> countMapB = solve(b);
        int Count = 0;

        for (Map.Entry<T, Integer> entry : countMapA.entrySet()) {
            T key = entry.getKey();
            int countA = entry.getValue();
            int countB = countMapB.getOrDefault(key, 0);
            if (countA == countB) {
                Count++;
            }
        }

        return Count;
    }

    private static <T> Map<T, Integer> solve(Collection<T> collection) {
        Map<T, Integer> countMap = new HashMap<>();
        for (T item : collection) {
            countMap.put(item, countMap.getOrDefault(item, 0) + 1);
        }
        return countMap;
    }

    public static void main(String[] args) {
        Collection<String> a = Arrays.asList("a", "b", "a", "b", "c");
        Collection<String> b = Arrays.asList("c", "a", "a", "d", "b", "b", "b");

        int result = sameCount(a, b);
        System.out.println(result);
    }
}
