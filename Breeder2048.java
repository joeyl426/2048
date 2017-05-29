import java.util.*;

public class Breeder2048{
    
    private int mutateP,crossP,selParam,popSize,treeDepth;
    private Random random = new Random();
    
    public Breeder2048() {
        mutateP = 30;
        crossP = 5;
        selParam = 5;
        popSize = 65;
        treeDepth = 6;
    }

    public Tree2048 tournamentSelect(Tree2048[] curpop, int selection){
        shufflePop(curpop);
        Tree2048 best = null;
        for (int i = 0; i < selection; i++){
            if (best == null) {
                best = curpop[i];
            }
            else if (best.getFitness() < curpop[i].getFitness()) {
                best = curpop[i];
            }
        }
        best = (Tree2048)best.clone();
        return best;
    }

    public void shufflePop(Tree2048[] curpop) {
        int randIndex;
        Tree2048 curIndex;
        Tree2048 cur;
        Random random = new Random();
        for (int i = curpop.length - 1; i > 0; i--){
            randIndex = random.nextInt(i + 1);
            curIndex = curpop[randIndex];
            curpop[randIndex] = curpop[i];
            curpop[i] = curIndex;
        }
    }
    
    public int depth(Tree2048 tree,int d) {
        int leftDepth = d, rightDepth = d;
        if(tree.getLeft() != null){
            leftDepth = depth(tree.getLeft(), d+1);
        }
        if(tree.getRight() != null){
            rightDepth = depth(tree.getRight(), d+1);
        }

        return leftDepth > rightDepth ? leftDepth : rightDepth;
    }
    
    public Tree2048[] createPopulation(){
        Tree2048[] curpop = new Tree2048[popSize];
        TreeGenerator tg = new TreeGenerator();
        for(int i = 0; i < popSize; i++){
            Tree2048 t = tg.create(treeDepth);
            t.setDepth(depth(t,1));
            t.setScore(0);
            t.setMaxTile(2);
            curpop[i] = t;
        }
        return curpop;
    }
    
    public void printPop(Tree2048[] pop){
        for(int j = 0; j < pop.length; j++){
                pop[j].postOrder();
                System.out.println();
            }
        System.out.println();
    }
    
        public void printPopWithScore(Tree2048[] pop){
        for(int j = 0; j < pop.length; j++){
                pop[j].postOrder();
                System.out.print("Score: " + pop[j].getScore());
                System.out.println();
            }
        System.out.println();
    }
    
    public Tree2048[] breed(Tree2048[] pop) {
        Tree2048[] selected = new Tree2048[popSize];
        for (int i = 0; i < popSize; i++){
            selected[i] = tournamentSelect(pop,selParam);
        }
        for (int i = 0; i < popSize; i++){
            selected[i].mutate(mutateP);
        }
        return selected;
    }
    
    public Tree2048 getBest(Tree2048[] pop){
        Tree2048 bestIndividual = new Tree2048("");
         for (int i = 0; i < popSize; i++){
            if(pop[i].getFitness() > bestIndividual.getFitness()){
                bestIndividual = pop[i];
            }
        }
        return bestIndividual;
    }
    
    public static void main(String[] args) {
        Breeder2048 b = new Breeder2048();
        Tree2048[] pop = b.createPopulation();
        b.printPop(pop);
        b.shufflePop(pop);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        b.printPop(pop);
        Tree2048 selected = b.tournamentSelect(pop, 3);
        selected.postOrder();
        
    }

}