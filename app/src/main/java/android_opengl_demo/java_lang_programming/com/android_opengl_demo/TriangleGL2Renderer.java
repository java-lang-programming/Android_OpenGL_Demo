package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by msuzuki on 2016/07/31.
 */
public class TriangleGL2Renderer  implements GLSurfaceView.Renderer {

    public static final String TAG = "TriangleGL2Renderer";

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];//視野変換用行列

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];//投影変換用行列

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];//下の三つを乗算した行列

    // Vertexシェーダーコード
    // https://wgld.org/d/webgl/w003.html
//    private String vertexShaderCode =
//            "attribute vec4 vPos;attribute vec4 a_Color;varying vec4 vColor; void main() { vColor = a_Color;gl_Position = vPos; }";
    private String vertexShaderCode =
            "uniform mat4 u_PMatrix;attribute vec4 vPos;attribute vec4 a_Color;varying vec4 vColor; void main() { vColor = a_Color;gl_Position = u_PMatrix;*vPos; }";
//    private String vertexShaderCode =
//        "uniform mat4 u_MVPMatrix;attribute vec2 vPos;attribute vec4 a_Color;varying vec4 vColor; void main() { vColor = a_Color;gl_Position = u_MVPMatrix*vec4(vPos, 0.0, 1.0); }";

    // Fragmentシェーダーコード
//    private String fragmentShaderCode =
//            "void main() { gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0); }";
    private String fragmentShaderCode =
            "varying vec4 vColor;void main() { gl_FragColor = vColor; }";

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
        //gl10.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        GLES20.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        //gl10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //GLES20.glClearColor(0.0f, 0.5f, 1.0f, 1.0f); // 青
        //gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        int vshader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fshader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        // Vertexシェーダーのコードをコンパイル
        GLES20.glShaderSource(vshader, vertexShaderCode);
        GLES20.glCompileShader(vshader);

        // Fragmentシェーダーのコードをコンパイル
        GLES20.glShaderSource(fshader, fragmentShaderCode);
        GLES20.glCompileShader(fshader);

        // Programを作成
        int program = GLES20.glCreateProgram();

        // Programのシェーダーを設定
        GLES20.glAttachShader(program, vshader);
        GLES20.glAttachShader(program, fshader);

        // link a program object
        GLES20.glLinkProgram(program);

//        // 頂点データ作成
//        float[] vertexList = {0.0f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f};

        // 座標
        float [] vertexList = new float[]{
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

        // 頂点データをバッファに格納
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexList.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexList);
        vertexBuffer.position(0);

        // The colors buffer.
        ByteBuffer dlb = ByteBuffer.allocateDirect(colors.length * 4);
        dlb.order(ByteOrder.nativeOrder());
        FloatBuffer colorsBuffer = dlb.asFloatBuffer();
        colorsBuffer.put(colors);
        colorsBuffer.position(0);

        GLES20.glUseProgram(program);

        // VertexシェーダーコードのvPos変数の番号を取得
        int mPMatrixHandle = GLES20.glGetUniformLocation(program, "u_PMatrix;");
        int vPos = GLES20.glGetAttribLocation(program, "vPos");
        int vCol = GLES20.glGetAttribLocation(program, "a_Color");
        Log.d(TAG, "vPos :" + vPos);
        GLES20.glEnableVertexAttribArray(vPos);


        // バッファとvPosを結びつける
        GLES20.glVertexAttribPointer(vPos, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(vCol);

        GLES20.glVertexAttribPointer(vCol, 4, GLES20.GL_FLOAT, false, 0, colorsBuffer);

        Matrix.orthoM(mProjectionMatrix, 0,
                -1.0f, 1.0f,//左端x→右端y
                -1.5f, 1.5f, //下端→上端
                -0.5f, 0.5f //手前→奥
        );

//        // mScreenWidth, (int)mScreenHeight
//        Matrix.orthoM(mProjectionMatrix, 0,
//                0f, mScreenWidth,//左端x→右端y
//                mScreenHeight, 0f, //下端→上端
//                -1f, 1f //手前→奥
//        );

        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

//        // 透視投影変換・ビュー変換を乗じて頂点座標の変換行列を作成
        Matrix.multiplyMM(mViewMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
//
//        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(mPMatrixHandle, 1, false, mProjectionMatrix, 0);

        // 描画する頂点をVertexシェーダーに指定
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

//        gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
//        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
//        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
//        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(vPos);
        GLES20.glDisableVertexAttribArray(vCol);
    }
}
