package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ektoplasma on 25/08/16.
 */
public class SignActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Caller.setCtx(this.getApplicationContext());
        Caller.storePersistantCookieString();
        if(Caller.getCookieInstance() != null)
        {
            Intent mainIntent = new Intent(this, MainActivity.class);
            this.startActivity(mainIntent);
        }

        setContentView(R.layout.activity_sign);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Button ButtonLogin = (Button)findViewById(R.id.ButtonLogin);
        Button ButtonRegister = (Button)findViewById(R.id.ButtonRegister);
        Button ButtonSettings = (Button)findViewById(R.id.ButtonSettings);

        ButtonLogin.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(SignActivity.this, LoginActivity.class));
                    }
                }
        );

        ButtonRegister.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(SignActivity.this, RegisterActivity.class));
                    }
                }
        );

        ButtonSettings.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(SignActivity.this, SettingsActivity.class));
                    }
                }
        );
    }
}
