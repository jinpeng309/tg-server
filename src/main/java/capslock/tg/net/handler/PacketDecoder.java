package capslock.tg.net.handler;

import capslock.tg.net.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by capslock.
 */
public class PacketDecoder extends ByteToMessageDecoder {
    private static final int ABRIDGEDV_ERSION = 0xffffffef;
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
                    if (packetLengthVersion == ABRIDGEDV_ERSION) {
                        state = PacketParseState.AWAIT_PACKET_LENGTH_HEADER;
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
                        final long authId = in.order(ByteOrder.LITTLE_ENDIAN).readLong();
                        final long messageId = in.order(ByteOrder.LITTLE_ENDIAN).readLong();
                        final int dataLength = in.order(ByteOrder.LITTLE_ENDIAN).readInt();
                        final byte[] data = new byte[dataLength];
                        in.readBytes(data);
                        state = PacketParseState.AWAIT_PACKET_LENGTH_HEADER;
                        final Packet packet = new Packet(authId, messageId, data);
                        out.add(packet);
                        return;
                    }
                    break;
            }
        }
    }
}
