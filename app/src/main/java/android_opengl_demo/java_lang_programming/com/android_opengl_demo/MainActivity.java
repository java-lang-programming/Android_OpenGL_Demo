package android_opengl_demo.java_lang_programming.com.android_opengl_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mSquareBtn;
    private Button mCircleBtn;
    private Button mTextureBtn;
    private Button mMatrixBtn;
    private Button mGl2SquareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSquareBtn = (Button) findViewById(R.id.square_btn);
        mSquareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveSquareActivity();
            }
        });

        mCircleBtn = (Button) findViewById(R.id.circle_btn);
        mCircleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveCircleActivity();
            }
        });

        mTextureBtn = (Button) findViewById(R.id.texture_btn);
        mTextureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTextureActivity();
            }
        });

        mMatrixBtn = (Button) findViewById(R.id.matrix_btn);
        mMatrixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMatrixActivity();
            }
        });

        mGl2SquareBtn = (Button) findViewById(R.id.gl2_square_btn);
        mGl2SquareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveSquareGl2Activity();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * screen transition to CircleActivity
     */
    private void moveCircleActivity() {
        Intent intent = new Intent(this, CircleActivity.class);
        startActivity(intent);
    }

    /**
     * screen transition to SquareActivity
     */
    private void moveSquareActivity() {
        Intent intent = new Intent(this, SquareActivity.class);
        startActivity(intent);
    }

    /**
     * screen transition to TextureActivity
     */
    private void moveTextureActivity() {
        Intent intent = new Intent(this, TextureActivity.class);
        startActivity(intent);
    }

    /**
     * screen transition to MatrixActivity
     */
    private void moveMatrixActivity() {
        Intent intent = new Intent(this, MatrixActivity.class);
        startActivity(intent);
    }

    /**
     * screen transition to SquareGl2Activity
     */
    private void moveSquareGl2Activity() {
        Intent intent = new Intent(this, SquareGl2Activity.class);
        startActivity(intent);
    }
}
