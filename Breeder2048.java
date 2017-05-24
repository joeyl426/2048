public class Breeder2048 implements Cloneable{

    /**
     *Copy the current Prisoner
     *@return a copy of the current Prisoner
     */
    public Object clone ()
    {
        Object self = null;
        try
        {
            self = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new RuntimeException("CloneNotSupportedException");
        }
        return self;
    }

    public Tree2048 tournamentSelect(Tree2048[] curpop){
        shufflePop(curpop);
        Tree2048 best = null;
        for (int i = 0; i < selParam; i++){
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

}