import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Utils {

    public static void saveImage(BufferedImage bufferedImage) {
        File file = new File("screen-capture.png");
        boolean status = false;
        try {
            status = ImageIO.write(bufferedImage, "png", file);
            System.out.println("Screen Captured ? " + status + " File Path:- " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveImage(Mat matImage) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = mat2BufferedImage(matImage);
            File file = new File("screen-capture.png");
            boolean status = false;
            status = ImageIO.write(bufferedImage, "png", file);
            System.out.println("Screen Captured ? " + status + " File Path:- " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Mat img2Mat(BufferedImage image) {
        image = convertTo3ByteBGRType(image);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }
    private static BufferedImage convertTo3ByteBGRType(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(image, 0, 0, null);
        return convertedImage;
    }

    public static BufferedImage mat2BufferedImage(Mat matrix)throws IOException {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }


}
