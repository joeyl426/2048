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
                if (random.nextInt(9) < 6) {
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
        return random.nextInt(3);
        }
    
    public void mutate(){
        System.out.println(this.value);
        if ((this.value != "right") || (this.value != "left") || (this.value != "up") || (this.value != "down")) {
            int wayChoice = random.nextInt(1);
            if (wayChoice == 1){
                left.mutate();
            }
            else {
                right.mutate();
            }
        }
        else{
            int alter = random.nextInt(99);
            if (alter < 5) {
                int altered = random.nextInt(3);
                switch(altered){
                    case 0: {
                        this.value = "up";
                    }
                    case 1: {
                        this.value = "right";
                    }
                    case 2: {
                        this.value = "down";
                    }
                    case 3: {
                        this.value = "left";
                    }
                }
            }
        }
    }
    
    public void inOrder(Tree2048 root) {
      if(root !=  null) {
           inOrder(root.left);
           //Visit the node by Printing the node data  
           System.out.print(root.value + " ");
           inOrder(root.right);
      }
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