package me.wuxie.wakeshow.wakeshow.network.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.network.InPacketBuilder;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.client_in.MessageNettyLogin;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * Created by Yohann on 2016/11/9.
 */
@Deprecated
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Getter
    private static final Map<UUID,Channel> playerChannelMap = new HashMap<>();
    @Getter
    private static final Map<Channel,UUID> ChannelPlayerMap = new HashMap<>();
    // 心跳丢失计数器
    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("--- Client is active ---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("--- Client is inactive ---");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 判断接收到的包类型
        if (msg instanceof NettyMessage) {
            NettyMessage nettyMessage = (NettyMessage) msg;
            if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == (byte) 1) {
                String data = new String(((NettyMessage) msg).getData());
                if(data.equals("heartbeat")){
                    handleHeartbreat(null, null);
                }else {
                    handleData(ctx, (NettyMessage) msg);
                }
            }
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent  event = (IdleStateEvent)evt;
            IdleState eventType =  event.state();
            Channel channel = ctx.channel();
            UUID uuid = getChannelPlayerMap().get(channel);
            List<NettyMessage> byteList = ServerThread.getWaitSends().computeIfAbsent(uuid,(uuid1 -> new ArrayList<>()));
            if(!byteList.isEmpty()){
                if (eventType == IdleState.ALL_IDLE) {
                    channel.writeAndFlush(byteList.get(0));
                    byteList.remove(0);
                }
            }

            // 空闲6s之后触发 (心跳包丢失)
            /*if (counter >= 3) {
                // 连续丢失3个心跳包 (断开连接)
                ctx.channel().close().sync();
                //System.out.println("已与Client断开连接");
            } else {
                counter++;
                //System.out.println("丢失了第 " + counter + " 个心跳包");
            }*/
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //System.out.println("连接出现异常");
    }

    /**
     * 处理心跳包
     *
     * @param ctx
     * @param packet
     */
    private void handleHeartbreat(ChannelHandlerContext ctx, byte[] packet) {
        // 将心跳丢失计数器置为0
        counter = 0;
        //System.out.println("收到心跳包");
        //ReferenceCountUtil.release(packet);
    }

    /**
     * 处理数据包
     *
     * @param ctx
     * @param packet
     */
    private void handleData(ChannelHandlerContext ctx, NettyMessage packet) {
        counter = 0;
        PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(packet.getData()));
        int packetId = buf.readByte();
        InPacketBuilder builder = PacketHandler.getInPacketBuilderMap().get(packetId);
        if(builder!=null){
            InPacket inPacket = builder.builder(buf);
            if(inPacket instanceof MessageNettyLogin){
                UUID uuid = ((MessageNettyLogin) inPacket).getUuid();
                ChannelPlayerMap.put(ctx.channel(),uuid);
                playerChannelMap.put(uuid,ctx.channel());
                inPacket.handlePacket(uuid);
            } else {
                UUID uuid = ChannelPlayerMap.get(ctx.channel());
                Bukkit.getScheduler().runTask(WakeShow.getPlugin(),()-> inPacket.handlePacket(uuid));
            }
            if(inPacket!=null){
                //System.out.println("收到 "+inPacket.getClass().getSimpleName()+" 数据包!");
            }
        }
        /*System.out.println(new String(packet));*/
        ReferenceCountUtil.release(packet);
        //ctx.channel().writeAndFlush("aaaa".getBytes());
    }
}
