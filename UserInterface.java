package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * User interface for train departure register.
 *
 * @author Nathalie Graidy Andreassen
 */

public class UserInterface {
  Scanner read = new Scanner(System.in);
  static final int VIEW_TRAIN_DEPARTURES = 1;
  static final int ADD_TRAIN_DEPARTURE = 2;
  static final int SEARCH_AND_UPDATE_TRAIN_DEPARTURE_BY_TRAIN_NUMBER = 3;
  static final int SEARCH_TRAIN_DEPARTURE_BY_DESTINATION = 4;
  static final int VIEW_TIME = 5;
  static final int SET_NEW_TIME = 6;
  static final int EXIT = 7;

  //UPDATE TRAIN DEPARTURE MENU
  static final int SET_TRACK = 1;
  static final int ADD_DELAY = 2;
  static final int REMOVE_TRAIN_DEPARTURE = 3;
  static final int GO_BACK_TO_MAIN_MENU = 4;

  LocalTime time = LocalTime.of(0, 0);
  public static HashMap<Integer, TrainDeparture> trainDepartures = new HashMap<>();
  TrainDepartureRegister trainDepartureRegister;

  /**
   * Initializes the train departure register and sets up the user interface.
  */
  public void init() {
    TrainDepartureRegister trainDepartureRegister =
        new TrainDepartureRegister(trainDepartures, time);
    UserInterface ui = new UserInterface();
  }

  /**
   * Starts the user interface by initializing sample train departures and launching the main menu.
   */
  public void start() {
    try {
      TrainDeparture train1 = new TrainDeparture(LocalTime.of(15, 30),
          "F6", 1, "Oslo", -1, Duration.ofMinutes(0));
      TrainDeparture train2 = new TrainDeparture(LocalTime.of(12, 0),
          "F6", 2, "Oslo", -1, Duration.ofMinutes(0));
      TrainDeparture train3 = new TrainDeparture(LocalTime.of(14, 0),
          "L2", 3, "Bergen", 3, Duration.ofMinutes(5));
      TrainDeparture train4 = new TrainDeparture(LocalTime.of(10, 30),
          "E4", 4, "Hamar", 1, Duration.ofMinutes(15));
      TrainDeparture train5 = new TrainDeparture(LocalTime.of(10, 0),
          "L8", 5, "Oslo", 2, Duration.ofMinutes(10));

      trainDepartures.put(1, train1);
      trainDepartures.put(2, train2);
      trainDepartures.put(3, train3);
      trainDepartures.put(4, train4);
      trainDepartures.put(5, train5);
      trainDepartureRegister = new TrainDepartureRegister(trainDepartures, time);

    } catch (IllegalArgumentException e) {
      System.out.print(e.getMessage());
    }
    mainMenu();
  }

  /**
   * Displays the main menu options and handles user input to execute corresponding functionalities.
   */
  public void mainMenu() {
    //noinspection InfiniteLoopStatement
    while (true) {
      System.out.println("----------MENU---------");
      System.out.println("""
          [1] View train departures
          [2] Add train departure
          [3] Search for-, and update, train departure by train-number
          [4] Search train departure by destination
          [5] View time
          [6] Set new time
          [7] Exit
          """);

      int menuChoice = Verifier.validateMenuChoice();

      if (menuChoice > 0 && menuChoice < 9) {
        switch (menuChoice) {
          case VIEW_TRAIN_DEPARTURES:
            viewTrainDeparture();
            break;
          case ADD_TRAIN_DEPARTURE:

            uiHandleAddTrainDeparture();
            break;
          case SEARCH_AND_UPDATE_TRAIN_DEPARTURE_BY_TRAIN_NUMBER:
            int foundTrain = uiSearchTrainDepartureByTrainNumber();
            editTrainDepartureMenu(foundTrain);

            break;
          case SEARCH_TRAIN_DEPARTURE_BY_DESTINATION:
            uiSearchTrainByDestination();
            break;
          case VIEW_TIME:
            uiViewTime();
            break;
          case SET_NEW_TIME:
            uiHandleSetTime();
            break;
          case EXIT:
            trainDepartureRegister.systemExit();
            break;
          default:

        }
      } else {
        System.out.println("Not a valid menu choice, put a number between 1 and 8: ");
      }
    }
  }

  /**
   * Displays a submenu to update specific train departures based on the train number.
   *
   * @param foundTrain the train number of the train to be edited
   */

  public void editTrainDepartureMenu(int foundTrain) {

    //noinspection InfiniteLoopStatement
    while (true) {
      System.out.println("---------UPDATE TRAIN DEPARTURE MENU---------");
      System.out.println("""
              [1] Set track
              [2] Add delay
              [3] Remove train departure
              [4] Go back to main menu
              """);

      int editChoice = Verifier.validateMenuChoice();

      if (editChoice > 0 && editChoice < 5) {
        switch (editChoice) {
          case SET_TRACK:
            uiSetTrack(foundTrain);
            break;
          case ADD_DELAY:
            uiAddDelay(foundTrain);
            break;
          case REMOVE_TRAIN_DEPARTURE:
            uiRemoveTrainDeparture(foundTrain);
            mainMenu();
            break;
          case GO_BACK_TO_MAIN_MENU:
            mainMenu();
            break;
          default:
        }
      } else {
        System.out.println("Not a valid menu choice, put a number between 1 and 4: ");
      }
    }
  }

  /**
   * Method to print the train departures hashmap.
   */

  public void viewTrainDeparture() {

    System.out.printf("%-20s%-10s%-17s%-15s%-10s%-10s%n", "Departure time", "Line",
        "Destination", "Train number", "Track", "Delay");

    ArrayList<TrainDeparture> sortedTrainDep = trainDepartureRegister.sort(
        trainDepartureRegister.getTrainDepartures());
    for (TrainDeparture t : sortedTrainDep) {
      System.out.println(t);
    }
  }

  /**
   * Method add train departure with the user interface. Also checks that the user input is valid.
   */

  public void uiHandleAddTrainDeparture() {

    LocalTime inputDepartureTime = null;
    while (inputDepartureTime == null) {
      try {
        System.out.println("Add the departure time of the train, in this format 00:00");
        inputDepartureTime = LocalTime.parse(read.nextLine());
      } catch (Exception e) {
        System.out.println("""
            Departure time can not be empty, has to be written in the right format
            ,and has to represent an actual time f.eks. can not be 24:60
            """);
      }
    }

    String inputLine = null;
    while (inputLine == null || inputLine.isEmpty()) {
      System.out.println("Add the train line:");
      inputLine = read.nextLine();
      if (inputLine == null || inputLine.isEmpty()) {
        System.out.println("Train line can not be empty\n");
      }
    }

    int inputTrainNumber = -1;
    while (inputTrainNumber <= -1) {
      try {
        System.out.println("Add the train number:");
        inputTrainNumber = Integer.parseInt(read.nextLine());
      } catch (Exception e) {
        System.out.println("Train number can not be text, empty, or already exist\n");
      }
    }

    String inputDestination = null;
    while (inputDestination == null || inputDestination.isEmpty()) {
      System.out.println("Add the train destination:");
      inputDestination = read.nextLine();
      if (inputDestination == null || inputDestination.isEmpty()) {
        System.out.println("Destination can not be empty\n");
      }
    }

    int inputTrack = -2;
    while (inputTrack < -1) {
      try {
        System.out.println("Add the track if none add (-1):");
        inputTrack = Integer.parseInt(read.nextLine());
      } catch (Exception e) {
        System.out.println("Train track can not be empty, text, less den -1 or 0\n");
      }
    }

    Duration inputDelay = Duration.ofMinutes(-1);
    while (inputDelay.toMinutes() < 0) {
      try {
        System.out.println("Add the delay if any:");
        inputDelay = Duration.ofMinutes(Long.parseLong(read.nextLine()));
      } catch (Exception e) {
        System.out.println("Train delay kan not be empty, text or a negative number, if there is "
            + "no delay add 0\n");
      }
    }

    TrainDeparture inputTrain = new TrainDeparture(inputDepartureTime,
        inputLine, inputTrainNumber, inputDestination, inputTrack, inputDelay);
    if (trainDepartures.containsKey(inputTrainNumber)) {
      System.out.println("There is already a train departure with this train number, every train "
          + "departure needs a different train number.");
    } else {
      trainDepartureRegister.addTrainDeparture(inputTrainNumber, inputTrain);
      viewTrainDeparture();
    }
    trainDepartureRegister.removeTrainsThatHaveLeftTheStation();
  }

  /**
   * Searches for a train departure based on the provided train number entered by the user.
   *
   * @return the train number being searched for
   */
  public int uiSearchTrainDepartureByTrainNumber() {

    System.out.println("Add the train number of the train departure you are searching for:");
    int searchTrainNumber = Integer.parseInt(read.nextLine());

    if (trainDepartureRegister.searchByTrainNumber(searchTrainNumber) == null) {
      System.out.println("This train departure does not exist.\n");
      mainMenu();
    } else {
      System.out.println(trainDepartureRegister.searchByTrainNumber(searchTrainNumber));
    }
    return searchTrainNumber;
  }

  /**
   * Handles user input to set the track for a specific train departure.
   *
   * @param foundTrain the train number of the train departure
   */
  public void uiSetTrack(int foundTrain) {

    System.out.println("Add track of the train departure:");
    int newTrack = Integer.parseInt(read.nextLine());

    System.out.println(trainDepartureRegister.setTrack(foundTrain, newTrack));
  }

  /**
   * Handles user input to add a delay for a specific train departure.
   *
   * @param foundTrain the train number of the train departure
   */
  public void uiAddDelay(int foundTrain) {
    System.out.println("Add delay of the train departure in minutes:");
    int newDelayMinutes = Integer.parseInt(read.nextLine());
    Duration newDelay = Duration.ofMinutes(newDelayMinutes);


    System.out.println(trainDepartureRegister.addDelay(foundTrain, newDelay));
  }

  /**
   * Removes a train departure based on the provided train number entered by the user.
   *
   * @param foundTrain the train number of the train departure to be removed
   */
  public void uiRemoveTrainDeparture(int foundTrain) {
    trainDepartureRegister.removeTrainDeparture(foundTrain);
    viewTrainDeparture();
  }

  /**
   * Searches for train departures based on the provided destination entered by the user.
   */
  public void uiSearchTrainByDestination() {
    System.out.println("Add the destination you are searching for");
    String searchDestination = read.nextLine();

    ArrayList<TrainDeparture> sortedTrainDep = trainDepartureRegister.sort(
        trainDepartureRegister.searchTrainByDestination(searchDestination));

    if (sortedTrainDep.isEmpty()) {
      System.out.println("Found no train departures with destination: " + searchDestination);
    } else {
      for (TrainDeparture t : sortedTrainDep) {
        System.out.println(t);
      }
    }
  }

  /**
   * Displays the current time.
   */
  public void uiViewTime() {
    System.out.println(trainDepartureRegister.getTime());

  }

  /**
   * Handles user input to set a new time and updates the train departures accordingly.
   */

  public void uiHandleSetTime() {

    LocalTime newTime = null;
    while (newTime == null) {
      try {
        System.out.println("Add the time in this format 00:00 of the new time");
        newTime = LocalTime.parse(read.nextLine());

        if (newTime.isAfter(time)) {
          System.out.println(trainDepartureRegister.setTime(newTime) + "\n");
          trainDepartureRegister.removeTrainsThatHaveLeftTheStation();
          viewTrainDeparture();
        } else {
          System.out.println("New time has to be after the current time");
        }
      } catch (Exception e) {
        System.out.println("New time is not valid");
      }
    }
  }
}