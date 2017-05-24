import java.util.*;

public class Breeder2048{
    
    private Tree2048 curpop[];
    private int mutateP,crossP,selParam,popSize;
    private Random random = new Random();
    
    public Breeder2048() {
        mutateP = 4;
        crossP = 5;
        selParam = 10;
        popSize = 50;
    }

    public Tree2048 tournamentSelect(Tree2048[] curpop,int selection){
        shufflePop(curpop);
        Tree2048 best = null;
        for (int i = 0; i < selection; i++){
            if (best == null) {
                best = curpop[i];
            }
            else if (best.getScore() < curpop[i].getScore()) {
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
    
    public Tree2048[] createPopulation(){
        curpop = new Tree2048[popSize];
        TreeGenerator tg = new TreeGenerator();
        for(int i = 0; i < popSize; i++){
            Tree2048 t = tg.create(10);
            t.setScore(0);
            curpop[i] = t;
        }
        return curpop;
    }
    
    public Tree2048[] breed(Tree2048[] pop) {
        Tree2048[] selected = new Tree2048[popSize];
        for (int i = 0; i < popSize; i++){
            selected[i] = tournamentSelect(pop,selParam);
        }
        for (int i = 0; i < popSize; i++){
            selected[i] = selected[i].mutate(selected[i],mutateP);
        }
        return selected;
    }

}