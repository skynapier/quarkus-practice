package tian;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.mockito.InjectMock;
import org.jboss.logging.Logger;
import org.mockito.Mockito;
import tian.timeconverter.TimeConverterService;

import javax.inject.Inject;

import static org.mockito.Mockito.when;


@QuarkusTest
public class MockTimeConverterServiceTest {

    private static final Logger LOGGER = Logger.getLogger(MockTimeConverterServiceTest.class);

    @Inject
    TimeConverterService timeConverterService;

    @Test
    public void testUsingMockTimeConverter(){
        Assertions.assertEquals("MockTimeConverterReturn", timeConverterService.timeParser(123123213L,123f,123f));

    }

}
