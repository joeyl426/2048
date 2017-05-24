import java.util.*;

public class Tree2048 implements Cloneable{
   String value;
   Tree2048 left, right;
   Random random = new Random();
   int score;

   public Tree2048(String value)
   {
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
    
    public Tree2048 mutate(Tree2048 t, int rate){
        if(!(t.value.equals("right")) && !(t.value.equals("left")) && !(t.value.equals("up")) && !(t.value.equals("down"))) {
            int wayChoice = random.nextInt(2);
            if (wayChoice == 1){
                mutate(t.left,rate);
            }
            else {
                mutate(t.right,rate);
            }
        }
        else{
            int alter = random.nextInt(100);
            if (alter < rate) {
                int altered = random.nextInt(4);
                switch(altered){
                    case 0: {
                        t.value = "up";
                        return t;
                    }
                    case 1: {
                        t.value = "right";
                        return t;
                    }
                    case 2: {
                        t.value = "down";
                        return t;
                    }
                    case 3: {
                        t.value = "left";
                        return t;
                    }
                }
            }
        }
        return t;
    }
    
    public void postOrder(Tree2048 root) {
      
           if (root == null)
            return;
 
        // first recur on left subtree
        postOrder(root.left);
 
        // then recur on right subtree
        postOrder(root.right);
 
        // now deal with the node
        System.out.print(root.value + " ");  
      
    }
    
    public String printTree() {
        switch(this.value) {
            case "+":{
                return left.printTree() + " " + "+" + " " + right.printTree();
            }
            case "-":{
                return left.printTree() + " " + "-" + " " + right.printTree();
            }  
            case "max":{
                return left.printTree() + " " + "max" + " " + right.printTree();
            }  
            case "min":{
                return left.printTree() + " " + "min" + " " + right.printTree();
            }  
            case "rand":{
                return left.printTree() + " " + "rand" + " " + right.printTree();
            }
            case "right": {
                return "right";
            }
            case "left": {
                return "left";
            }
            case "up":{
                return "up";
            }
            case "down":{
                return "down";
            }
        }
        return "null";
    }
    
    
    }