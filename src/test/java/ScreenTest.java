import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScreenTest {

    final User32 user32 = mock(User32.class); //new WindowUser32();
    final ScreenCapture screen = new ScreenCapture(user32);

    @BeforeAll
    static void setUp() {

    }

    @Test
    void whenGetApplication_ShouldGetApllication() {
        when(user32.FindWindow(any(), any())).thenReturn(new HWND());
        when(user32.GetWindowRect(any(), any())).thenReturn(true);
        assertEquals(screen.getApplication(), new RECT().toRectangle());
    }


}