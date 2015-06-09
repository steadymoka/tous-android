package com.tous.application.mvc.controller.activity.account;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainActivity;


public class SignInActivity extends BaseActivity implements View.OnClickListener {

	private ImageView imageView_sample;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_sign_in );

		imageView_sample = (ImageView) findViewById( R.id.imageView_sample );
		imageView_sample.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {

		startActivity( new Intent( this, MainActivity.class ) );
		finish();
	}

}
