package me.wuxie.wakeshow.wakeshow.network.netty;

//import common.PacketProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yohann on 2016/11/9.
 */
@Deprecated
public class ServerThread{
    private static Thread serverThread;
    private static NioEventLoopGroup acceptorGroup;
    private static NioEventLoopGroup workerGroup;
    private static ServerHandler handler;
    public static void startServerThread(int port) {
        serverThread = new Thread(() -> {
            acceptorGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(acceptorGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        //.option(ChannelOption.SO_BACKLOG, 100)
                        //.handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ChannelPipeline pipeline = ch.pipeline();
                                //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024,4,4,2,0));
                                pipeline.addLast(new NettyMessageDecoder());
                                pipeline.addLast(new NettyMessageEncoder());
                                pipeline.addLast(new IdleStateHandler(10,10,10, TimeUnit.MILLISECONDS));
                                handler = new ServerHandler();
                                pipeline.addLast(handler);
                            }
                        });
                Channel ch = bootstrap.bind(port).sync().channel();
                ch.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(acceptorGroup!=null&&!acceptorGroup.isShutdown()){
                    acceptorGroup.shutdownGracefully();
                }
                acceptorGroup = null;
                if(workerGroup!=null&&!workerGroup.isShutdown()){
                    workerGroup.shutdownGracefully();
                }
                workerGroup=null;
            }
        });
        serverThread.start();
    }

    public static void shutdown(){
        if(acceptorGroup!=null&&!acceptorGroup.isShutdown()){
            acceptorGroup.shutdownGracefully();
        }
        acceptorGroup = null;
        if(workerGroup!=null&&!workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
        workerGroup=null;
        if(serverThread!=null&&!serverThread.isInterrupted()){
            serverThread.interrupt();
        }
        serverThread = null;
        handler = null;
    }

    @Getter
    private static Map<UUID,List<NettyMessage>> waitSends = new HashMap<>();
    public static void sendPacket(UUID uuid, byte[] bytes){
        List<NettyMessage> blist =waitSends.computeIfAbsent(uuid,(uuid1)-> new ArrayList<>());
        blist.add(buildServerData(bytes));
        /*Channel channel = ServerHandler.getPlayerChannelMap().get(uuid);
        if(channel!=null){
            channel.writeAndFlush(buildServerData(bytes));
        }*/
    }

    /**
     * 创建请求消息体
     *
     * @return
     */
    private static NettyMessage buildServerData(byte[] data) {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setDelimiter(0xABEF0101);
        header.setLength(data.length);
        header.setType((byte) 1);
        header.setReserved((byte) 0);
        nettyMessage.setHeader(header);
        // 设置数据包
        nettyMessage.setData(data);
        return nettyMessage;
    }

}
