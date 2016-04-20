package capslock.tg.handler;

import capslock.tg.ProtocolData;
import capslock.tg.api.ApiObjectDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteOrder;

/**
 * Created by capslock.
 */
public final class ProtocolDataHandler extends SimpleChannelInboundHandler<ProtocolData> {

    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final ProtocolData protocolData) throws Exception {
        final ByteBuf littleEndDataBuf = protocolData.getData().order(ByteOrder.LITTLE_ENDIAN);
        final long authId = littleEndDataBuf.readLong();
        if (authId == 0){
            final long messageId = littleEndDataBuf.readLong();
            final int dataLength = littleEndDataBuf.readInt();
            final ByteBuf data = Unpooled.buffer(dataLength);
            littleEndDataBuf.readBytes(data);
            //todo ack
            ApiObjectDeserializer.deserialize(data);

        }else{

        }
    }
}
