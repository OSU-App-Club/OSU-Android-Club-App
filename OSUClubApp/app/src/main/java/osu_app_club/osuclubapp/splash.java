package osu_app_club.osuclubapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Chris on 2/16/2015.
 */
public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView iv = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        animation.setDuration(6000);
        iv.startAnimation(animation);
    }
}
