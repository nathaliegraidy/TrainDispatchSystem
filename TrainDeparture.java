package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a train departure.
 *
 * @author Nathalie Graidy Andreassen
 */

public class TrainDeparture {

  /**
   * The departure time of the train departure.
   */
  private final LocalTime departureTime;
  /**
   * The line of the train departure.
   */
  private final String line;
  /**
   * The train number of the train departure.
   */
  private final int trainNumber;
  /**
   * The destination of the train departure.
   */
  private final String destination;
  /**
   * The track of the train departure.
   */
  private int track;
  /**
   * The delay of the train departure.
   */
  private Duration delay;

  /**
   * Constructs a Traindeparture object.
   *
   * @param departureTime the time of departure.
   * @param line the line or route of the traindeparture.
   * @param trainNumber the identification number of the traindeparture.
   * @param destination the destination of the traindeparture.
   * @param track the track number of the traindeparture.
   * @param delay the delay in minutes (if any).
   *
   * @throws IllegalArgumentException if the train number is negative.
   * @throws IllegalArgumentException if the track number is less than -1
   *                               -1 represents that the train has no assigned track.
   * @throws IllegalArgumentException if the delay is not within the range of 0 to 60 minutes.
   * @throws IllegalArgumentException if destination is empty.
   * @throws IllegalArgumentException if line is empty.
   */
  public TrainDeparture(
      LocalTime departureTime,
      String line,
      int trainNumber,
      String destination,
      int track,
      Duration delay) {

    if (trainNumber < 0) {
      throw new IllegalArgumentException("Train number cannot be negative");
    }

    if (destination.isEmpty()) {
      throw new IllegalArgumentException("Destination can not be empty");
    }

    if (line.isEmpty()) {
      throw new IllegalArgumentException("Line can not be empty");
    }

    this.departureTime = departureTime;
    this.line = line;
    this.trainNumber = trainNumber;
    this.destination = destination;
    setTrack(track);
    setDelay(delay);
  }

  // TODO Kan lage en deep copy class

  /**
   * Gets the departure time of the traindeparture.
   *
   * @return the departure time
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Gets the line or route of the traindeparture.
   *
   * @return the line or route
   */
  public String getLine() {
    return line;
  }

  /**
   * Gets the identification number of the traindeparture.
   *
   * @return the train number
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Gets the destination of the traindeparture.
   *
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Gets the track of the traindeparture.
   *
   * @return the track
   */
  public int getTrack() {
    return track;
  }

  /**
   * Gets the delay of the traindeparture.
   *
   * @return the delay in minutes
   */
  public Duration getDelay() {
    return delay;
  }


  /**
   * Sets the track number of the train.
   *
   * @param track the track number
   */
  public void setTrack(int track) {
    if (track < -1 || track == 0) {
      throw new IllegalArgumentException("Track can only be a positive number and -1,"
          + " since -1 represents that the train has no track.");
    }
    this.track = track;
  }

  /**
   * Laget en variabel for 24 timer, og bruker denne i setDelay siden det er usannsynlig at ett tog
   * med så store forsinkelser ikke blir innstilt.
   */
  private static final int minutesInDay = 60 * 24;

  /**
   * Sets the delay in minutes for the train departure.
   *
   * @param delay the delay in minutes
   */
  public void setDelay(Duration delay) {
    if (delay.toMinutes() > minutesInDay) {
      throw new IllegalArgumentException("Delay can not be more than 24 hours");
    }
    this.delay = delay;
  }

  /**
   * ToString - Lager en ferdig formattert tekst som printer ut alle
   * objektvariablene til et objekt på en bestemt måte.
   *
   * @return (String)
   */

  @Override
  public String toString() {
    String delay = Long.toString(getDelay().toMinutes());
    String track = Integer.toString(getTrack());

    int minutesDelay = (int) (getDelay().toMinutes() % 60);
    int hoursDelay = (int) (getDelay().toMinutes() / 60);

    if (getDelay().toMinutes() == 0) {
      delay = "";
    } else {
      if (getDelay().toMinutes() > 59) {
        delay = hoursDelay + " h " + minutesDelay + " m";
      } else {
        delay = delay + " m";
      }
    }

    if (getTrack() == -1) {
      track = "";
    }

    return String.format("%-20s%-10s%-17s%-15s%-10s%-10s",
        getDepartureTime(),
        getLine(),
        getTrainNumber(),
        getDestination(),
        track,
        delay
        );
  }
}


