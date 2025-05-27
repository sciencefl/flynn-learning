package com.sciencefl.flynn;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里的 TextWebSocketFrame 类型，表示一个文本帧（frame）
 */
public class MyTextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public  static  ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    // 当 web 客户端连接后，触发该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelMap.put(ctx.channel().id().asLongText(),ctx.channel());
        // id 表示唯一的值，LongText 是唯一的，ShortText不是惟一得
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息 " + msg.text());

        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 " + LocalDateTime.now() + " " + msg.text()));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        // 关闭连接
        ctx.close();
    }
}
