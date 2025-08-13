package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the TrainDeparture class.
 *
 * @author Nathalie Graidy Andreassen
 */

public class TrainDepartureTest {

  int initialHour = 10;
  int initialMinute = 30;
  String initialLine = "F3";
  int initialTrainNumber = 1;
  String initialDestination = "Oslo";
  int initialTrack = -1;
  Duration initialDelay = Duration.ZERO;

  TrainDeparture train1 = new TrainDeparture
      (LocalTime.of(initialHour,initialMinute),initialLine, initialTrainNumber,initialDestination,
          initialTrack, initialDelay);

  @Test
  void setTrack() {
    int newTrack = 6;
    train1.setTrack(newTrack);
    Assertions.assertEquals(newTrack, train1.getTrack());
    }

  @Test
  void setDelay(){
    Duration newDelay = Duration.ofMinutes(10);
    train1.setDelay(newDelay);
    Assertions.assertEquals(newDelay, train1.getDelay());
  }

  @Test
  void invalidTrainNumber() {
    assertThrows(IllegalArgumentException.class, () -> new TrainDeparture
        (LocalTime.of(10,30),
            "F3", -1,"Oslo", -1, Duration.ofMinutes(0)));
  }

  @Test
  void invalidTrack(){
    assertThrows(IllegalArgumentException.class, () -> new TrainDeparture
        (LocalTime.of(10,30),
            "F3", -1,"Oslo", -1, Duration.ofMinutes(0)));

  }

  @Test
  void invalidDelay(){
    assertThrows(IllegalArgumentException.class, () -> new TrainDeparture
        (LocalTime.of(10,30),
            "F3", -1,"Oslo", -1, Duration.ofMinutes(0)));

  }
}
