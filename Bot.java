/*
 * This class is responsible for creating the Robot
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

public class Bot {

	private GLU glu = new GLU();
	private GLUT glut = new GLUT();

	private Light headLamp;
	public static final float[] HEAD_LAMP = {2.0f,0.0f,0.0f};
	public static final float[] LAMP_AMBIENT = {1.0f,1.0f,1.0f};

	//Brings all the parts of the robot to create the final robot
	public void displayRobot(GL2 gl, Float eyeR, Float eyeG, Float eyeB, Double x, Double y, Double z, Double rx, Double ry, Double rz, Double jointOne, Double jointTwo) {
		gl.glPushMatrix();
			// Animation transformations
			gl.glTranslated(x,y,z);
			gl.glRotated(ry,0,1,0);
			gl.glRotated(rx,1,0,0);
			gl.glRotated(rz,0,0,1);

			//Renders all the parts of the body
			renderBody(gl, eyeR, eyeG, eyeB);
			doHeadLamp(gl);
			gl.glPushMatrix();
				gl.glTranslated(0.0f,3.0f,1.0f);
				renderArm(gl, jointOne, jointTwo);
			gl.glPopMatrix();

			gl.glPushMatrix();
				gl.glTranslated(0.0f,3.0f,-1.0f);
				gl.glRotated(180.0f,1,0,0);
				renderArm(gl, (-1*jointOne),(-1*jointTwo));
			gl.glPopMatrix();
		gl.glPopMatrix();
  }

	//Renders a robot arm
	private void renderArm(GL2 gl, Double jointOne, Double jointTwo) {
		gl.glPushMatrix();
			gl.glPushMatrix();
					gl.glRotated(jointOne,1,0,0);// ROTATE JOINT 1
					gl.glPushMatrix();
						gl.glTranslated(0.0f,0.0f,1.0f);
							gl.glRotated(jointTwo,1,0,0);// ROTATE JOINT 2
							gl.glPushMatrix();
								renderDrill(gl);
								renderWing(gl,0.5f);
							gl.glPopMatrix();
						renderJoint(gl);
					gl.glPopMatrix();
				renderWing(gl,0.5f);
			gl.glPopMatrix();
			renderJoint(gl);
	  gl.glPopMatrix();
	}

	//Renders the drill at the end of the robot arm
	private void renderDrill(GL2 gl) {
		gl.glPushMatrix();
			gl.glPushMatrix();
				gl.glTranslated(1.1f,0.0f,1.0f);
				gl.glRotated(90.0,0,1,0);
				gl.glTranslated(0.0f,0.0f,-0.55f);
				setColour(gl, 0.2f, 0.2f, 0.2f, 1.0f);
				glut.glutSolidCone(0.3, 0.5, 20, 20);
			gl.glPopMatrix();

			gl.glPushMatrix();
				gl.glTranslated(0.0f,0.0f,1.0f);
				gl.glRotated(90.0,0,1,0);
				gl.glTranslated(0.0f,0.0f,-0.55f);
				setColour(gl, 0.2f, 0.2f, 0.2f, 1.0f);
				glut.glutSolidCylinder(0.15, 1.10, 20, 20);
			gl.glPopMatrix();
		gl.glPopMatrix();
	}

	//renders part of the robot arm
	private void renderWing(GL2 gl, Float x) {
		gl.glPushMatrix();
			gl.glTranslated(0.0f,0.0f,x);
			gl.glScaled(1.0f,0.2f,1.0f);
			setColour(gl, 0.4f, 0.4f, 0.4f, 1.0f);
			glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
	}

	//renders joint of the robot arm
	private void renderJoint(GL2 gl) {
		gl.glPushMatrix();
			gl.glRotated(90.0,0,1,0);
			gl.glTranslated(0.0f,0.0f,-0.55f);
			setColour(gl, 0.2f, 0.2f, 0.2f, 1.0f);
			glut.glutSolidCylinder(0.15, 1.10, 20, 20);
		gl.glPopMatrix();
	}

	//renders body of the robot with eye and halo
	private void renderBody(GL2 gl, Float eyeR, Float eyeG, Float eyeB) {
		gl.glPushMatrix();
			//Body
			gl.glPushMatrix();
				gl.glScaled(1.0f,3.0f,1.0f);
				gl.glTranslated(0.0f,1.0f,0.0f);
				setColour(gl,1.0f,1.0f,1.0f,1.0f);
				glut.glutSolidSphere(1.0f, 100, 100);
			gl.glPopMatrix();
			// Red eye
			gl.glPushMatrix();
				gl.glTranslated(0.6f,4.0f,0.0f);
				gl.glScaled(0.5f,0.5f,0.5f);
				gl.glTranslated(0.0f,1.0f,0.0f);
				setColour(gl, eyeR, eyeG, eyeB, 10.0f);
				glut.glutSolidSphere(1.0f, 100, 100);
			gl.glPopMatrix();
			// Halo
			gl.glPushMatrix();
				gl.glTranslated(0.0f,5.7f,0.0f);
				gl.glRotated(90.0,1,0,0);
				setColour(gl, 1.0f, 1.0f, 0.0f, 0.0f);
				glut.glutSolidTorus(0.15, 0.70, 10, 10);
			gl.glPopMatrix();
		gl.glPopMatrix();
	}

	//Sets colour of glut objects
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

	//Initialises head lamp
	public void initHeadLamp(){
		float[] headLampPosition1 = {1.0f,5,0,1};
		headLamp = new Light(GL2.GL_LIGHT5, headLampPosition1, LAMP_AMBIENT, HEAD_LAMP, HEAD_LAMP, true);
		float[] headLightDirection = {1f,-0.8f,0f}; // direction from position to origin
		headLamp.makeSpotlight(headLightDirection, 20f);
	}

  private void doHeadLamp(GL2 gl) {
    headLamp.use(gl, glut, false);
  }

	public Light getHeadLamp() {
		return headLamp;
	}

}
