package com.github.zipcodewilmington;

//Seth Prentice

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author xt0fer
 * @version 1.0.0
 * @date 5/27/21 11:02 AM
 */
public class Hangman {

    public static void main(String[] args){
        new Hangman().play();
    }

    public void play(){
        do{
            game();
        }while(playAgain());
        System.out.println("Thanks for playing!");
    }

    public void game(){
        String[] word = getWord().split("");
        String[] solved = new String[word.length];
        String guess;
        for(int i = 0; i<word.length; i++){
            solved[i] = "_";
        }
        int guesses = 11;
        System.out.println("You have eleven wrong guesses");
        while(guesses > 0){
            System.out.println("Current guesses remaining: " + guesses);
            showStatus(solved);
            guess = getGuess("What letter are you guessing?");
            solved = process(word, solved, guess);
            if(checkAns(solved)){
                showStatus(solved);
                System.out.println("Congratulations you won!");
                break;
            }

            guesses--;
        }
        if(guesses == 0){
            System.out.print("Sorry, you are out of guesses, the word was: ");
            showStatus(word);
        }

    }

    private void showStatus(String[] solved) {
        for(String s: solved){
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
    }

    private boolean checkAns(String[] solved) {
        for(String s : solved){
            if(s.equals("_")){
                return false;
            }
        }
        return true;
    }

    private String[] process(String[] word, String[] solved, String guess) {
        for(int i = 0; i<word.length; i++){
            if(word[i].equals(guess)){
                solved[i] = guess;
            }
        }
        return solved;
    }

    public String getWord(){
        try {
            return Files.readAllLines(Paths.get("src/main/java/com/github/zipcodewilmington/words.txt")).get(getRand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGuess(String prompt){
        Scanner in = new Scanner(System.in);
        System.out.println(prompt);
        return String.valueOf(in.nextLine().toLowerCase().charAt(0));
    }

    public int getRand(){
        return (int) (Math.random() * 50);
    }

    /**
     * prompts the user if they want to play again
     * @return boolean of play again
     */
    public boolean playAgain(){
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to play again Y/N?");
        return in.nextLine().toLowerCase().charAt(0) == 'y';
    }
}
