import java.util.*;

public class Taboo<T> {
    private final Map<T, Set<T>> tabooRules;

    public Taboo(List<T> rules){
        tabooRules = new HashMap<>();
        T prev = null;
        for (T rule: rules){
            if (rule != null){
                if (prev != null) {
                    Set<T> noFollowSet = tabooRules.getOrDefault(prev,new HashSet<>());
                    noFollowSet.add(rule);
                    tabooRules.put(prev, noFollowSet);
                }
                prev = rule;
            }
        }
    }
    public Set<T> noFollow(T elm){
        return tabooRules.getOrDefault(elm,Collections.emptySet());
    }
    public void reduce(List<T> list){
        if (list == null || list.isEmpty()) return;
        Iterator<T> iterator = list.iterator();
        T prev = iterator.next();
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (tabooRules.containsKey(prev) && tabooRules.get(prev).contains(current)) {
                iterator.remove();
            } else {
                prev = current;
            }
        }
    }
    public static void main(String[] args) {
        List<String> rules = Arrays.asList("a", "c", "a", "b");
        Taboo<String> taboo = new Taboo<>(rules);
        System.out.println(taboo.noFollow("a"));
        List<String> list = new ArrayList<>(Arrays.asList("a", "c", "b", "x", "c", "a"));
        taboo.reduce(list);
        System.out.println(list);
    }
}