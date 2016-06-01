package de.jkblume.sav.components.utils;

import java.awt.DisplayMode;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class CubeAnimator implements GLEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313675494223113008L;

	final private int width = 800;
	final private int height = 600;

	public DisplayMode dm, dm_old;
	private GLU glu = new GLU();
	
	private boolean init = false;

	private float base_rot_z = 0.0f, base_rot_y = 0.0f, base_rot_x = 0.0f;
	private float rot_z = 0.0f, rot_y = 0.0f, rot_x = 0.0f;



	public CubeAnimator() {

		// TODO Auto-generated method stub
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		// The canvas
		final GLCanvas glcanvas = new GLCanvas(capabilities);

		glcanvas.addGLEventListener(this);
		glcanvas.setSize(400, 400);

		final JFrame frame = new JFrame(" Textured Cube");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);

		animator.start();
	}
	
	public void setRotation(float x, float y, float z) {
		if (!init) {
			init = true;
//			this.base_rot_x = x;
//			this.base_rot_y = y;
			this.base_rot_z = z;
		}
		
		this.rot_x = x - this.base_rot_x;
		this.rot_y = y - this.base_rot_y;
		this.rot_z = z - this.base_rot_z;
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -20.0f);

		// Rotate The Cube On X, Y & Z
		gl.glRotatef(rot_z, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rot_x, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rot_y, 0.0f, 0.0f, 1.0f);

		// giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		
		gl.glColor3f(1f, 0f, 0f); // red color
		gl.glVertex3f(2.0f, 1.0f, -4.0f); // Top Right Of The Quad (Top)
		gl.glVertex3f(-2.0f, 1.0f, -4.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f(-2.0f, 1.0f, 4.0f); // Bottom Left Of The Quad (Top)
		gl.glVertex3f(2.0f, 1.0f, 4.0f); // Bottom Right Of The Quad (Top)

		gl.glColor3f(0f, 1f, 0f); // green color
		gl.glVertex3f(2.0f, -1.0f, 4.0f); // Top Right Of The Quad
		gl.glVertex3f(-2.0f, -1.0f, 4.0f); // Top Left Of The Quad
		gl.glVertex3f(-2.0f, -1.0f, -4.0f); // Bottom Left Of The Quad
		gl.glVertex3f(2.0f, -1.0f, -4.0f); // Bottom Right Of The Quad

		gl.glColor3f(0f, 0f, 1f); // blue color
		gl.glVertex3f(2.0f, 1.0f, 4.0f); // Top Right Of The Quad (Front)
		gl.glVertex3f(-2.0f, 1.0f, 4.0f); // Top Left Of The Quad (Front)
		gl.glVertex3f(-2.0f, -1.0f, 4.0f); // Bottom Left Of The Quad
		gl.glVertex3f(2.0f, -1.0f, 4.0f); // Bottom Right Of The Quad

		gl.glColor3f(1f, 1f, 0f); // yellow (red + green)
		gl.glVertex3f(2.0f, -1.0f, -4.0f); // Bottom Left Of The Quad
		gl.glVertex3f(-2.0f, -1.0f, -4.0f); // Bottom Right Of The Quad
		gl.glVertex3f(-2.0f, 1.0f, -4.0f); // Top Right Of The Quad (Back)
		gl.glVertex3f(2.0f, 1.0f, -4.0f); // Top Left Of The Quad (Back)

		gl.glColor3f(1f, 0f, 1f); // purple (red + green)
		gl.glVertex3f(-2.0f, 1.0f, 4.0f); // Top Right Of The Quad (Left)
		gl.glVertex3f(-2.0f, 1.0f, -4.0f); // Top Left Of The Quad (Left)
		gl.glVertex3f(-2.0f, -1.0f, -4.0f); // Bottom Left Of The Quad
		gl.glVertex3f(-2.0f, -1.0f, 4.0f); // Bottom Right Of The Quad

		gl.glColor3f(0f, 1f, 1f); // sky blue (blue +green)
		gl.glVertex3f(2.0f, 1.0f, -4.0f); // Top Right Of The Quad (Right)
		gl.glVertex3f(2.0f, 1.0f, 4.0f); // Top Left Of The Quad
		gl.glVertex3f(2.0f, -1.0f, 4.0f); // Bottom Left Of The Quad
		gl.glVertex3f(2.0f, -1.0f, -4.0f); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		if (height < 0)
			height = 1;

		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

}
