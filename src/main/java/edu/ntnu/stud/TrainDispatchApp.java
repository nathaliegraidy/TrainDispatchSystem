package edu.ntnu.stud;

/**
 * This is the main class for the train dispatch application, with the main method.
 *
 * @author Nathalie Graidy Andreassen
 */

public class TrainDispatchApp {
  /**
   * The main entry point of the Train Departure Management System.
   * Initializes the User Interface (UI) and starts the system by calling the {@code init()} and
   * {@code start()} methods.
   */

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}

