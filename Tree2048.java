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
                return left.evaluate() + right.evaluate();
            }
            case "-":{
                return left.evaluate() - right.evaluate();
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
        return random.nextInt(3);
        }
    
    public String printTree() {
        switch(this.value) {
            case "+":{
                return left.printTree() + "+" + right.printTree();
            }
            case "-":{
                return left.printTree() + "-" + right.printTree();
            }  
            case "max":{
                return left.printTree() + "max" + right.printTree();
            }  
            case "min":{
                return left.printTree() + "min" + right.printTree();
            }  
            case "rand":{
                return left.printTree() + "rand" + right.printTree();
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