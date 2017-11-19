package tesis.com.py.sisgourmetmobile.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import tesis.com.py.sisgourmetmobile.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*TextView textView = (TextView) findViewById(R.id.splash_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/splash_font2.otf");
        textView.setTypeface(typeface);*/
        new SplashTask().execute();
    }

    private class SplashTask extends AsyncTask<Void, Void, Void> {


        public SplashTask() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            } //3 SECONDS
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            super.onPostExecute(aVoid);
        }
    }

}
