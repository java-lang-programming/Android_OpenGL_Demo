package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by msuzuki on 2016/07/30.
 */
public class SquareGl2Renderer implements GLSurfaceView.Renderer {

    public static final String TAG = "SquareRenderer";

    // screen resolution
    int   mScreenWidth = 1280;
    float   mScreenHeight = 768;

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];//モデルビュー変換用行列

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];//視野変換用行列

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];//投影変換用行列

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];//下の三つを乗算した行列

    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;

    /** This will be used to pass in model position information. */
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    private int mColorHandle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        float aspect = (float) width / height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        //gl10.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        GLES20.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        //gl10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20090829
        // 詳しく説明する必要あり。俺でもわかるように。
        // 座標系
        // http://www.programmingmat.jp/android_lab/gles20_matrix.html
        //gl10.glOrthof(-1.0f, 1.0f, -1.5f, 1.5f,0.5f, -0.5f);
        // http://www.songho.ca/opengl/gl_projectionmatrix.html
        Matrix.orthoM(mProjectionMatrix, 0,
                -1.0f, 1.0f,//左端x→右端y
                -1.5f, 1.5f, //下端→上端
                -0.5f, 0.5f //手前→奥
        );

//
//        // Position the eye behind the origin.
//        final float eyeX = 0.0f;
//        final float eyeY = 0.0f;
//        final float eyeZ = 1.5f;
//
//        // We are looking toward the distance
//        final float lookX = 0.0f;
//        final float lookY = 0.0f;
//        final float lookZ = -5.0f;
//
//        // Set our up vector. This is where our head would be pointing were we holding the camera.
//        final float upX = 0.0f;
//        final float upY = 1.0f;
//        final float upZ = 0.0f;

//        Matrix.setLookAtM(viewMatrix, 0,
//                eye[0], eye[1], eye[2],
//                center[0], center[1], center[2],
//                up[0], up[1], up[2]);


        // 座標
        float [] vertices = new float[]{
                // X, Y, Z
                // R, G, B, A
                -0.5f, -0.5f, 0.0f,
                1.0f, 1.0f, 0.0f, 1.0f,

                0.5f, -0.5f, 0.0f,
                0.0f, 1.0f, 1.0f, 1.0f,

                -0.5f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f,

                0.5f, 0.5f, 0.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
        };

        // メモリに確保
        // The vertex buffer.
        FloatBuffer vertexBuffer = GraphicUtil.getFloatBuffer(vertices);
        vertexBuffer.put(vertices);


        final String vertexShader =
                "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.

                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.

                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.

                        + "void main()                    \n"		// The entry point for our vertex shader.
                        + "{                              \n"
                        + "   v_Color = a_Color;          \n"		// Pass the color through to the fragment shader.
                        // It will be interpolated across the triangle.
                        + "   gl_Position = u_MVPMatrix   \n" 	// gl_Position is a special variable used to store the final position.
                        + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
                        + "}                              \n";    // normalized screen coordinates.

        final String fragmentShader =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "void main()                    \n"		// The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.
                        + "}                              \n";

        // Load in the vertex shader.
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        if (vertexShaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(vertexShaderHandle, vertexShader);

            // Compile the shader.
            GLES20.glCompileShader(vertexShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                GLES20.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }

        if (vertexShaderHandle == 0)
        {
            throw new RuntimeException("Error creating vertex shader.");
        }

        // Load in the fragment shader shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (fragmentShaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

            // Compile the shader.
            GLES20.glCompileShader(fragmentShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                GLES20.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }

        if (fragmentShaderHandle == 0)
        {
            throw new RuntimeException("Error creating fragment shader.");
        }

        // Create a program object and store the handle to it.
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
            GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        // VertexシェーダーコードのvPos変数の番号を取得
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(programHandle);

        Matrix.setIdentityM(mModelMatrix, 0);
//
//
//        //gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                7 * 4, vertexBuffer);
//
//        // gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
//
//
//        // color buffer
        vertexBuffer.position(3);
//
//        // gl10.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
                7 * 4, vertexBuffer);
//
//        // gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        GLES20.glEnableVertexAttribArray(mColorHandle);
//
//        //gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//
        //GLES20.glDisableVertexAttribArray(mPositionHandle);

        //gl10.glDisable(GL10.GL_BLEND);

    }
}
