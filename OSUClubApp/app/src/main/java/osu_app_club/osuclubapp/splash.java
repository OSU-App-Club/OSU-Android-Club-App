package osu_app_club.osuclubapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Intent;

public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView iv = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        animation.setDuration(3000);
        iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener(){
            public void onAnimationStart(Animation a){
            }
            public void onAnimationRepeat(Animation a){
            }
            public void onAnimationEnd(Animation a){
                Intent intent = new Intent(Splash.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
