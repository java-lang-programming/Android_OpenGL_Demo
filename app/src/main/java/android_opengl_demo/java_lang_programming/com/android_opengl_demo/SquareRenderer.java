package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SquareRenderer implements GLSurfaceView.Renderer {

    public static final String TAG = "SquareRenderer";

    // screen resolution
    int   mScreenWidth = 1280;
    float   mScreenHeight = 768;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        gl10.glMatrixMode(GL10.GL_PROJECTION);

        gl10.glLoadIdentity();
        // 座標系の設定
        // gl10.glOrthof(-1.0f, 1.0f, -1.0f, 1.0f,0.5f, -0.5f);
        gl10.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f,0.5f, -0.5f);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();

        gl10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // valid a blindingdd
        gl10.glEnable(GL10.GL_BLEND);
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // 座標
        float [] vertices = new float[]{
                -0.5f, -0.5f,
                0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, 0.5f,
        };

        // 色
        float [] colors = new float[]{
                1.0f, 1.0f, 0.0f, 0.5f,
                0.0f, 1.0f, 1.0f, 0.5f,
                0.0f, 0.0f, 0.0f, 0.5f,
                1.0f, 0.0f, 1.0f, 0.5f,
        };

        // メモリに確保
        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // The colors buffer.
        ByteBuffer dlb = ByteBuffer.allocateDirect(colors.length * 4);
        dlb.order(ByteOrder.nativeOrder());
        FloatBuffer colorsBuffer = dlb.asFloatBuffer();
        colorsBuffer.put(colors);
        colorsBuffer.position(0);

        gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl10.glDisable(GL10.GL_BLEND);
    }
}
