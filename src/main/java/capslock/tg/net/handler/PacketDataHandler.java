package capslock.tg.net.handler;

import capslock.tg.net.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by capslock.
 */
public final class PacketDataHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final Packet packet) throws Exception {

    }
}
