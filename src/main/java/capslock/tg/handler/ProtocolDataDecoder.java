package capslock.tg.handler;

import capslock.tg.ProtocolData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by capslock.
 */
public class ProtocolDataDecoder extends ByteToMessageDecoder {
    private PacketParseState state = PacketParseState.AWAIT_PACKET_LENGTH_VERSION;
    private int packetLength;

    private enum PacketParseState {
        AWAIT_PACKET_LENGTH_VERSION, AWAIT_PACKET_LENGTH_HEADER, AWAIT_PACKET_LENGTH, AWAIT_PACKET_DATA
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
        while (in.readableBytes() > 0) {
            switch (state) {
                case AWAIT_PACKET_LENGTH_VERSION:
                    final int packetLengthVersion = in.readByte();
                    if (packetLengthVersion == 0xffffffef) {
                        state = PacketParseState.AWAIT_PACKET_LENGTH_HEADER;
                    } else {
                        ctx.close();
                    }
                    break;
                case AWAIT_PACKET_LENGTH_HEADER:
                    final byte length = in.readByte();
                    if (length < 0x7F) {
                        packetLength = length * 4;
                        state = PacketParseState.AWAIT_PACKET_DATA;
                    } else {
                        state = PacketParseState.AWAIT_PACKET_LENGTH;
                    }
                    break;
                case AWAIT_PACKET_LENGTH:
                    if (in.readableBytes() >= 3) {
                        final byte[] lengthByteArray = new byte[3];
                        in.order(ByteOrder.LITTLE_ENDIAN).readBytes(lengthByteArray);
                        packetLength = (lengthByteArray[0]) | (lengthByteArray[1] << 8) | (lengthByteArray[2] << 16);
                    } else {
                        return;
                    }
                    break;
                case AWAIT_PACKET_DATA:
                    if (in.readableBytes() >= packetLength) {
                        final ByteBuf protocolDataByteBuf = Unpooled.buffer(packetLength);
                        in.readBytes(protocolDataByteBuf, packetLength);
                        out.add(new ProtocolData(protocolDataByteBuf));
                        return;
                    }
                    break;
            }
        }
    }
}
