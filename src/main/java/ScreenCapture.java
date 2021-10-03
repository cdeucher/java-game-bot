import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ScreenCapture {

    private final String windowName = "METIN2";
    private User32 user32 = null;

    public ScreenCapture() {
        this.user32 = User32.INSTANCE;
    }

    public ScreenCapture(User32 user32) {
        this.user32 = user32;
    }

    public BufferedImage getScreenFrame(Rectangle rectangle) {
        try {
            Robot robot = new Robot();

            if(rectangle.isEmpty())
                rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            return robot.createScreenCapture(rectangle);
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }



    public Rectangle getApplication() {
        HWND game = user32.FindWindow(null, windowName);
        if (Objects.isNull(game))
            throw new RuntimeException("application not found " + windowName);

        RECT rect = new RECT();
        boolean result = user32.GetWindowRect(game, rect);
        if (!result)
            throw new RuntimeException("application position not found " + windowName);

        System.out.printf("The corner locations for the window \"%s\" are %s",
                windowName, rect.toString());

        return rect.toRectangle();
    }

}
