import java.util.*;

/*This class contains all the information necessary for representing our tree. This consists of the value of the node, the left & right subtrees, the score of the tree, the max tile, and the depth. This class also contains functions used to evaluate the the output (move) of the tree, as well as functions for mutation, crossover, & visual representation of trees. We could not get crossover to work due to problems with Java passing object references in methods in a way that we did not expect, however our algorithm is there and could be fixed given more time*/

public class Tree2048 implements Cloneable{
    String value;
    Tree2048 left, right;
    Random random = new Random();
    int score = 0;
    int maxTile;
    int depth;

    public Tree2048(String value){
        this.value=value;
    }

    public Tree2048 (String value, Tree2048 left, Tree2048 right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

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

    public String getValue(){
        return value;}
    public void setValue(String value){
        this.value = value;}

    public Tree2048 getLeft(){
        return left;}
    public int getScore() {
        return score;
    }
    public void setScore(int newScore) {
        score = newScore;
    }
    public int getMaxTile() {
        return maxTile;
    }
    public void setMaxTile(int newMaxTile) {
        maxTile = newMaxTile;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int newDepth) {
        depth = newDepth;
    }
    public Tree2048 getRight(){
        return right;}
    public void setLeft(Tree2048 ln){
        left = ln;}
    public void setRight(Tree2048 rn){
        right = rn;}

    //Evaluate the tree to output a move (number from 0 to 3)
    public int evaluate(){
        switch(this.value) {
            case "+":{ 
                int sum = left.evaluate() + right.evaluate();
                if ((sum > 3) || (sum < 0)){
                    sum = sum % 3;
                    if (sum < 0){
                        return sum + 2;
                    }
                    else {
                        return sum;
                    }
                }
                else{
                    return sum;
                }
            }
            case "-":{
                int sum = left.evaluate() - right.evaluate();
                if ((sum > 3) || (sum < 0)){
                    sum = sum % 3;
                    if (sum < 0){
                        return sum + 2;
                    }
                    else{
                        return sum;
                    }
                }
                else{
                    return sum;
                }
            }
            case "min":{
                if (left.evaluate() < right.evaluate()) {
                    return left.evaluate();
                }
                else {
                    return right.evaluate();
                }
            }
            case "max":{
                if (left.evaluate() > right.evaluate()) {
                    return left.evaluate();
                }
                else {
                    return right.evaluate();
                }
            }
            case "rand":{
                if (random.nextInt(10) < 5) {
                    return left.evaluate();
                }
                else {
                    return right.evaluate();
                }
            }
            case "right":{
                return Game2048.senseRight();
            }
            case "left":{
                return Game2048.senseLeft();
            }
            case "up":{
                return Game2048.senseUp();
            }
            case "down":{
                return Game2048.senseDown();
            }
        }
        return random.nextInt(4);
    }

    //Mutates one node on the current tree with "rate" chance 
    public void mutate(int rate){
        if(this.isFunction()) {
            int wayChoice = random.nextInt(2);
            if (wayChoice == 1){
                this.left.mutate(rate);
            }
            else {
                this.right.mutate(rate);
            }
        }
        else{
            int alter = random.nextInt(100);
            if (alter < rate) {
                int altered = random.nextInt(4);
                switch(altered){
                        case 0: {
                        this.value = "up";
                        break;
                    }
                        case 1: {
                        this.value = "right";
                        break;
                    }
                        case 2: {
                        this.value = "down";
                        break;
                    }
                        case 3: {
                        this.value = "left";
                        break;
                    }
                }
            }
        }
        
    }
    
    //Returns true if current node is a function
    public boolean isFunction(){
        return (!(this.value.equals("right")) && !(this.value.equals("left")) && !(this.value.equals("up")) && !(this.value.equals("down")));
    }
    
    //Not currently working
    public void crossover(int rate, Tree2048 t){
        int alter = random.nextInt(100);
        if(alter < rate){
            /*
            Tree2048 point1 = pickRandomNode(this,this);
            System.out.println("t1 chosen point: " + point1.value);
            Tree2048 point2 = pickRandomNode(t,t);
            System.out.println("t2 chosen point: " + point2.value);

            point1 = point2;
            System.out.println("point1 after changing: "+ point1.value);
            point2 = temp;
            */
            
            
           // pickRandomNode(this,this) = pickRandomNode(t,t);
        }
    }
    
    public Tree2048 pickRandomNode(Tree2048 selected, Tree2048 t){
        int choose = random.nextInt(2);
        int leftright = random.nextInt(2);
        if(choose == 1){
            if(leftright == 0 && t.right != null){
                selected = t;
                return pickRandomNode(selected,t.right);
            }
            if(leftright == 1 && t.left != null){
                selected = t;
                return pickRandomNode(selected,t.left);
            }
            return selected;
        }
        else{
           if(leftright == 0 && t.right != null){
                return pickRandomNode(selected,t.right);
            }
            if(leftright == 1 && t.left != null){
                return pickRandomNode(selected,t.left);
            }
            return selected;
        }
        
    }
    
    
    public int getFitness(){
        return (score/10 + maxTile);
    }
    

    public void postOrder() {
        // first recur on left subtree
        if(this.left != null){
            this.left.postOrder();
        }
        // then recur on right subtree
        if(this.right != null){
            this.right.postOrder();
        }
        // now deal with the node
        System.out.print(this.value + " ");  

    }
    
    //Visual representation of tree printed to console
    public void printTree()
    {
        printNode(0);
    }

    private void printNode(int indentation)
    {
        // Print the value to the console/file/whatever
        // This prefixes the value with the necessary amount of indentation
        char[] spaces = new char[indentation];
        Arrays.fill(spaces, ' ');
        System.out.println(new String(spaces) + this.value);

        
        if(this.left != null)
            this.left.printNode(indentation + 5); // Increment the indentation counter.
        if(this.right != null)
            this.right.printNode(indentation + 5); // Increment the indentation counter.
    }

}