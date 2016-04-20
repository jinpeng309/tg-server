package capslock.tg;

import capslock.tg.component.Connector;
import capslock.tg.configuration.DiConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by capslock.
 */
public class Application {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DiConfiguration.class);
        final Connector connector = context.getBean(Connector.class, 80);
        connector.start();
    }
}
