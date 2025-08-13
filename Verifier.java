package edu.ntnu.stud;

import java.util.Scanner;

/**
 * Verifies the menu choices.
 *
 * @author Nathalie Graidy Andreassen
 */

public class Verifier {

  private static final Scanner scan = new Scanner(System.in);


  /**
   * Checks if the menu choice from the user input is valid.
   *
   * @return menu choice
   */
  public static int validateMenuChoice() {
    while (!scan.hasNextInt()) {
      System.out.print("Put a number that represents the choices above: ");
      scan.nextLine();
    }

    int menuChoice = scan.nextInt();
    scan.nextLine();
    return menuChoice;
  }
}

