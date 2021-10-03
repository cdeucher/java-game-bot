import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class Bot {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ScreenCapture screenCap = new ScreenCapture();

        final Rectangle rect = screenCap.getApplication();
        BufferedImage frame = screenCap.getScreenFrame(rect);

        Mat out = ProcessOpenCV.detectImage(Utils.img2Mat(frame), "gomi.png");

        if (Objects.nonNull(out))
            Utils.saveImage(out);
    }


}
