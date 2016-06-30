package nettyfirstdemo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter  {
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务器连接成功");
		super.channelActive(ctx);
		ctx.writeAndFlush("客户端你好!");
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
		
		ByteBuf buf = (ByteBuf)msg;
		
		byte[] req = new byte[buf.readableBytes()];
		
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("server read :"+body);
		String currentTime = "获取时间".equals(body) ? new java.util.Date(
				System.currentTimeMillis()
		).toString() : "error";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
	}
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		
        ctx.close();
    }
}
