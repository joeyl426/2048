import java.util.*;
/*This class is solely responsible for creating random trees with a specified minimum and maximum depth*/
public class TreeGenerator {
    public static final String[] Functions = {"+","-","rand","min","max"};
    public static final String[] Terminals = {"up", "down", "left","right"};
    private static Random random = new Random();
    
    public static Tree create(int minDepth, int maxDepth, int curDepth) {
        int end = random.nextInt(10);
        if ((maxDepth > 1 && (end > 4)) || minDepth > curDepth) {
            String function = Functions[random.nextInt(Functions.length)];
            return new Tree(function, create(minDepth, maxDepth - 1, curDepth + 1), create(minDepth, maxDepth - 1, curDepth + 1));
        }
        else {
            String terminal = Terminals[random.nextInt(Terminals.length)];
            return new Tree(terminal);
        }
    }
}