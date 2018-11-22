package com.example.mytimer;

import com.example.mytimer.consts.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 接收、处理、响应客户端 WebSocket 请求的核心业务处理类。
 *
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {


    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private static final String WEB_SOCKET_URL = "ws://localhost:8888/websocket";

    private WebSocketServerHandshaker webSocketServerHandshaker;



    /**
     * 客户端与服务端创建连接是调用
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        NettyConfig.group.add(ctx.channel());
        System.out.println("客户端与服务端连接开启……");
    }

    /**
     * 客户端与服务端断开连接时调用
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        NettyConfig.group.remove(ctx.channel());
        System.out.println("客户端与服务端断开连接……");

    }

    /**
     * 服务器端接收客户端发送的数据结束后调用
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 出现异常时调用
     * @param ctx ChannelHandlerContext
     * @param cause Throwable
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws  Exception {
        LOGGER.error("", cause);
        ctx.close();
    }

    /**
     * 处理 WebSocket 请求的核心方法
     * @param ctx ChannelHandlerContext
     * @param message Object
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) throws Exception {
        // 处理客户端向服务器端发起 http 请求握手的业务
        if (message instanceof FullHttpRequest) {
            handHttpRequest(ctx, (FullHttpRequest) message);
            return;
        }
        // 处理 WebSocket 连接业务
        else if (message instanceof WebSocketFrame) {
            handWebSocketFrame(ctx, (WebSocketFrame) message);
            return;
        }
    }
    /**
     * 处理客户端向服务器端发起 http 请求握手的业务
     * @param ctx ChannelHandlerContext
     * @param request FullHttpRequest
     */
    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

        String upgradeValue = request.headers().get("Upgrade").toString();
        boolean decoderResultIsFailure = request.decoderResult().isFailure();
        LOGGER.info("upgradeValue: {}", upgradeValue);
        LOGGER.info("decoderResultIsFailure: {}", decoderResultIsFailure);
        if (decoderResultIsFailure || ! ("websocket".equals(upgradeValue))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST
            ));
            return;
        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                WEB_SOCKET_URL, null, false
        );

        webSocketServerHandshaker = factory.newHandshaker(request);

        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), request);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {

        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            return;
        }

        // 服务端向客户端发送数据
        ChannelFuture future = ctx.channel().writeAndFlush(response);

        if (response.status().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);

        }
    }


    /**
     * 处理 WebSocket 业务
     * @param ctx ChannelHandlerContext
     * @param frame WebSocketFrame
     */
    private void handWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // 如果关闭指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }

        // 是否是 ping 消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 是否是二进制消息
        if (! (frame instanceof TextWebSocketFrame)) {
            LOGGER.error("不支持二进制消息");
            throw new UnsupportedOperationException("不支持");
        }

        String text = ((TextWebSocketFrame) frame).text();
        LOGGER.info("text: {}", text);
        TextWebSocketFrame twsf = new TextWebSocketFrame(new Date().toString() +
                ctx.channel().id() + " ============== " +
                text);
        // 群发，服务端向每个客户端群发：
        NettyConfig.group.writeAndFlush(twsf);



    }
}
