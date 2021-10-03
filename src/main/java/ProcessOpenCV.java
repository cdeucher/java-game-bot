import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProcessOpenCV {

    public static Mat loadImage(String path) {
        return Imgcodecs.imread(path);
    }


    public static void convertImage(BufferedImage frame) {
        Mat grayImage = new Mat();
        Mat detectedEdges = new Mat();
        Mat canny = new Mat();
        List<Mat> hsvPlanes = Arrays.asList(new Mat(),new Mat(),new Mat());
        Mat hsvImg = new Mat();

        Mat mat = Utils.img2Mat(frame);

//        Imgproc.cvtColor(mat, grayImage, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.blur(grayImage, detectedEdges, new Size(7, 7));
//        Imgproc.Canny(detectedEdges, canny, 255, 255/3, 3, true);
//        Imgproc.cvtColor(mat, hsvImg, Imgproc.COLOR_BGR2HSV);

//        Utils.saveImage(detectedEdges);
//        Imgproc.blur(mat, blurredImage, new Size(7, 7));
//        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

    }

    // https://stackoverflow.com/questions/59273899/how-to-adjust-the-threshold-for-template-matching-in-opencv-java
    public static Mat detectImage(Mat imageSource, String templatePath) {
        double minMatchQuality = 0.60;
        return detectImage(imageSource, templatePath, minMatchQuality);
    }

    public static Mat detectImage(Mat imageSource, String templatePath, double minMatchQuality) {
        Mat template = loadImage(templatePath);
        Mat outputImage = new Mat();
        int machMethod=Imgproc.TM_CCOEFF_NORMED; //TM_CCOEFF;
        Imgproc.matchTemplate(imageSource, template, outputImage, machMethod);

        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        System.out.printf("Match:%s -> %s", mmr.maxVal, String.valueOf(mmr.maxLoc));

        if (mmr.maxVal >= minMatchQuality) {
            //Draw rectangle on result image
            Imgproc.rectangle(imageSource, mmr.maxLoc, new Point(mmr.maxLoc.x + template.cols(),
                    mmr.maxLoc.y + template.rows()), new Scalar(255, 255, 255));

            return imageSource;
        }else
            return null;
    }


}
