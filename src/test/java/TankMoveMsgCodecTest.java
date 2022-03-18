import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.netty.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TankMoveMsgCodecTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel c = new EmbeddedChannel();
        c.pipeline().addLast(new TankMsgEncoder());

        UUID id = UUID.randomUUID();
        TankMoveMsg msg = new TankMoveMsg(id, 10,20,Dir.LEFT);
        c.writeOutbound(msg);

        ByteBuf buf = c.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        Assert.assertEquals(MsgType.TankMove, type);
        Assert.assertEquals(28, length);
        Assert.assertEquals(10, x);
        Assert.assertEquals(20, y);
        Assert.assertEquals(Dir.LEFT, dir);
        Assert.assertEquals(uuid , id);

    }

    @Test
    public void testDecoder() {
        EmbeddedChannel c = new EmbeddedChannel();
        c.pipeline().addLast(new TankMsgDecoder());

        UUID id = UUID.randomUUID();
        TankMoveMsg msg = new TankMoveMsg(id,10,20, Dir.DOWN);
        MsgType type = MsgType.TankMove;

        ByteBuf buf = Unpooled.buffer();
        byte[] bytes = msg.toBytes();
        buf.writeInt(type.ordinal());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        c.writeInbound(buf.duplicate());

        TankMoveMsg t = c.readInbound();

        Assert.assertEquals(10, t.getX());
        Assert.assertEquals(20, t.getY());
        Assert.assertEquals(Dir.DOWN, t.getDir());
        Assert.assertEquals(id, t.getId());

    }
}
