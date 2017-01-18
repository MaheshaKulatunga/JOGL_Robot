/**
 * A class for controlling a set of Anim instances
 *
 * @author    Dr Steve Maddock
 * @edited Mahesha Kulatunga mkkulatunga1@sheffield.ac.uk *
 */

public class AnimationScene {
  //Code added in by Mahesha Kulatunga
  //Initialise main robot parameters
  public static final int ROBOT_X_PARAM = 0;
  public static final int ROBOT_Y_PARAM = 1;
  public static final int ROBOT_Z_PARAM = 2;

  public static final int ROBOT_X_ROTATE = 3;
  public static final int ROBOT_Y_ROTATE = 4;
  public static final int ROBOT_Z_ROTATE = 5;

  public static final int JOINT_1_ROTATE  = 6;
  public static final int JOINT_2_ROTATE  = 7;

  //Initialise robot 2 parameters
  public static final int ROBOT2_X_PARAM = 8;
  public static final int ROBOT2_Y_PARAM = 9;
  public static final int ROBOT2_Z_PARAM = 10;

  public static final int ROBOT2_X_ROTATE = 11;
  public static final int ROBOT2_Y_ROTATE = 12;
  public static final int ROBOT2_Z_ROTATE = 13;

  public static final int ROBOT2_JOINT_1_ROTATE  = 14;
  public static final int ROBOT2_JOINT_2_ROTATE  = 15;

  public static final int RING_Y_ROTATE = 16;


  public static final int MAX_PARAMS = 18;
  private Anim[] param;
  private int numParams;
  private double globalStartTime, localTime, repeatTime, savedLocalTime;

  /**
   * Constructor.
   *
   * @param keys List of key info, i.e. list of pairs {key frame value, key parameter value}
   */
  public AnimationScene() {
    param = new Anim[MAX_PARAMS];

      //Code added in by Mahesha Kulatunga
    // ROBOT 1 ANIMATION PARAMS
    param[ROBOT_X_PARAM] = create(0.0, 10.0, true, true,
                                new double[]{0.0,-15.0, 0.25,15.0, 0.5,15.0,0.75,-15.0, 1.0,-15.0});
    param[ROBOT_Y_PARAM] = create(0.0, 10.0, true, true,
                                new double[]{0.0,7.5, 0.25,7.5, 0.5,7.5, 0.58,5.0, 0.64,5.0, 0.75,7.5, 1.0,7.5});
    param[ROBOT_Z_PARAM] = create(0.0, 10.0, true, true,
                                new double[]{0.0,15.0, 0.25,15.0, 0.5,-20.0,0.75,-20.0, 1.0,15.0});

    param[ROBOT_X_ROTATE] = create(0.0, 10.0, true, true,
                                new double[]{0.0,-20.0, 0.10,0.0, 0.15,0.0, 0.25,-20.0, 0.35,0.0, 0.40,0.0, 0.50,-20.0, 0.60,0.0, 0.65,0.0, 0.75,-20.0, 0.85,0.0, 0.90,0.0, 1.0,-20.0});
    param[ROBOT_Y_ROTATE] = create(0.0, 10.0, true, true,
                                new double[]{0.0,-45.5, 0.08,0.0, 0.16,0.0, 0.24,45.0, 0.32,90.0, 0.38,90.0, 0.49,130.0, 0.58,180.0, 0.64,180.0, 0.74,225.0, 0.84,270.0, 0.88,270.0, 1.0,315.0});
    param[ROBOT_Z_ROTATE] = create(0.0, 10.0, true, true,
                                new double[]{0.0,0.0, 0.48,0.0, 0.54,-20.0, 0.58,0.0, 0.64,0.0, 0.7,20.0, 0.77,0.0, 1.0,0.0});

    param[JOINT_1_ROTATE] = create(0.0, 10.0, true, true,
                              new double[]{0.0,0.0, 0.3,0.0, 0.325,45.0, 0.45, 0.0, 0.8, 0.0, 0.875, 45.0, 0.95, 0.0, 1.0,0.0});
    param[JOINT_2_ROTATE] = create(0.0, 10.0, true, true,
                              new double[]{0.0,0.0, 0.3,0.0, 0.325,90.0, 0.45, 0.0, 0.8, 0.0, 0.875, 90.0, 0.95, 0.0, 1.0,0.0});

      // ROBOT 2 ANIMATION PARAMS
    param[ROBOT2_X_PARAM] = create(2.0, 10.0, true, true,
                                new double[]{0.0,-50.0, 0.27,0.0, 0.5,0.0 ,0.75,30.0, 1.0,30.0});
    param[ROBOT2_Y_PARAM] = create(2.0, 10.0, true, true,
                                new double[]{0.0,7.5, 1.0,7.5});
    param[ROBOT2_Z_PARAM] = create(2.0, 10.0, true, true,
                                new double[]{0.0,30.0, 1.0,30.0});

    param[ROBOT2_X_ROTATE] = create(2.0, 10.0, true, true,
                                new double[]{0.0,0.0, 0.10,0.0, 0.15,0.0, 0.25,-20.0, 0.35,0.0, 0.40,0.0, 0.50,0.0, 0.60,20.0, 0.65,0.0, 0.75,0.0, 0.85,0.0, 0.90,0.0, 1.0,0.0});
    param[ROBOT2_Y_ROTATE] = create(2.0, 10.0, true, true,
                                new double[]{0.0,0.0, 0.08,0.0, 0.16,0.0, 0.24,45.0, 0.32,90.0, 0.38,90.0, 0.49,90.0, 0.58,90.0, 0.64,0.0, 0.74,0.0, 0.84,0.0, 0.88,0.0, 1.0,0.0});
    param[ROBOT2_Z_ROTATE] = create(2.0, 10.0, true, true,
                                new double[]{0.0,0.0, 0.48,0.0, 0.54,-20.0, 0.58,0.0, 0.64,0.0, 0.7,20.0, 0.77,0.0, 1.0,0.0});

    param[ROBOT2_JOINT_1_ROTATE] = create(2.0, 10.0, true, true,
                              new double[]{0.0,0.0, 0.33,0.0, 0.37,-35.0, 0.41,-35, 0.45,-35.0, 0.49,0.0, 1.0, 0.0});
    param[ROBOT2_JOINT_2_ROTATE] = create(2.0, 10.0, true, true,
                              new double[]{0.0,0.0, 0.33,0.0, 0.37,-65.0, 0.41,0.0, 0.45,-65.0, 0.49,0.0, 1.0, 0.0});


    param[RING_Y_ROTATE] = create(0.0, 10.0, true, true,
                              new double[]{0.0,0.0, 0.125,180.0, 0.25,180.0, 0.325,360.0, 0.5,360.0 , 0.625,540.0 , 0.75,540.0 , 0.875,720.0 , 1.0, 720.0});


    numParams = RING_Y_ROTATE+1;
    localTime = 0;
    savedLocalTime = 0;
    repeatTime = 10;
    globalStartTime = getSeconds();
  }

  public Anim create (double start, double duration, boolean pre, boolean post, double[] data) {
    KeyInfo[] k = new KeyInfo[data.length/2];
    for (int i=0; i<data.length/2; ++i) {
      k[i] = new KeyInfo(data[i*2], data[i*2+1]);
    }
    return new Anim(start, duration, pre, post, k);
  }

  public void startAnimation() {
    globalStartTime = getSeconds() - savedLocalTime;
  }

  public void pauseAnimation() {
    savedLocalTime = getSeconds() - globalStartTime;
  }

  public void reset() {
    globalStartTime = getSeconds();
    savedLocalTime = 0;
    for (int i=0; i<numParams; ++i) {
      param[i].reset();
    }
  }

  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  /**
   *
   */
  public void update() {
    localTime = getSeconds() - globalStartTime;
    if (localTime > repeatTime) {
      globalStartTime = getSeconds();
      localTime = 0;
      savedLocalTime = 0;
    }
    for (int i=0; i<numParams; ++i) {
      param[i].update(localTime);
    }
  }

 /**
   *
   *
   * @return The current parameter value
   */
  public double getParam(int i) {
    if (i<0 || i>=numParams) {
      System.out.println("EEError: parameter out of range");
      return 0;
    }
    else {
      return param[i].getCurrValue();
    }
  }

  /**
   * Standard use of toString method
   *
   * @return A string representing the key data
   */
  public String toString() {
    String s = "Anim manager: ";
    return s;
  }

  public static void main(String[] args) {
    AnimationScene a = new AnimationScene();
    System.out.println(a.getParam(a.ROBOT_X_PARAM));
    double start = a.getSeconds();
    double t=start;
    while (t<start+20) {
      double ls = a.getSeconds();
      double lt = ls;
      while (lt < ls+0.2) lt = a.getSeconds();
      a.update();
      System.out.println(a.localTime + ", " + a.getParam(a.ROBOT_X_PARAM));
      t = a.getSeconds();
    }
  }

}
