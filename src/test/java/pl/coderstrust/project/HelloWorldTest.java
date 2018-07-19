package pl.coderstrust.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloWorldTest {

  @Test
  public void shouldPrintGreeting() {
    //given
    String expected = "Hello World!";

    //when
    String actual = HelloWorld.greeting();

    //then
    assertEquals(expected, actual);
  }
}