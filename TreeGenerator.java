import java.util.*;

public class TreeGenerator {
    public static final String[] Functions = {"+","-","rand","min","max"};
    public static final String[] Terminals = {"up", "down", "left","right"};
    private static Random random = new Random();
    
    public static Tree2048 create(int maxDepth) {
        int end = random.nextInt(10);
        if (maxDepth > 1 && (end > 2)) {
            String function = Functions[random.nextInt(Functions.length)];
            return new Tree2048(function, create(maxDepth - 1), create(maxDepth - 1));
        }
        else {
            String terminal = Terminals[random.nextInt(Terminals.length)];
            return new Tree2048(terminal);
        }
    }
    
    public static void main(String[] args) {
        Tree2048 tree = create(5);
        tree.inOrder(tree);

    }

}