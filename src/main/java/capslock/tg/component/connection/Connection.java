package capslock.tg.component.connection;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

/**
 * Created by capslock.
 */
@AllArgsConstructor
public class Connection {
    private final String connId;
    private final ChannelHandlerContext context;

    public void close() {
        context.close();
    }
}
