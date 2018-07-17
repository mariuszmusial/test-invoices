package pl.coderstrust.project;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void shouldPrintGreeting() {
        //given
        String expected = "Hello World!";

        //when
        String actual = HelloWorld.greeting();

        //then
        assertEquals(expected,actual);
    }
}