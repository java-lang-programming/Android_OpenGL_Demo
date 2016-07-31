package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CircleRenderer implements GLSurfaceView.Renderer {

    public static final String TAG = "CircleRenderer";

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
        gl10.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f,0.5f, -0.5f);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();

        gl10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

        /////  draw circle start /////


//        // 座標
//        float [] vertices = new float[]{
//                -0.5f, -0.5f,
//                0.5f, -0.5f,
//                -0.5f, 0.5f,
//                0.5f, 0.5f,
//        };

        //「ｎ角形」: n-sided polygon
        int n_sided_polygon = 8;
        float [] vertices = new float[n_sided_polygon * 3 * 2];

        float x = 0; // 中心
        float y = 0; // 中心
        float r = 1; // 半径
        float radius = 1.0f; // ラジアン

        int vertexId = 0;
        for (int i = 0; i < n_sided_polygon; i++) {
            // ラジアン

            float theta1 = (((float)360/(float)n_sided_polygon * (float)i)) / (float)180 * (float)Math.PI;// radius
            float theta2 = (((float)360/(float)n_sided_polygon * (float)(i+1))) / (float)180 * (float)Math.PI;
            //float radian1 = (((float)360/(float)n_sided_polygon * (float)i)) / (float)180;
            //float theta = ((float)360/(float)n_sided_polygon) * (i + 1);

            Log.d(TAG, "radian_theta" + i + ":" + theta1);
            Log.d(TAG, "radian_theta2-" + i + ":" + theta2);

            // i番目の三角形
            vertices[vertexId++] = x;
            vertices[vertexId++] = y;

            // i番目の三角形の1番目の頂点
            //vertices[vertexId++] = (float)Math.cos(theta1) * radius + x; // x
            //vertices[vertexId++] = (float)Math.sin(theta1) * radius + y; // y
            vertices[vertexId++] = (float)Math.cos(theta1) + x; // x
            vertices[vertexId++] = (float)Math.sin(theta1) + y; // y

            // i番目の三角形の2番目の頂点
//            vertices[vertexId++] = (float)Math.cos(theta2) * radius + x; // x
//            vertices[vertexId++] = (float)Math.sin(theta2) * radius + y; // y
            vertices[vertexId++] = (float)Math.cos(theta2) + x; // x
            vertices[vertexId++] = (float)Math.sin(theta2) + y; // y
        }

//        // 色
//        float [] colors = new float[]{
//                1.0f, 1.0f, 0.0f, 1.0f,
//                0.0f, 1.0f, 1.0f, 1.0f,
//                0.0f, 0.0f, 0.0f, 0.0f,
//                1.0f, 0.0f, 1.0f, 1.0f,
//        };

        // メモリに確保
        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // color
        gl10.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);

//        // The colors buffer.
//        ByteBuffer dlb = ByteBuffer.allocateDirect(colors.length * 4);
//        dlb.order(ByteOrder.nativeOrder());
//        FloatBuffer colorsBuffer = dlb.asFloatBuffer();
//        colorsBuffer.put(colors);
//        colorsBuffer.position(0);

        gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
//        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, n_sided_polygon * 3);
    }
}
