package org.example.netty.general;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelCutting {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelCutting.class);

    public static class SimpleInHandler1 extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            LOGGER.info("入站处理 1 ，");
            super.channelRead(ctx, msg);

        }
    }
    public static class SimpleInHandler2 extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            LOGGER.info("入站处理 2 ，");
            //super.channelRead(ctx, msg);
            ByteBuf outmsg = Unpooled.buffer();
            outmsg.writeInt(100);
            ctx.writeAndFlush(outmsg);
        }
    }
    public static class SimpleInHandler3 extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            LOGGER.info("入站处理 3 ，");
            super.channelRead(ctx, msg);

        }
    }

    @Test
    public void testPipelineCutting() {

        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new SimpleInHandler1())
                        .addLast(new SimpleInHandler2())
                        .addLast(new SimpleInHandler3());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);

        Object o = channel.readOutbound();
        LOGGER.info("result => {} == {}", o.getClass(), o.toString());

        if (o instanceof ByteBuf) {
            int result = ((ByteBuf) o).readInt();
            LOGGER.info("result => {}", result);
        }

    }
}
