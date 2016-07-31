/**
 * Copyright (C) 2016 Programming Java Android Development Project
 * Programming Java is
 *
 *      http://java-lang-programming.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by java-lang-programming.com on 2016/07/28.
 */
public class GraphicUtil {

    public static final int loadTexture(GL10 gl10, Resources resources, int resId) {
        int [] textures = new int[1];

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 自動リサイズをしない
        options.inScaled = false;
        // 32bit(0〜127)
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        Bitmap bmp = BitmapFactory.decodeResource(resources, resId, options);
        if (bmp == null) {
            return 0;
        }

        // OpenGL用のテキスチャを生成する
        gl10.glGenTextures(1, textures, 0);
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, 0);

        // OpenGLへの転送が終了したので、VMメモリ上に作成したBitmapを破棄する
        bmp.recycle();

        // グローバルに登録
        Global.textures = textures;

        return textures[0];
    }

    public static FloatBuffer getFloatBuffer(float [] floats) {
        ByteBuffer dlb = ByteBuffer.allocateDirect(floats.length * 4);
        dlb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = dlb.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);
        return floatBuffer;
    }

}
