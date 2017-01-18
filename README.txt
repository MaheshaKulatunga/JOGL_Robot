COM3503 Robot Animation Assigment - aca14mkk (Mahesha Kulatunga) 
-----------------------------------------------------------------
USED CODE PROVIDED BY DR STEVE MADDOCK 

Running the program
-------------------
The main class is the 'Robot' class. To run the program compile all the files and run Robot.java. 

Advanced work
-------------
A second Robot has been added with a different animation. 
Video of animation can be found at http://maheshak.com/demo.html

Exersize sheet files used (PROVIDED BY DR STEVE MADDOCK) 
-------------------------
Anim.java
BoundingBox.java
Camera.java
KeyInfo.java
Light.java
Material.java
Mesh.java
ProceduralMeshFactory.java
Render.java
Triangle.java
Vertex.java
AnimationScene.java (EDITED)


Robot.java
----------
Main class, initializes window, scene and actionListers. Some code taken from main classes of echeet6. Contains UI controls to turn background lights, head lamp on/off 
as well as start/pause/reset animations.  

RobotSetScene.java
------------------
Creates animation scene, renders robot using Bot class, the room using Room class. Initialises camera and animation scene. 
Handles all room lighting, creates 2 background lights that can be turned on and off. One background light that acts as a 
constant ambiant light. Two spotlights in the middle of the room pointing down. 
Gets all values for animating the robots and ring obstacle. 

AnimationScene.java
-------------------
Edited from the AnimationScene.java in esheet7. Created parameters for: 
	- x,y,z for Robots 1 & 2
	- x,y,z rotation angles for Robots
	- rotation angle for robot arms
	- y rotation angle for ring
Each parameter has a start and stop time. Robot 1's animation is carried out throughout the animation period while robot 2
starts at 2seconds in. 

Bot.java
--------
Creates the robot using glut objects. The displayRobot function takes parameters for the colour of the robots eye, x, y, z
positon, rotation angles and arm rotation angles. 

Room.java
---------
Creates the room using textured meshes and glut objects. The displayRoom function takes parameter for the rotation angle for
the ring. 

Images
------
Images are found in the images folder.
