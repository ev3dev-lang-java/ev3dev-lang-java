package ev3dev.utils.io;

/**
 * A collection of various Linux constants (ioctl numbers, file modes erc)
 */
public final class NativeConstants {
    public static final int O_ACCMODE = 0003;
    public static final int O_RDONLY = 00;
    public static final int O_WRONLY = 01;
    public static final int O_RDWR = 02;
    public static final int O_CREAT = 0100;
    public static final int O_EXCL = 0200;
    public static final int O_NOCTTY = 0400;
    public static final int O_TRUNC = 01000;
    public static final int O_APPEND = 02000;
    public static final int O_NONBLOCK = 04000;
    public static final int O_NDELAY = O_NONBLOCK;
    public static final int O_SYNC = 04010000;
    public static final int O_FSYNC = O_SYNC;
    public static final int O_ASYNC = 020000;
    public static final int PROT_READ = 1;
    public static final int PROT_WRITE = 2;
    public static final int MAP_SHARED = 1;
    public static final int MAP_public = 2;
    public static final int MAP_FILE = 0;
    public static final int VT_OPENQRY = 0x5600;       /* find available vt */
    public static final int VT_GETMODE = 0x5601;       /* get mode of active vt */
    public static final int VT_SETMODE = 0x5602;       /* set mode of active vt */
    public static final int VT_GETSTATE = 0x5603;      /* get global vt state info */
    public static final int VT_SENDSIG = 0x5604;       /* signal to send to bitmask of vts */
    public static final int VT_RELDISP = 0x5605;       /* release display */
    public static final int VT_ACTIVATE = 0x5606;      /* make vt active */
    public static final int VT_WAITACTIVE = 0x5607;    /* wait for vt active */
    public static final int VT_DISALLOCATE = 0x5608;   /* free memory associated to vt */
    public static final int VT_RESIZE = 0x5609;        /* set kernel's idea of screensize */
    public static final int VT_RESIZEX = 0x560A;       /* set kernel's idea of screensize + more */
    public static final int VT_LOCKSWITCH = 0x560B;    /* disallow vt switching */
    public static final int VT_UNLOCKSWITCH = 0x560C;  /* allow vt switching */
    public static final int VT_GETHIFONTMASK = 0x560D; /* return hi font mask */
    public static final int VT_WAITEVENT = 0x560E;     /* Wait for an event */
    public static final int VT_SETACTIVATE = 0x560F;   /* Activate and set the mode of a console */
    public static final int KDGKBMODE = 0x4B44; /* gets current keyboard mode */
    public static final int KDSKBMODE = 0x4B45; /* sets current keyboard mode */
    public static final int FBIOGET_CON2FBMAP = 0x460F;

    public static final int KDSETMODE = 0x4B3A;    /* set text/graphics mode */
    public static final int KD_TEXT = 0x00;
    public static final int KD_GRAPHICS = 0x01;
    public static final int K_OFF = 0x04;
    public static final int VT_AUTO = 0x00; /* auto vt switching */
    public static final int VT_PROCESS = 0x01;    /* process controls switching */
    public static final int VT_ACKACQ = 0x02;    /* acknowledge switch */
    public static final int SIGUSR2 = 12;
    public static final int ENOTTY = 25;
    /**
     * Pixels are laid at once one-by-one.
     */
    public static final int FB_TYPE_PACKED_PIXELS = 0;
    /**
     * 1BPP visual, 0 = black, 1 = white
     */
    public static final int FB_VISUAL_MONO01 = 0;
    /**
     * 1BPP visual, 1 = black, 0 = white
     */
    public static final int FB_VISUAL_MONO10 = 1;
    /**
     * 32BPP visual
     */
    public static final int FB_VISUAL_TRUECOLOR = 2;
    /**
     * IOCTL number for getting variable screen info.
     */
    public static final int FBIOGET_VSCREENINFO = 0x4600;
    /**
     * IOCTL number for setting variable screen info.
     */
    public static final int FBIOPUT_VSCREENINFO = 0x4601;
    /**
     * IOCTL number for getting fixed screen info.
     */
    public static final int FBIOGET_FSCREENINFO = 0x4602;
}
