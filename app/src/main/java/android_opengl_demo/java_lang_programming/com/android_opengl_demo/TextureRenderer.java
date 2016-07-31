package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by msuzuki on 2016/07/28.
 */
public class TextureRenderer implements GLSurfaceView.Renderer {

    public static final String TAG = "TextureRenderer";

    private Context mContext;

    boolean isAlphaBlend = true;

    // screen resolution
    int   mScreenWidth = 1280;
    float   mScreenHeight = 768;

    private int mTexture;

    public TextureRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;

        // Activityで管理できるように、グローバルで管理する
        Global.gl = gl10;

        // テキスチャの生成
        mTexture = GraphicUtil.loadTexture(gl10,
                mContext.getResources(),
                R.drawable.cat);

        Log.d(TAG, "texture load :" + mTexture);
        if (mTexture == 0) {
            Log.e(TAG, "texture load error!!");
        }
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

        if (isAlphaBlend) {
            // valid a blindingdd
            gl10.glEnable(GL10.GL_BLEND);
            gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        }

        // テクスチャーをはる
        drawTexture(gl10);

        if (isAlphaBlend) {
            // アルファブレンディングを無効
            gl10.glDisable(GL10.GL_BLEND);
        }
    }

    public void drawTexture(GL10 gl10) {
        // 座標
        float [] vertices = new float[]{
                -0.5f, -0.5f,
                0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, 0.5f,
        };

//        float [] vertices = new float[]{
//                -1.0f, -1.0f,
//                1.0f, -1.0f,
//                -1.0f, 1.0f,
//                1.0f, 1.0f,
//        };

        // 色
        float [] colors = new float[]{
                1.0f, 1.0f, 1.0f, 0.5f,
                1.0f, 1.0f, 1.0f, 0.5f,
                1.0f, 1.0f, 1.0f, 0.5f,
                1.0f, 1.0f, 1.0f, 0.5f,
        };

        // テキスチャマッピング
        float [] coords = new float[]{
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
        };

        // 左上
//        float [] coords = new float[]{
//                0.0f, 0.5f,
//                0.5f, 0.5f,
//                0.0f, 0.0f,
//                0.5f, 0.0f,
//        };

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

        // The coords buffer.
        ByteBuffer coordsbb = ByteBuffer.allocateDirect(coords.length * 4);
        coordsbb.order(ByteOrder.nativeOrder());
        FloatBuffer coordsBuffer = coordsbb.asFloatBuffer();
        coordsBuffer.put(coords);
        coordsBuffer.position(0);

        // テキスチャ機能を有効化
        gl10.glEnable(GL10.GL_TEXTURE_2D);
        // テキストオブジェクトの指定
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, mTexture);

        gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, coordsBuffer);
        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // テキスチャ機能を無効化
        gl10.glDisable(GL10.GL_TEXTURE_2D);

    }
}
