import com.chaka.tech.chakatakehometask.ChakaTakehomeTaskApplication;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ChakaTakehomeTaskApplication.class })
@PropertySource("application.properties")
public class ChakaTakehomeTaskApplicationIT {
}
