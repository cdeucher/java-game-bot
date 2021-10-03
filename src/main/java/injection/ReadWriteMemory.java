package injection;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

//https://pastebin.com/Vq8wfy39
public class ReadWriteMemory {
    static final String windowName = "METIN2";
    //static Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
    //static User32 user32Tmp = (User32)   Native.loadLibrary("user32"  , User32.class);
    static Kernel32 kernel32 = null;
    static User32 user32 = null;

    public static void main(String[] args) {
        user32 =  User32.INSTANCE;
        kernel32 =  Kernel32.INSTANCE;

        int pid = getProcessId(windowName); // get our process ID
        HANDLE readprocess = openProcess(0x0010, pid); // open the process ID with read priviledges.

        int size = 4; // we want to read 4 bytes
        Memory read = readMemory(readprocess,0x00AB0C62,size); // read 4 bytes of memory starting at the address 0x00AB0C62.

        System.out.println(read.getInt(0)); // print out the value!
    }

    public static HANDLE openProcess(int permissions, int pid)
    {
        HANDLE process = kernel32.OpenProcess(permissions,true, pid);
        return process;
    }

    public static int getProcessId(String window)
    {
        IntByReference pid = new IntByReference(0);
        user32.GetWindowThreadProcessId(user32.FindWindow(null,window), pid);

        return pid.getValue();
    }

    public static Memory readMemory(HANDLE process, int address, int bytesToRead)
    {
        IntByReference read = new IntByReference(0);
        Memory output = new Memory(bytesToRead);

        Pointer tmp = new Pointer(address);
        kernel32.ReadProcessMemory(process, tmp.getPointer(address), output, bytesToRead, read);
        return output;
    }

//    public static int writeMemory(Pointer process, int address, short[] data)
//    {
//        IntByReference written =  new IntByReference(0);
//
//        Memory toWrite = new Memory(data.length);
//
//        for(long i = 0; i < data.length;i++)
//        {
//            toWrite.setShort(0, data[new Integer(Long.toString(i))]);
//        }
//
//        boolean b = kernel32.WriteProcessMemory(process, address, toWrite, data.length, written);
//        return written.getValue();
//    }
}