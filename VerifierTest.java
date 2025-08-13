package edu.ntnu.stud;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the Verifier class.
 */
public class VerifierTest {
  @Test
    public void testValidateMenuChoiceWithValidInput() {
      String input = "5\n"; // Simulating user input of "5"
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      int validatedChoice = Verifier.validateMenuChoice();
      assertEquals(5, validatedChoice);
    }

    @Test
    public void testValidateMenuChoiceWithInvalidInput() {
      String input = "invalid\n7\n"; // Simulating user input of "invalid" first, then "7"
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      int validatedChoice = Verifier.validateMenuChoice();
      assertEquals(7, validatedChoice); // Since the method loops until it gets an integer, it should return the next valid integer (7 in this case)
    }
}



