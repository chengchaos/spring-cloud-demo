package org.example.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.LoggerFactory;

public class NettyDiscardServer {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NettyDiscardHandler.class);

    private final int serverPort;

    private final ServerBootstrap sb = new ServerBootstrap();

    public NettyDiscardServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start() {

        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            // 1. 设置反应器线程组
            sb.group(bossLoopGroup, workerLoopGroup);
            // 2. 设置 nio 类型的通道
            sb.channel(NioServerSocketChannel.class);
            // 3. 设置监听端口
            sb.localAddress(this.serverPort);
            // 4. 设置 Channel 的参数

            sb.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            sb.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 5. 装配 Sub Channel 的流水线
            sb.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel sc) throws Exception {
                    // 流水线管道子通道中的 Handler
                    sc.pipeline()
                            .addLast(new NettyDiscardHandler());
                }
            });
            // 6. 开始绑定服务器
            // 通过调用 sync 同步方法阻塞直到，绑定成功。
            ChannelFuture channelFuture = sb.bind().sync();

            LOGGER.info("启动成功，监听端口 => {}",
                    channelFuture.channel().localAddress());
            // 7. 等待通道关闭的异步任务结束
            // 监听服务通道会一直等待通道关闭的一部任务结束。
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8. 关闭 EventLoopGroup
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        int port = NettyDemoConfig.SOCKET_SERVER_PORT;
        new NettyDiscardServer(port).start();
    }
}
