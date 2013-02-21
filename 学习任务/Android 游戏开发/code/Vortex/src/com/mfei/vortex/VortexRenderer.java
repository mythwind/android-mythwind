package com.mfei.vortex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class VortexRenderer implements GLSurfaceView.Renderer {

	private ShortBuffer indexBuffer;
	private FloatBuffer vertexBuffer;
	private short[] indicesArray = { 0, 1, 2 };
	private int vertices = 3;
	
	private float yAngle;
	
	private float red = 0.9f;
	private float green = 0.2f;
	private float blue = 0.2f;
	
	public void setColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public void setAngle(float yAngle) {
		this.yAngle = yAngle;
	}
	
	private void initTriangle() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(vertices * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = vbb.asShortBuffer();
		
		float[] coords = {
				-0.5f, -0.5f, 0f,  // (x1, y1, z1)
				0.5f, -0.5f, 0f,  // (x2, y2, z2)
				0f, 0.5f, 0f  // (x3, y3, z3)
		};
		vertexBuffer.put(coords); // 三角形坐标
		vertexBuffer.position(0);
		indexBuffer.put(indicesArray);
		indexBuffer.position(0);
	}
	
	/**
	 *   绘制的时候调用？
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// 定义底色的颜色
		gl.glClearColor(red, green, blue, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// 沿着Y轴旋转
		gl.glRotatef(yAngle, 0f, 1f, 0f);
		// 设置三角形为暗红色
		gl.glColor4f(0.5f, 0f, 0f, 0.5f);
		// 初始化Vertex Pointer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, vertices, GL10.GL_UNSIGNED_SHORT, indexBuffer);
	}
	/**
	 *   Surface 发生改变时调用
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
	}
	/**
	 *   创建 Surface 时调用
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		initTriangle();
	}

}
