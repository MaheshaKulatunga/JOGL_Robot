/*
 * This class is responsible for creating the scene, rendering the Robot, Room and setting up the lighting
 *
 * @author Mahesha Kulatunga mkkulatunga1@sheffield.ac.uk
 * I declare that this code is my own work
 * Created 16th November 2016
 * Last Edited (02/12/2016)
*/

import java.io.File;
import java.awt.image.*;
import javax.imageio.*;
import com.jogamp.opengl.util.awt.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class RobotSetScene {

  private GLU glu = new GLU();
  private GLUT glut = new GLUT();

  private final double INC_ROTATE=2.0;

  // Light setting constants
  public static final float[] SUN_LIGHT = {0.1f,0.05f,0.0f};
  public static final float[] SUN_AMBIENT = {0.5f,0.5f,0.5f};
  public static final float[] BACKGROUND_LIGHT = {0.3f,0.3f,0.3f};
  public static final float[] BACKGROUND_AMBIENT = {0.0f,0.0f,0.0f};

  private int canvaswidth=0, canvasheight=0;

  // Initialise objects
  private Light backgroundLight1, backgroundLight2, light1, light2, sun;
  private Camera camera;

  private Room room;
  private Bot bot;

  private AnimationScene animationScene;

  // Initialises scene
  public RobotSetScene(GL2 gl, Camera camera) {
    animationScene = new AnimationScene();

    generalLighting();
    this.camera = camera;
    room = new Room();
	  bot = new Bot();
    room.createRenderObjects(gl);
    bot.initHeadLamp();
  }

  // if user resizes the window
  public void setCanvasSize(int w, int h) {
    canvaswidth=w;
    canvasheight=h;
  }
  // resets scene
  public void reset() {
    animationScene.reset();
  }
  // starts animation
  public void startAnimation() {
    animationScene.startAnimation();
  }
  // pauses animation
  public void pauseAnimation() {
    animationScene.pauseAnimation();
  }
  // updated scene as animation is carried out
  public void update() {
    animationScene.update();
  }

  //Renders all objects in scene and feeds animation xyz values to animated objects
  public void render(GL2 gl) {
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    camera.view(glu);      // Orientate the camera

	// Lighting
    doBackgroundLight1(gl);
    doBackgroundLight2(gl);
    doLight1(gl);
    doLight2(gl);
    doSun(gl);

	//Render Room
    gl.glPopMatrix();
      double ringY = animationScene.getParam(animationScene.RING_Y_ROTATE);
      room.displayRoom(gl, ringY);
    gl.glPopMatrix();

  //Render Robot
    gl.glPopMatrix();
      //Get values
      double robot2X = animationScene.getParam(animationScene.ROBOT2_X_PARAM);
      double robot2Y = animationScene.getParam(animationScene.ROBOT2_Y_PARAM);
      double robot2Z = animationScene.getParam(animationScene.ROBOT2_Z_PARAM);

      double robot2RotateX = animationScene.getParam(animationScene.ROBOT2_X_ROTATE);
      double robot2RotateY = animationScene.getParam(animationScene.ROBOT2_Y_ROTATE);
      double robot2RotateZ = animationScene.getParam(animationScene.ROBOT2_Z_ROTATE);

      double robot2JointOne = animationScene.getParam(animationScene.ROBOT2_JOINT_1_ROTATE);
      double robot2JointTwo = animationScene.getParam(animationScene.ROBOT2_JOINT_2_ROTATE);

      bot.displayRobot(gl, 0.0f, 0.5f, 0.0f,robot2X ,robot2Y ,robot2Z,robot2RotateX,robot2RotateY,robot2RotateZ,robot2JointOne,robot2JointTwo);
    gl.glPopMatrix();

    gl.glPushMatrix();
      double robotX = animationScene.getParam(animationScene.ROBOT_X_PARAM);
      double robotY = animationScene.getParam(animationScene.ROBOT_Y_PARAM);
      double robotZ = animationScene.getParam(animationScene.ROBOT_Z_PARAM);

      double robotRotateX = animationScene.getParam(animationScene.ROBOT_X_ROTATE);
      double robotRotateY = animationScene.getParam(animationScene.ROBOT_Y_ROTATE);
      double robotRotateZ = animationScene.getParam(animationScene.ROBOT_Z_ROTATE);

      double jointOne = animationScene.getParam(animationScene.JOINT_1_ROTATE);
      double jointTwo = animationScene.getParam(animationScene.JOINT_2_ROTATE);

      bot.displayRobot(gl, 10.0f, 0.0f, 0.0f, robotX,robotY,robotZ ,robotRotateX , robotRotateY, robotRotateZ , jointOne, jointTwo);
    gl.glPopMatrix();
  }


  // lighting -----------------------------------------------------------
  private void generalLighting(){
    float[] backgroundspotlightPosition1 = {10.0f,10.0f,10.0f,1};
    backgroundLight1 = new Light(GL2.GL_LIGHT0, backgroundspotlightPosition1,BACKGROUND_AMBIENT,BACKGROUND_LIGHT,BACKGROUND_LIGHT,true);

    float[] backgroundspotlightPosition2 = {-10.0f,10.0f,-10.0f,1};
    backgroundLight2 = new Light(GL2.GL_LIGHT1, backgroundspotlightPosition2,BACKGROUND_AMBIENT,BACKGROUND_LIGHT,BACKGROUND_LIGHT,true);

    float[] spotlightPosition1 = {12.5f,25,0,1};
    light1 = new Light(GL2.GL_LIGHT2, spotlightPosition1);
    float[] lightDirection1 = {0f,-1f,0f};
    light1.makeSpotlight(lightDirection1, 20f);

    float[] spotlightPosition2 = {-12.5f,25,0,1};
    light2 = new Light(GL2.GL_LIGHT3, spotlightPosition2);
    float[] lightDirection2 = {0f,-1f,0f};
    light2.makeSpotlight(lightDirection2, 20f);

    float[] sunPosition = {0.0f,35.0f,65.0f,1};
    sun = new Light(GL2.GL_LIGHT4, sunPosition, SUN_AMBIENT, SUN_LIGHT, SUN_LIGHT, true);

  }

  private void doBackgroundLight1(GL2 gl) {
   backgroundLight1.use(gl, glut, false);
  }

  private void doBackgroundLight2(GL2 gl) {
   backgroundLight2.use(gl, glut, false);
  }

  private void doSun(GL2 gl) {
   sun.use(gl, glut, false);
  }

  private void doLight1(GL2 gl) {
   light1.use(gl, glut, false);
  }

   private void doLight2(GL2 gl) {
    light2.use(gl, glut, false);
  }

  public Light getBGLight1() {
    return backgroundLight1;
  }

  public Light getBGLight2() {
    return backgroundLight2;
  }

  public Light getHL() {
    return bot.getHeadLamp();
  }

}
