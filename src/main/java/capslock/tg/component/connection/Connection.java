package capslock.tg.component.connection;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by capslock.
 */
@AllArgsConstructor
@Data
public class Connection {
    private final String connId;
    private final ChannelHandlerContext context;

    public void close() {
        context.close();
    }
}
