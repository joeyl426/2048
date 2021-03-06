/*
 * Copyright 1998-2014 Konstantin Bulenkov http://bulenkov.com/about
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.bulenkov.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Konstantin Bulenkov
 */

public class Game{
    
    private static Tile[] myTiles;
    boolean myWin = false;
    boolean myLose = false;
    int myScore = 0;
    Random rand = new Random();

    public Game() {
        resetGame();  
    }

    public void randomMove() {
        while(!myLose){    
            if (!canMove()) {
                myLose = true;
            }
            if(!myLose){
                int move = rand.nextInt(4);
                switch(move){
                    case 0:
                        up();
                        break;
                    case 1:
                        right();
                        break;
                    case 2:
                        down();
                        break;
                    case 3:
                        left();
                        break;

                }
            }

            if (!myWin && !canMove()) {
                myLose = true;
            }
        }
    }
    
    public void TreeMove(Tree t) {
        int prevMove = 0;
        int prevScore = 0;
        int sameMoveCounter = 0;
        while(!myLose){
            if(sameMoveCounter >= 200 && myScore == prevScore)
                myLose = true;
            if (!canMove()) {
                myLose = true;
            }
            if(!myLose){
                //System.out.println("gothere");
                int move = t.evaluate();
                
                //System.out.println(move);
                if(prevMove == move && prevScore == myScore){
                    sameMoveCounter++;
                }
                else{
                    sameMoveCounter = 0;
                }
                prevMove = move;
                switch(move){
                    case 0:
                        up();
                        break;
                    case 1:
                        right();
                        break;
                    case 2:
                        down();
                        break;
                    case 3:
                        left();
                        break;

                }
            }

            if (!myWin && !canMove()) {
                myLose = true;
                
            }
            prevScore = myScore;
        }
    }
    
    

    public void resetGame() {
        myScore = 0;
        myWin = false;
        myLose = false;
        myTiles = new Tile[4 * 4];
        for (int i = 0; i < myTiles.length; i++) {
            myTiles[i] = new Tile();
        }
        addTile();
        addTile();
    }

    public void left() {
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }

        if (needAddTile) {
            addTile();
        }
    }

    public void right() {
        myTiles = rotate(180);
        left();
        myTiles = rotate(180);
    }

    public void up() {
        myTiles = rotate(270);
        left();
        myTiles = rotate(90);
    }

    public void down() {
        myTiles = rotate(90);
        left();
        myTiles = rotate(270);
    }

    private static Tile tileAt(int x, int y) {
        return myTiles[x + y * 4];
    }

    private void addTile() {
        List<Tile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTime = list.get(index);
            emptyTime.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<Tile>(16);
        for (Tile t : myTiles) {
            if (t.isEmpty()) {
                list.add(t);
            }
        }
        return list;
    }

    private boolean isFull() {
        return availableSpace().size() == 0;
    }

    boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = tileAt(x, y);
                if ((x < 3 && t.value == tileAt(x + 1, y).value)
                    || ((y < 3) && t.value == tileAt(x, y + 1).value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compare(Tile[] line1, Tile[] line2) {
        if (line1 == line2) {
            return true;
        } else if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (line1[i].value != line2[i].value) {
                return false;
            }
        }
        return true;
    }

    private static Tile[] rotate(int angle) {
        Tile[] newTiles = new Tile[4 * 4];
        int offsetX = 3, offsetY = 3;
        if (angle == 90) {
            offsetY = 0;
        } else if (angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * 4] = tileAt(x, y);
            }
        }
        return newTiles;
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<Tile>();
        for (int i = 0; i < 4; i++) {
            if (!oldLine[i].isEmpty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].value;
            if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
                num *= 2;
                myScore += num;
                int ourTarget = 2048;
                if (num == ourTarget) {
                    myWin = true;
                }
                i++;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {
            return oldLine;
        } else {
            ensureSize(list, 4);
            return list.toArray(new Tile[4]);
        }
    }

    private static void ensureSize(java.util.List<Tile> l, int s) {
        while (l.size() != s) {
            l.add(new Tile());
        }
    }

    private static Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(i, index);
        }
        return result;
    }

    private void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, myTiles, index * 4, 4);
    }
    
    
    /*We will be doing everything by sensing to the left. For each sense, we will rotate properly, then check sense, then rotate the board back */
    private static int sense(int moveVal) {
        for (int i=0; i<4; i++){
            Tile[] line = getLine(i);
            if (lineSense(line) == true) {
                return moveVal;
            }
        }
        Random randMove = new Random();
        int move = randMove.nextInt(4);
        return move;
    }
    /*Returns true if anything can be combined in any 4 of the lines */ 
    private static boolean lineSense(Tile[] oldLine){
        for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].value;
            if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
                return true;
            }
        }
        return false;
    }
    
    public static int senseLeft() {
        return sense(3);
    }
    
    public static int senseRight() {
        myTiles = rotate(180);
        int senseVal = sense(1);
        myTiles = rotate(180);
        return senseVal;
    }
    
    public static int senseUp() {
        myTiles = rotate(270);
        int senseVal = sense(0);
        myTiles = rotate(90);
        return senseVal;
    }
    
    public static int senseDown() {
        myTiles = rotate(90);
        int senseVal = sense(2);
        myTiles = rotate(270);
        return senseVal;
    }

        
    public int getMyScore() {
        return myScore;
    }
    
    public int getMaxTile(){
        int maxtile = 2;
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++) {
                if (tileAt(i,j).value > maxtile) {
                    maxtile = tileAt(i,j).value;
                }
            }
        }
        return maxtile;
    }

    static class Tile {
        int value;

        public Tile() {
            this(0);
        }

        public Tile(int num) {
            value = num;
        }

        public boolean isEmpty() {
            return value == 0;
        }

        public Color getForeground() {
            return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
        }

        public Color getBackground() {
            switch (value) {
                case 2:    return new Color(0xeee4da);
                case 4:    return new Color(0xede0c8);
                case 8:    return new Color(0xf2b179);
                case 16:   return new Color(0xf59563);
                case 32:   return new Color(0xf67c5f);
                case 64:   return new Color(0xf65e3b);
                case 128:  return new Color(0xedcf72);
                case 256:  return new Color(0xedcc61);
                case 512:  return new Color(0xedc850);
                case 1024: return new Color(0xedc53f);
                case 2048: return new Color(0xedc22e);
            }
            return new Color(0xcdc1b4);
        }
    }
    
    public Tree[] runGeneration(Tree[] pop, Game game, int numGames) {
        for (int i = 0; i< pop.length; i++){
            int sumScore = 0;
            int sumMaxTile = 0;
            for(int j = 0; j < numGames; j++){
                game.TreeMove(pop[i]);
                sumScore += myScore;
                sumMaxTile += game.getMaxTile();
                game.resetGame();
            }
            pop[i].setScore(sumScore/numGames);
            pop[i].setMaxTile(sumMaxTile/numGames);
        }
        return pop;
    }
    

    public static void main(String[] args) {
        int avgBestScore = 0;
        int avgBestTile = 0;
        Breeder breeder = new Breeder();
        //10 runs of the genetic program, with a new initial population each time
        //Average score and max tile of the 10 runs is printed at the end
        for(int j = 0; j < 10; j++){  
           
            Tree[] population = breeder.createPopulation();      
            Tree bestOfRun = new Tree("");

            System.out.println("Initial pop: \n");
            breeder.printPop(population);

            Game Game = new Game();
                  
            for(int i = 0; i < 20; i++){
                //Breed a new population each generation
                population = Game.runGeneration(population,Game, 7);
                population = breeder.breed(population);

                //Print out results of each generation
                System.out.println("\n\n Run " + (j+1) + ", Generation " + (i + 1) + "\n---------------");
                System.out.print("Best individual:\n");
                Tree best = (Tree)breeder.getBest(population).clone();
                //Track best individual so far
                if(bestOfRun.getScore() < best.getScore()){
                    bestOfRun = (Tree)best.clone();
                }
                best.printTree();
                System.out.println("\nScore: " + best.getScore());
                System.out.println("Best tile: " + best.getMaxTile() + "\n");
            }

            //Print out results of run
            System.out.println("Final pop: \n");
            breeder.printPop(population);
            System.out.println("Best individual of run: ");
            bestOfRun.printTree();
            System.out.println("Score: " + bestOfRun.getScore());
            avgBestScore += bestOfRun.getScore();
            System.out.println("\nMax Tile: " + bestOfRun.getMaxTile());
            avgBestTile += bestOfRun.getMaxTile();
        }
        
        System.out.println("\nAvg best score of run: " + avgBestScore/10);
        System.out.println("\nAvg max tile of run: " + avgBestTile/10);
      }
}
