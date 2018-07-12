import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {
    @Test
    public void shouldCheckIfGreetingIsPrinted() {
        //given
        String expected = "Hello World!";

        //when
        String actual = HelloWorld.greeting();

        //then
        assertEquals(expected, actual);
    }
}