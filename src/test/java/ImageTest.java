import com.cy.tank.Mgr.ResourceMgr;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageTest {

    @Test
    public void test() throws IOException {
        BufferedImage image = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
        Assert.assertNotNull(image);
    }
}
