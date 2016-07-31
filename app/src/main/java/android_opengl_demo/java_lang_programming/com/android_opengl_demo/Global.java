package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by msuzuki on 2016/07/28.
 */
public class Global {

    // GLコンテキストを保持する
    public static GL10 gl;

    public static int [] textures;

    public static void removeTexture() {
        gl.glDeleteTextures(1, textures, 0);
    }

}
