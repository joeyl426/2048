import java.util.*;

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

    public Tree2048 (String value, Tree2048 left, Tree2048 right) 
    {
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

    // Getter & setter for the value.
    public String getValue(){
        return value;}
    public void setValue(String value){
        this.value = value;}

    // Getters & setters for left & right nodes.
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

    public void mutate(int rate){
        if(this.isFunction()) {
            int wayChoice = random.nextInt(2);
            if (wayChoice == 1){
                this.left.mutate(rate);
            }
            else {
                this.left.mutate(rate);
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
    
    public boolean isFunction(){
        return (!(this.value.equals("right")) && !(this.value.equals("left")) && !(this.value.equals("up")) && !(this.value.equals("down")));
    }
    
    
    private void crossover(Tree2048 t, int rate){
        if(this.isFunction()) {
            int wayChoice = random.nextInt(2);
            if (wayChoice == 1){
                this.left.mutate(rate);
            }
            else {
                this.left.mutate(rate);
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
    
    
    public int maxDepth(Tree2048 t) 
    {
        if (t == null)
            return 0;
        else
        {
            /* compute the depth of each subtree */
            int lDepth = maxDepth(t.left);
            int rDepth = maxDepth(t.right);
  
            /* use the larger one */
            if (lDepth > rDepth)
                return (lDepth + 1);
             else
                return (rDepth + 1);
        }
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