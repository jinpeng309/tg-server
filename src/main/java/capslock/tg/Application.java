package capslock.tg;

import capslock.tg.component.connection.Connector;
import capslock.tg.configuration.DiConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by capslock.
 */
public class Application {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DiConfiguration.class);
        final Connector connector = context.getBean(Connector.class);
        connector.start(80);
    }
}
