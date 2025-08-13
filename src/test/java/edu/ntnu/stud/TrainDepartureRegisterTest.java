package edu.ntnu.stud;

import java.time.Duration;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests TrainDepartureRegister class.
 *
 * @author Nathalie Graidy Andreassen
 */
public class TrainDepartureRegisterTest {

  private TrainDepartureRegister trainDepartureRegister;
  private HashMap<Integer, TrainDeparture> trainDepartures;

  @BeforeEach
  public void setUp() {
    trainDepartures = new HashMap<>();
    // Adding sample Traindepartures for testing
    TrainDeparture train1 = new TrainDeparture(LocalTime.of(15, 30), "F6",
        1, "Oslo", -1, Duration.ofMinutes(0));
    TrainDeparture train2 = new TrainDeparture(LocalTime.of(12, 0), "F6",
        2, "Oslo", -1, Duration.ofMinutes(5));
    TrainDeparture train3 = new TrainDeparture(LocalTime.of(14, 0), "L2",
        3, "Bergen", 3, Duration.ofMinutes(5));

    trainDepartures.put(1, train1);
    trainDepartures.put(2, train2);
    trainDepartures.put(3, train3);

    LocalTime time = LocalTime.of(7, 0);

    // Initialize trainDepartureRegister with sample data for testing
    trainDepartureRegister = new TrainDepartureRegister(trainDepartures, time);
  }

  @Test
  public void testGetTrainDepartures() {
    assertNotNull(trainDepartureRegister.getTrainDepartures());
  }

  @Test
  public void testAddTrainDeparture() {
    int newTrainNumber = 4;
    TrainDeparture newTrain =
        new TrainDeparture(LocalTime.of(15, 30), "F6", newTrainNumber,
            "Hamar", -1, Duration.ofMinutes(0));
    trainDepartureRegister.addTrainDeparture(newTrainNumber, newTrain);

    assertTrue(trainDepartures.containsKey(4));
  }

  @Test
  public void testAddTrainDepartureIfTrainDepartureAlreadyExists() { //vet ikke hvordan jeg skal endre denne
    int newTrainNumber = 1;
    TrainDeparture newTrain =
        new TrainDeparture(LocalTime.of(15, 30), "F6", newTrainNumber,
            "Hamar", -1, Duration.ofMinutes(0));
    trainDepartureRegister.addTrainDeparture(newTrainNumber, newTrain);

    assertNotEquals(4, trainDepartures.size());
  }

  @Test
  public void testSearchByTrainNumber() {
    trainDepartureRegister.searchByTrainNumber(2);
    assertEquals(trainDepartures.get(2), trainDepartureRegister.searchByTrainNumber(2));
  }

  @Test
  public void testSearchByTrainNumberIfTrainNumberDoesNotExist() {
    assertNull(trainDepartureRegister.searchByTrainNumber(4));
  }

  @Test
  public void testSetTrack() { //perfekt
    trainDepartureRegister.setTrack(3, 5);
    assertEquals(5, trainDepartureRegister.searchByTrainNumber(3).getTrack());
  }

  @Test
  public void testSetTrackIfTrainDepartureDoesNotExist() {
    assertNull(trainDepartureRegister.setTrack(9, 5));
  }

  @Test
  public void testAddDelay() {
    trainDepartureRegister.addDelay(1, Duration.ofMinutes(15));
    assertEquals(Duration.ofMinutes(15), trainDepartureRegister.searchByTrainNumber(1).getDelay());
  }

  @Test
  public void testAddDelayIfTrainDepartureDoesNotExist() {
    assertNull(trainDepartureRegister.addDelay(9, Duration.ofMinutes(15)));
  }

  @Test
  public void testSearchTrainByDestination() {
    HashMap<Integer, TrainDeparture> found = new HashMap<>();

    TrainDeparture train1 = trainDepartures.get(1);
    TrainDeparture train2 = trainDepartures.get(2);

    found.put(1, train1);
    found.put(2, train2);

    trainDepartureRegister.searchTrainByDestination("Oslo");
    assertEquals(found, trainDepartureRegister.searchTrainByDestination("Oslo"));
  }

  @Test
  public void testSearchTrainByDestinationIfDestinationDoesNotExist() {
    assertTrue(trainDepartureRegister.searchTrainByDestination("Alta").isEmpty());
  }

  @Test
  public void testRemoveTrainDeparture() {
    trainDepartureRegister.removeTrainDeparture(1);
    assertNull(trainDepartures.get(1));
  }

  @Test
  public void testRemoveTrainDepartureIfTrainDepartureDoesNotExist() {
    TrainDepartureRegister trainDepartureRegisterBeforeChange =
        new TrainDepartureRegister(trainDepartureRegister.getTrainDepartures(),
            trainDepartureRegister.getTime());

    trainDepartureRegister.removeTrainDeparture(4);
    assertEquals(trainDepartureRegisterBeforeChange.getTrainDepartures(),
        trainDepartureRegister.getTrainDepartures());
  }

  @Test
  public void testSort() {
    ArrayList<TrainDeparture> sortedMap = trainDepartureRegister.sort(trainDepartures);

    // Assuming trainDepartures are already sorted by time
    LocalTime prevTime = LocalTime.MIN;
    for (TrainDeparture departure : sortedMap) {
      assertTrue(
          departure.getDepartureTime().isAfter(prevTime)
              || departure.getDepartureTime().equals(prevTime));
      prevTime = departure.getDepartureTime();
    }
  }

  @Test
  public void testSetTime() {
    LocalTime newTime = LocalTime.of(12, 0);
    trainDepartureRegister.setTime(newTime);
    assertEquals(newTime, trainDepartureRegister.getTime());
  }
}
