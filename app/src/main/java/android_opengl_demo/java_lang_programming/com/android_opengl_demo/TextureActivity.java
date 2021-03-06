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

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class TextureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Fullscreen mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create GLSurfaceView
        GLSurfaceView gLSurfaceView = new GLSurfaceView(this);

        // Create GLSurfaceView.Renderer
        TextureRenderer renderer = new TextureRenderer(this);

        // set Renderer to GLSurfaceView
        gLSurfaceView.setRenderer(renderer);

        setContentView(gLSurfaceView);
    }

    @Override
    public void onPause() {
        super.onPause();

        // テキスチャーを削除する
        //Global.removeTexture();
    }
}
