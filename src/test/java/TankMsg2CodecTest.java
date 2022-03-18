import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.netty.MsgType;
import com.cy.tank.netty.TankJoinMsg;
import com.cy.tank.netty.TankMsgDecoder;
import com.cy.tank.netty.TankMsgEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TankMsg2CodecTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel c = new EmbeddedChannel();
        c.pipeline().addLast(new TankMsgEncoder());

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(10,20, Dir.DOWN, false, Group.GOOD, id);
        c.writeOutbound(msg);

        ByteBuf buf = c.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group g = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        Assert.assertEquals(MsgType.TankJoin, type);
        Assert.assertEquals(33, length);
        Assert.assertEquals(10, x);
        Assert.assertEquals(20, y);
        Assert.assertEquals(Dir.DOWN, dir);
        Assert.assertEquals(false, moving);
        Assert.assertEquals(Group.GOOD, g);
        Assert.assertEquals(uuid , id);

    }

    @Test
    public void testDecoder() {
        EmbeddedChannel c = new EmbeddedChannel();
        c.pipeline().addLast(new TankMsgDecoder());

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(10,20, Dir.DOWN, false, Group.GOOD, id);
        MsgType type = MsgType.TankJoin;

        ByteBuf buf = Unpooled.buffer();
        byte[] bytes = msg.toBytes();
        buf.writeInt(type.ordinal());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        c.writeInbound(buf.duplicate());

        TankJoinMsg t = c.readInbound();

        Assert.assertEquals(10, t.getX());
        Assert.assertEquals(20, t.getY());
        Assert.assertEquals(Dir.DOWN, t.getDir());
        Assert.assertEquals(false, t.isMoving());
        Assert.assertEquals(Group.GOOD, t.getGroup());
        Assert.assertEquals(id, t.getId());

    }
}
