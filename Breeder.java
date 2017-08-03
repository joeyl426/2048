import java.util.*;

/* Breeder contains all of the evolutionary paramaters such as mutation probability, crossover probability, tournament size, population size, minimum and maximum depth. It also contains our two implementations of tournament selection; one which selects based on score and another that selects based on max tile. */
public class Breeder{
    
    private int mutateP,crossP,tournamentSize,popSize,minDepth, maxDepth;
    private Random random = new Random();
    
    public Breeder() {
        mutateP = 100;
        crossP = 5;
        tournamentSize = 5;
        popSize = 65;
        minDepth = 3;
        maxDepth = 7;
    }

    public Tree tournamentSelectScore(Tree[] curpop, int selection){
        shufflePop(curpop);
        Tree best = null;
        for (int i = 0; i < selection; i++){
            if (best == null) {
                best = curpop[i];
            }
            else if (best.getScore() < curpop[i].getScore()) {
                best = curpop[i];
            }
        }
        best = (Tree)best.clone();
        return best;
    }
    
    public Tree tournamentSelectMaxTile(Tree[] curpop, int selection){
        shufflePop(curpop);
        Tree best = null;
        for (int i = 0; i < selection; i++){
            if (best == null) {
                best = curpop[i];
            }
            else if (best.getMaxTile() < curpop[i].getMaxTile()) {
                best = curpop[i];
            }
        }
        best = (Tree)best.clone();
        return best;
    }
    

    public void shufflePop(Tree[] curpop) {
        int randIndex;
        Tree curIndex;
        Tree cur;
        Random random = new Random();
        for (int i = curpop.length - 1; i > 0; i--){
            randIndex = random.nextInt(i + 1);
            curIndex = curpop[randIndex];
            curpop[randIndex] = curpop[i];
            curpop[i] = curIndex;
        }
    }
    
    public int depth(Tree tree,int d) {
        int leftDepth = d, rightDepth = d;
        if(tree.getLeft() != null){
            leftDepth = depth(tree.getLeft(), d+1);
        }
        if(tree.getRight() != null){
            rightDepth = depth(tree.getRight(), d+1);
        }

        return leftDepth > rightDepth ? leftDepth : rightDepth;
    }
    
    public Tree[] createPopulation(){
        Tree[] curpop = new Tree[popSize];
        TreeGenerator tg = new TreeGenerator();
        for(int i = 0; i < popSize; i++){
            Tree t = tg.create(minDepth, maxDepth, 1);
            t.setDepth(depth(t,1));
            t.setScore(0);
            t.setMaxTile(2);
            curpop[i] = t;
        }
        return curpop;
    }
    
    public void printPop(Tree[] pop){
        for(int j = 0; j < pop.length; j++){
                pop[j].postOrder();
                System.out.println();
            }
        System.out.println();
    }
    
        public void printPopWithScore(Tree[] pop){
        for(int j = 0; j < pop.length; j++){
                pop[j].postOrder();
                System.out.println("Score: " + pop[j].getScore());
            }
        System.out.println();
    }
    
    public Tree[] breed(Tree[] pop) {
        Tree[] selected = new Tree[popSize];
        //Perform selection based on score for first half of pop
        for (int i = 0; i < popSize/2; i++){
            selected[i] = tournamentSelectScore(pop,tournamentSize);
        }
        //Perform selection based on max tile for second half of pop
        for (int i = popSize/2; i < popSize; i++){
            selected[i] = tournamentSelectMaxTile(pop,tournamentSize);
        }
        shufflePop(selected);
        selected[0].mutate(mutateP);
        for (int i = 1; i < popSize; i++){
            selected[i].mutate(mutateP);
        }
        return selected;
    }
    
    public Tree getBest(Tree[] pop){
        Tree bestIndividual = new Tree("");
         for (int i = 0; i < popSize; i++){
            if(pop[i].getFitness() > bestIndividual.getFitness()){
                bestIndividual = pop[i];
            }
        }
        return bestIndividual;
    }
    
    public static void main(String[] args) {
        Breeder b = new Breeder();
        Tree[] pop = b.createPopulation();
        b.printPop(pop);
        b.shufflePop(pop);
        System.out.print("\n\n\n\n");
        b.printPop(pop);
    }

}