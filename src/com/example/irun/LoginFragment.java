package com.example.irun;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//µÇÂ¼½çÃæ
public class LoginFragment extends Fragment implements OnClickListener {

	private EditText editUser;
	private EditText editPassword;
	private Button buttonLogin;
	private Button buttonCancel;
	
	private MessageReceiver messageReceiver;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		editUser = (EditText) view.findViewById(R.id.user);
		editPassword = (EditText) view.findViewById(R.id.password);
		buttonLogin = (Button) view.findViewById(R.id.btnLogin);
		buttonCancel = (Button) view.findViewById(R.id.btnCancel);
		
		buttonLogin.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		
        initMessageReceiver();
        
		return view;
	}
	
	private void initMessageReceiver()
	{
		messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SocketService");
        getActivity().registerReceiver(messageReceiver,filter);
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(messageReceiver);
		super.onDestroy();
	}
	
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String content = intent.getStringExtra("jsonString");
			try {
				JSONObject root = new JSONObject(content);
				boolean isLoginSuccess = root.getBoolean("result");
				if(isLoginSuccess)
				{
					Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
					UserInfo.setID(editUser.getText().toString());
					toMainFragment();
				}
				else 
				{
					Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}		
    }
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnLogin)
		{
			if((editUser.getText().toString().length() > 0) && (editPassword.getText().toString().length() > 0))
			{	
				try {
					JSONObject root = new JSONObject();
					root.put("user", editUser.getText());
					root.put("password", editPassword.getText());
					SocketService.send(root.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}			
			}
		}
		else if(v.getId() == R.id.btnCancel)
		{
			Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
			toMainFragment();
		}	
	}
	
	private void toMainFragment()
	{		
		MainActivity.bottomBar.setVisibility(View.VISIBLE);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fm.findFragmentByTag("MainFragment"));
		ft.hide(this);
		ft.commit();
	}
}
