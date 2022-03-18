import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.netty.TankJoinMsg;
import com.cy.tank.netty.TankMsgDecoder;
import com.cy.tank.netty.TankMsgEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TankMsgCodecTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel c = new EmbeddedChannel();
        c.pipeline().addLast(new TankMsgEncoder());

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(10,20, Dir.DOWN, false, Group.GOOD, id);
        c.writeOutbound(msg);

        ByteBuf buf = c.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group g = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

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

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());

        c.writeInbound(buf);

        TankJoinMsg t = c.readInbound();

        Assert.assertEquals(10, t.getX());
        Assert.assertEquals(20, t.getY());
        Assert.assertEquals(Dir.DOWN, t.getDir());
        Assert.assertEquals(false, t.isMoving());
        Assert.assertEquals(Group.GOOD, t.getGroup());
        Assert.assertEquals(id, t.getId());

    }
}
