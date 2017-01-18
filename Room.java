/*
 * This class is responsible for generating the room, with textures walls and obstacles
 *
 * @author Mahesha Kulatunga mkkulatunga1@sheffield.ac.uk
 * I declare that this code is my own work
 * Created 16th November 2016
 * Last Edited 28th November 2016
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

public class Room {

  private Mesh floorPlane, backgroundPlane, wallPlane, windowPlane;
  private Render floor, background, wall, window;

  private GLU glu = new GLU();
  private GLUT glut = new GLUT();

  //Initialise textures
  Texture wallTex, windowTex, floorTex, outsideTex;

  //Renders textured objects in RobotSetScene
  public void createRenderObjects(GL2 gl) {
    createTextured(gl);
  }

  //Loads textures and links them to meshes. Creates render objects
  public void createTextured(GL2 gl){
    // Load the relevant textures
    floorTex = loadTexture(gl, "Textures/floor.jpg");
    wallTex = loadTexture(gl, "Textures/wall.jpg");
    windowTex = loadTexture(gl, "Textures/window.png");
    outsideTex = loadTexture(gl, "Textures/outside.jpg");

    // Create mesh for different textured parts
    floorPlane = ProceduralMeshFactory.createPlane(10,10,10,10,1,1);
    wallPlane = ProceduralMeshFactory.createPlane(5,5,10,10,1,1);
    windowPlane = ProceduralMeshFactory.createPlane(5,5,10,10,1,1);
    backgroundPlane = ProceduralMeshFactory.createPlane(120,75,10,10,1,1);

    // Create a new Render object for all parts
    floor = new Render(floorPlane, floorTex);
    floor.initialiseDisplayList(gl, true);

    background = new Render(backgroundPlane, outsideTex);
    background.initialiseDisplayList(gl, true);

    wall = new Render(wallPlane, wallTex);
    wall.initialiseDisplayList(gl, true);

    window = new Render(windowPlane, windowTex);
    window.initialiseDisplayList(gl, true);
  }

  //Brings all parts together to generate the entire room with obstacles
  public void displayRoom(GL2 gl, Double ringRotate) {
    //Floor
    renderFloor(gl);
    //Render light bulbs
    renderLights(gl);
    //Roof
    gl.glPushMatrix();
      gl.glTranslated(0.0f,25.0f,0.0f);
      gl.glRotated(180.0f,1,0,0);
      renderFloor(gl);
    gl.glPopMatrix();

    //Walls
    gl.glPushMatrix();
      gl.glTranslated(25.0f,12.5f,0.0f);
      gl.glRotated(90.0f,0,0,1);
      gl.glRotated(90.0f,0,1,0);
      renderWall(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
      gl.glTranslated(-25.0f,12.5f,0.0f);
      gl.glRotated(-90.0f,0,0,1);
      gl.glRotated(90.0f,0,1,0);
      renderWall(gl);
    gl.glPopMatrix();

    gl.glPushMatrix();
      gl.glTranslated(0.0f,12.5f,-25.0f);
      gl.glRotated(90.0f,1,0,0);
      renderWall(gl);
    gl.glPopMatrix();
    // Windowed wall
    gl.glPushMatrix();
      gl.glTranslated(-22.5f,2.5f,25.0f);
      gl.glRotated(270.0f,1,0,0);
      renderWindowedWall(gl);
    gl.glPopMatrix();
    // Background behind windowed wall
    gl.glPushMatrix();
      gl.glTranslated(0.0f,8.5f,75.0f);
      gl.glRotated(270.0f,1,0,0);
      gl.glRotated(180.0f,0,1,0);
      background.renderDisplayList(gl);
    gl.glPopMatrix();
    //Render obstacles
    renderObstacles(gl, ringRotate);
  }

  //Creates standard textured wall
  private void renderWall(GL2 gl) {
    Integer x;
    Integer y;
    for (x = 0 ; x < 5 ; x+=1){
      for (y = 0 ; y < 10 ; y+=1){
          gl.glPushMatrix();
            gl.glTranslated(-22.5f,0.0f,-10.0f);
            gl.glTranslated(y*5.0f,0.0f,x*5.0f);
            wall.renderDisplayList(gl);
          gl.glPopMatrix();
      }
    }
  }

  //Creates windowed wall, with free space in rows 1,2,3 to create a wall with a large centre window
  private void renderWindowedWall(GL2 gl) {
    Integer x;
    Integer y;
    for (x = 0 ; x < 5 ; x+=1){
      for (y = 0 ; y < 10 ; y+=1){
        if (x == 1 || x == 2  || x == 3) {
        }
        else {
          gl.glPushMatrix();
            gl.glTranslated(y*5.0f,0.0f,x*5.0f);
            window.renderDisplayList(gl);
          gl.glPopMatrix();
        }
      }
    }
  }

  //Creates square floor
  private void renderFloor(GL2 gl) {
    Integer x;
    Integer y;
    for (x = 0 ; x < 5 ; x+=1){
      for (y = 0 ; y < 5 ; y+=1){
          gl.glPushMatrix();
            gl.glTranslated(-20.0f,0.0f,-20.0f);
            gl.glTranslated(y*10.0f,0.0f,x*10.0f);
            floor.renderDisplayList(gl);
          gl.glPopMatrix();
      }
    }
  }

  //Renders spheres where spotlights are to act as light bulbs
  private void renderLights(GL2 gl) {
    gl.glPushMatrix();
      gl.glTranslated(12.0f,25.0f,0.0f);
      setColour(gl, 2.0f, 2.0f, 2.0f, 1.0f);
      glut.glutSolidSphere(1.0f, 100, 100);
    gl.glPopMatrix();

    gl.glPushMatrix();
      gl.glTranslated(-12.0f,25.0f,0.0f);
      setColour(gl, 2.0f, 2.0f, 2.0f, 1.0f);
      glut.glutSolidSphere(1.0f, 100, 100);
    gl.glPopMatrix();
  }

  //Creates all obstacles. Parameter ringRotate is the value the orange ring must be rotated by as it is animated
  private void renderObstacles(GL2 gl, Double ringRotate) {
    // Blue ring with base
    gl.glPushMatrix();
      gl.glTranslated(-15.0f,0.0f,0.0f);
      setColour(gl, 0.0f, 0.0f, 1.0f, 1.0f);
      gl.glPushMatrix();
        gl.glTranslated(0.0f,11.0f,0.0f);
        gl.glScaled(0.6f,1.0f,1.0f);
        glut.glutSolidTorus(0.5,5.0,50, 50);
      gl.glPopMatrix();

      gl.glPushMatrix();
        gl.glTranslated(0.0f,6.0f,0.0f);
        gl.glScaled(0.5f,6.0f,0.5f);
        gl.glRotated(90.0,1,0,0);
        glut.glutSolidCylinder(1.0, 1.0, 20, 20);
      gl.glPopMatrix();
    gl.glPopMatrix();

    //Orange ring with base
    gl.glPushMatrix();
      gl.glTranslated(15.0f,0.0f,0.0f);
      setColour(gl, 1.0f, 0.25f, 0.0f, 1.0f);
        gl.glPushMatrix();
          gl.glTranslated(0.0f,11.0f,0.0f);
          gl.glScaled(0.6f,1.0f,1.0f);
          gl.glRotated(ringRotate,0,1,0);
          setColour(gl, 1.0f, 0.25f, 0.0f, 1.0f);
          glut.glutSolidTorus(0.5,5.0,50, 50);
        gl.glPopMatrix();

        gl.glPushMatrix();
          gl.glTranslated(0.0f,25.0f,0.0f);
          gl.glScaled(0.5f,9.0f,0.5f);
          gl.glRotated(90.0,1,0,0);
          glut.glutSolidCylinder(1.0, 1.0, 20, 20);
        gl.glPopMatrix();
    gl.glPopMatrix();

    //White box
    gl.glPushMatrix();
      gl.glTranslated(0.0f,12.5f,-21.5f);
      gl.glScaled(10.0f,7.0f,7.0f);
      gl.glTranslated(0.0f,0.51f,0.0f);
      setColour(gl, 0.75f, 0.75f, 0.75f, 1.0f);
      glut.glutSolidCube(1.0f);
    gl.glPopMatrix();

  }

  //Sets the colour of the glut objects
  private void setColour(GL2 gl, Float r, Float g, Float b, Float a) {
    gl.glPushMatrix();
      float[] eyeAmbientDiffuse = new float[4];
      eyeAmbientDiffuse[0] = r;
      eyeAmbientDiffuse[1] = g;
      eyeAmbientDiffuse[2] = b;
      eyeAmbientDiffuse[3] = a;
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, eyeAmbientDiffuse, 0);
    gl.glPopMatrix();
  }

  //Loads a texture file
  private Texture loadTexture(GL2 gl, String filename) {
    Texture tex = null;
    // since file loading is involved, must use try...catch
    try {
      File f = new File(filename);

      // The following line results in a texture that is flipped vertically (i.e. is upside down)
      // due to OpenGL and Java (0,0) position being different:
      // tex = TextureIO.newTexture(new File(filename), false);

      // So, instead, use the following three lines which flip the image vertically:
      BufferedImage img = ImageIO.read(f); // read file into BufferedImage
      ImageUtil.flipImageVertically(img);

	    // No mip-mapping.
      tex = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);

      // Different filter settings can be used to give different effects when the texture
      // is applied to a set of polygons.
      tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
      tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);

    }
    catch(Exception e) {
      System.out.println("Error loading texture " + filename);
    }
    return tex;
  } // end of loadTexture()


}
