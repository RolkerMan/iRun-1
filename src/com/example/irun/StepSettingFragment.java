package com.example.irun;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

//计步器功能的设置界面
public class StepSettingFragment extends Fragment implements OnClickListener {
	
	public static SharedPreferences sharedPreferences;
	private Editor editor;
	
	//key
	public static final String WEIGHT_VALUE = "weight_value";//体重
	public static final String STEP_LENGTH_VALUE = "step_length_value";// 步长
	public static final String SENSITIVITY_VALUE = "sensitivity_value";// 灵敏值
	public static final String SETP_SHARED_PREFERENCES = "setp_shared_preferences";// 设置

	//value
	private int sensitivity = 0;
	private int step_length = 0;
	private int weight = 0;
	
	private TextView tv_sensitivity_vlaue;
	private TextView tv_step_length_vlaue;
	private TextView tv_weight_value;

	//在settings.xml已设置好android:max
	private SeekBar sb_sensitivity;
	private SeekBar sb_step_length;
	private SeekBar sb_weight;
	
	private Button buttonSave;
	private Button buttonCancel;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_step_setting, container, false);
		
		tv_sensitivity_vlaue = (TextView) view.findViewById(R.id.sensitivity_value);
		tv_step_length_vlaue = (TextView) view.findViewById(R.id.step_lenth_value);
		tv_weight_value = (TextView) view.findViewById(R.id.weight_value);

		sb_sensitivity = (SeekBar) view.findViewById(R.id.sensitivity);
		sb_step_length = (SeekBar) view.findViewById(R.id.step_lenth);
		sb_weight = (SeekBar) view.findViewById(R.id.weight);
		
		buttonSave = (Button) view.findViewById(R.id.save);
		buttonCancel = (Button) view.findViewById(R.id.cancle);
		
		init();
		listener();
		
		return view;
	}
	
	/**
	 * SeekBar的拖动监听
	 */
	private void listener() {
		sb_sensitivity
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {    // 灵敏值动作的监听

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						sensitivity = progress;
						tv_sensitivity_vlaue.setText(sensitivity + "");
					}
				});

		sb_step_length
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						step_length = progress * 5 + 40;
						tv_step_length_vlaue.setText(step_length
								+ getString(R.string.cm));
					}
				});

		sb_weight.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				weight = progress * 2 + 30;
				tv_weight_value.setText(weight + getString(R.string.kg));
			}
		});
		
		buttonSave.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		if (sharedPreferences == null) {    //SharedPreferences是Android平台上一个轻量级的存储类，
											//主要是保存一些常用的配置比如窗口状态
			sharedPreferences = getActivity().getSharedPreferences(SETP_SHARED_PREFERENCES,
					getActivity().MODE_PRIVATE);
		}

		editor = sharedPreferences.edit();

		sensitivity = 10 - sharedPreferences.getInt(SENSITIVITY_VALUE, 7);
		step_length = sharedPreferences.getInt(STEP_LENGTH_VALUE, 70);
		weight = sharedPreferences.getInt(WEIGHT_VALUE, 50);

		sb_sensitivity.setProgress(sensitivity);
		sb_step_length.setProgress((step_length - 40) / 5);//40是起始值，并且间隔为5
		sb_weight.setProgress((weight - 30) / 2);

		tv_sensitivity_vlaue.setText(sensitivity + "");
		tv_step_length_vlaue.setText(step_length + getString(R.string.cm));
		tv_weight_value.setText(weight + getString(R.string.kg));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			editor.putInt(SENSITIVITY_VALUE, 10 - sensitivity);
			editor.putInt(STEP_LENGTH_VALUE, step_length);
			editor.putInt(WEIGHT_VALUE, weight);
			editor.commit();
			
			Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
			
			StepDetector.SENSITIVITY = 10 - sensitivity;
			toMainFragment();
			break;
			
		case R.id.cancle:
			toMainFragment();
			break;

		default:
			toMainFragment();
			break;
		}	
	}
	
	private void toMainFragment()
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.remove(this);
		ft.show(fm.findFragmentByTag("MainFragment"));
		ft.commit();
	}
}
