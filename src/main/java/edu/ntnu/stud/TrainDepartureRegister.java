package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


/**
 * A register to manage train departure.
 *
 * @author Nathalie Graidy Andreassen
 */

public class TrainDepartureRegister {

  /**
   * The hashmap tha contains all the train departures, user the train number as the key,
   * and the train departure as the object.
   */

  private final HashMap<Integer, TrainDeparture> trainDepartures;

  /**
   * The time of day set by the user.
   */

  private LocalTime time;

  /**
   * Constructs a train departure register.
   *
   * @param trainDepartures hashmap to contain all the train departures
   * @param time the time set by the user
   */

  public TrainDepartureRegister(HashMap<Integer, TrainDeparture> trainDepartures,
      LocalTime time) {
    this.trainDepartures = trainDepartures;
    this.time = time;
  }

  /**
   * Gets the current time.
   *
   * @return time
   */

  public LocalTime getTime() {
    return time;
  }

  /**
   * Sets the time of day.
   *
   * @param newTime the new time et by the user input
   * @return new time
   */

  public LocalTime setTime(LocalTime newTime) {
    if (newTime.isAfter(time)) {
      this.time = newTime;
    }
    return time;
  }


  /**
   * Gets alle the train departures.
   *
   * @return all train departures.
   *
   */

  public HashMap<Integer, TrainDeparture> getTrainDepartures() {
    return trainDepartures;
  }

  /**
   * Method to add a train departure, to the train departures hashmap.
   *
   * @param newTrainNumber the new train number
   * @param trainDeparture the new train departure
   */

  public void addTrainDeparture(int newTrainNumber,
      TrainDeparture trainDeparture) {
    trainDepartures.put(newTrainNumber, trainDeparture);
  }

  /**
   * Method to search for a train departure, by the train departures train number.
   *
   * @param trainNumber the train number of the train
   * @return the train departure searched for
   */

  public TrainDeparture searchByTrainNumber(int trainNumber) {
    return trainDepartures.get(trainNumber);
  }

  /**
   * Sets the track of a specific train departure based on the train number.
   *
   * @param trainNumber train number of the train departure
   * @param track new track for the train departure
   * @return train departure with the new/changed track
   */

  public TrainDeparture setTrack(int trainNumber, int track) {
    if (!trainDepartures.containsKey(trainNumber)) {
      return null;
    }

    trainDepartures.get(trainNumber).setTrack(track);
    return trainDepartures.get(trainNumber);
  }

  /**
   * Sets the delay of a specific train departure based on the train number.
   *
   * @param trainNumber train number of the train departure
   * @param delay new delay for the train departure
   * @return train departure with the new/changed delay
   */

  public TrainDeparture addDelay(int trainNumber, Duration delay) {
    if (!trainDepartures.containsKey(trainNumber)) {
      return null;
    }
    trainDepartures.get(trainNumber).setDelay(delay);
    return trainDepartures.get(trainNumber);
  }

  /**
   * Method to search for all train departures by the destination.
   *
   * @param destination the destination of the train departure
   * @return a new hashmap with all the train departure leaving from the destination
   */

  public HashMap<Integer, TrainDeparture> searchTrainByDestination(String destination) {
    HashMap<Integer, TrainDeparture> foundTrainDepartures = new HashMap<>();
    trainDepartures.forEach((key, value) -> {
      if (value.getDestination().equalsIgnoreCase(destination)) {
        foundTrainDepartures.put(key, value);
      }
    });

    return foundTrainDepartures;
  }

  /**
   * removes a train departure from the hashmap train departures.
   *
   * @param trainNumber train number of the train departure
   */

  public void removeTrainDeparture(int trainNumber) {
    trainDepartures.remove(trainNumber);
  }

  /**
   * Method to sort the train departure by the departure time in chronological order.
   *
   * @param trainDeparture the hashmap that is to be sorted
   * @return arraylist with the sorted train departures
   */

  public ArrayList<TrainDeparture> sort(HashMap<Integer, TrainDeparture> trainDeparture) {
    var outlist = trainDeparture.values().stream().sorted(
        Comparator.comparing(TrainDeparture::getDepartureTime).thenComparing(
            TrainDeparture::getDestination)
    ).toList();
    return new ArrayList<>(outlist);
  }


  /**
   * Method to remove all the train departures before the set time.
   */


  public void removeTrainsThatHaveLeftTheStation() {
    ArrayList<Integer> keysToRemove = new ArrayList<>();
    trainDepartures.forEach(
        (key, value) -> {
          if (value.getDepartureTime().plusMinutes(value.getDelay().toMinutes()).isBefore(getTime())
          ) {
            keysToRemove.add(key);
          }
        }
    );

    keysToRemove.forEach(trainDepartures::remove); // removes all the departures before the time

  }

  /**
   * Method to exit the system.
   */

  public void systemExit() {
    System.exit(0);
  }
}




