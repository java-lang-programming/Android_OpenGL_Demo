package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CircleActivity extends Activity {

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
        CircleRenderer renderer = new CircleRenderer();

        // set Renderer to GLSurfaceView
        gLSurfaceView.setRenderer(renderer);

        setContentView(gLSurfaceView);
    }
}
