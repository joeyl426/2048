import java.util.*;

public class Tree2048{
   String value;
   Tree2048 left, right;
    Random random = new Random();

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

   // Getter & setter for the value.
   public String getValue(){
      return value;}
   public void setValue(String value){
      this.value = value;}

   // Getters & setters for left & right nodes.
   public Tree2048 getLeft(){
      return left;}
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
//                return Game2048.senseRight();
                return 1;
            }
            case "left":{
//                return Game2048.senseLeft();
                return 3;
            }
            case "up":{
//                return Game2048.senseUp();
                return 0;
            }
            case "down":{
//                return Game2048.senseDown();
                return 2;
            }
            }
        return random.nextInt(4);
        }
    
    public void mutate(Tree2048 t){
        System.out.println("\nCurrent: " + t.value + "\n");
        if(!(t.value.equals("right")) && !(t.value.equals("left")) && !(t.value.equals("up")) && !(t.value.equals("down"))) {
           System.out.println("Got into if statement: " + t.value);
            int wayChoice = random.nextInt(2);
            if (wayChoice == 1){
                mutate(t.left);
            }
            else {
                mutate(t.right);
            }
        }
        else{
            int alter = random.nextInt(100);
            if (alter < 100) {
                int altered = random.nextInt(4);
                switch(altered){
                    case 0: {
                        t.value = "up";
                        return;
                    }
                    case 1: {
                        t.value = "right";
                        return;
                    }
                    case 2: {
                        t.value = "down";
                        return;
                    }
                    case 3: {
                        t.value = "left";
                        return;
                    }
                }
            }
        }
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