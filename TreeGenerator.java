import java.util.*;

public class TreeGenerator {
    public static final String[] Functions = {"+","-","rand","min","max"};
    public static final String[] Terminals = {"up", "down", "left","right"};
    private static Random random = new Random();
    
    public static Tree2048 create(int minDepth, int maxDepth, int curDepth) {
        int end = random.nextInt(10);
        if ((maxDepth > 1 && (end > 4)) || minDepth > curDepth) {
            String function = Functions[random.nextInt(Functions.length)];
            return new Tree2048(function, create(minDepth, maxDepth - 1, curDepth + 1), create(minDepth, maxDepth - 1, curDepth + 1));
        }
        else {
            String terminal = Terminals[random.nextInt(Terminals.length)];
            return new Tree2048(terminal);
        }
    }
    
    public static void main(String[] args) {
        Tree2048 t = create(5,5,1);
        System.out.println("t1: ");
        t.printTree();
        
        Tree2048 t1 = create(5,5,1);
        System.out.println("\n\nt2: ");
        t1.printTree();
       
        
        
        t.crossover(100, t1);
        System.out.println("\n\nt1 after crossover: ");
        t.printTree();
        System.out.println("\n\nt2 after crossover: ");
        t1.printTree();
        
    }

}