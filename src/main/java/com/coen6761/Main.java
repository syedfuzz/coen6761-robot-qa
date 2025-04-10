package com.coen6761;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProgramActions robotActions = new ProgramActions();

        System.out.println("Welcome to Robot Game!");
        System.out.println("Enter command to initialise Robot");
        String command = sc.nextLine();
        
        while(!command.equals("Q") && !command.equals("q")){
            robotActions.actions(command);
            System.out.println("\nEnter next command");
            command = sc.nextLine();
        }
        
        System.out.println("Games stopped");
        sc.close();
    }
}