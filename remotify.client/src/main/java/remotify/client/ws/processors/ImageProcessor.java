package remotify.client.ws.processors;

import messages.device.ImageMessage;
import org.apache.log4j.Logger;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * .
 * User: Ostap
 * Date: 5/8/14
 * Time: 12:10 AM
 */
public abstract class ImageProcessor<T extends ImageMessage> implements Processor<T>  {

    Logger l = Logger.getLogger(ImageProcessor.class);

    @Override
    public T process(T message) {
        try {
            BufferedImage screenShot = getImage();
            double coef = screenShot.getHeight()/(double)screenShot.getWidth();
            int newWidth = 600;
            int newHeight = (int) (newWidth * coef);
            screenShot = ImageUtils.resize(screenShot, newWidth, newHeight);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenShot, "jpg", baos);

            ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
            baos = new ByteArrayOutputStream();
            GZIPOutputStream gzos = new GZIPOutputStream(baos);

            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }

            in.close();

            gzos.finish();
            gzos.close();


            message.image = baos.toByteArray();
            System.out.println("Image size = " + message.image.length);
        } catch (Exception ex) {
            l.error("", ex);
        }
        return message;
    }

    protected abstract BufferedImage getImage() throws Exception;
}
