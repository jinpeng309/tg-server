package capslock.tg.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by capslock.
 */
@Configuration
@ComponentScan(value = {"capslock.tg.component", "capslock.tg.service"})
public class DiConfiguration {
}
