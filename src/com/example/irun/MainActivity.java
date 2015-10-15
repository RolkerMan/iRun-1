package com.example.irun;

import com.unity3d.player.UnityPlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;  
import android.app.FragmentManager;  
import android.app.FragmentTransaction; 
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

//主Activity，管理界面的切换
public class MainActivity extends Activity {

	private FragmentManager fm;
	public static View bottomBar;
	private RadioGroup radioGroup;
	
	private MainFragment fragment1;
	private ChooseChatFragment fragment2;
	private MyMapFragment fragment3;
	private StepSettingFragment fragment4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFormat(PixelFormat.TRANSLUCENT);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fm = getFragmentManager();
		bottomBar = findViewById(R.id.bottomBar);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb1){
					setFragmentShow(0);
				}
				else if(checkedId == R.id.rb2){
					setFragmentShow(1);
				}
				else if(checkedId == R.id.rb3){
					setFragmentShow(2);
				}
				else if(checkedId == R.id.rb4){
					setFragmentShow(3);
				}
			}
		});	

		setFragmentShow(0);

//		new Handler().postDelayed(new Runnable() {
//		@Override
//		public void run() {
//			FragmentManager fm = getFragmentManager();
//			FragmentTransaction ft = fm.beginTransaction();
//			ft.hide(fm.findFragmentByTag("MainFragment"));
//			ft.add(R.id.content, new LoginFragment(),"LoginFragment");
//			ft.commit();		
//		}
//	}, 3000);
		
		startService(new Intent(MainActivity.this, SocketService.class));
	}
	
	@Override
	protected void onDestroy() {
		stopService(new Intent(MainActivity.this, SocketService.class));
		stopService(new Intent(MainActivity.this, MusicService.class));
		stopService(new Intent(MainActivity.this, AccelerometerSensorService.class));
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
	}
	
	private void setFragmentShow(int index)
	{
		FragmentTransaction ft = fm.beginTransaction();
		if(fragment1 != null)
		{
			ft.hide(fragment1);
		}
		if(fragment2 != null)
		{
			ft.hide(fragment2);
		}
		if(fragment3 != null)
		{
			ft.hide(fragment3);
		}
		if(fragment4 != null)
		{
			ft.hide(fragment4);
		}
		
		switch (index) 
		{
		case 0:
			radioGroup.getChildAt(0).setSelected(true);
			if(fragment1 == null)
			{
				fragment1 = new MainFragment();
				ft.add(R.id.content, fragment1,"MainFragment");
			}
			else 
			{
				ft.show(fragment1);
			}
			break;

		case 1:
			radioGroup.getChildAt(1).setSelected(true);
			if(fragment2 == null)
			{
				fragment2 = new ChooseChatFragment();
				ft.add(R.id.content, fragment2,"ChooseChatFragment");
			}
			else 
			{
				ft.show(fragment2);
			}
			break;
			
		case 2:
			radioGroup.getChildAt(2).setSelected(true);
			if(fragment3 == null)
			{
				fragment3 = new MyMapFragment();
				ft.add(R.id.content, fragment3,"MyMapFragment");
			}
			else 
			{
				ft.show(fragment3);
			}
			break;
	
		case 3:
			radioGroup.getChildAt(3).setSelected(true);
			if(fragment4 == null)
			{
				fragment4 = new StepSettingFragment();
				ft.add(R.id.content, fragment4,"StepSettingFragment");
			}
			else 
			{
				ft.show(fragment4);
			}
			break;
			
		default:
			break;
		}
		
		ft.commit();
	}
}
