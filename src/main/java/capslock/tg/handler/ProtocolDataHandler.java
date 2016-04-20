package capslock.tg.handler;

import capslock.tg.model.ProtocolData;
import capslock.tg.component.connection.Connection;
import capslock.tg.component.connection.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;

import java.net.InetSocketAddress;

/**
 * Created by capslock.
 */
public final class ProtocolDataHandler extends SimpleChannelInboundHandler<ProtocolData> {
    private ConnectionManager connectionManager;
    private String connId;

    public ProtocolDataHandler(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleState) {
            connectionManager.clientClosed(connId);
            ctx.close();
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        final InetSocketAddress clientAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        connId = clientAddress.getHostName() + ":" + clientAddress.getPort();
        final Connection connection = new Connection(connId, ctx);
        connectionManager.registerConnection(connId, connection);
    }

    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final ProtocolData protocolData) throws Exception {
        connectionManager.messageReceivedFromClient(connId, protocolData);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        connectionManager.clientClosed(connId);
        ctx.close();
    }
}
