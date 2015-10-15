package com.example.irun;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

//选择聊天模式的界面，分为群聊和一对一聊天
public class ChooseChatFragment extends Fragment implements OnClickListener,OnItemClickListener{
	
	private TextView textTitle;
	private EditText editText;
	private Button buttonBack;
	private Button buttonAdd;
	private Button buttonGroup;
	
	private ListView listView;
	private FriendEntityViewAdapter adapter;
	private List<FriendEntity> list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_choose_chat, container, false);
		textTitle = (TextView)view.findViewById(R.id.titleText);
		editText = (EditText)view.findViewById(R.id.editText);
		buttonBack = (Button)view.findViewById(R.id.buttonBack);
		buttonAdd = (Button)view.findViewById(R.id.buttonAdd);
		buttonGroup = (Button)view.findViewById(R.id.buttonGroup);
		listView = (ListView)view.findViewById(R.id.listview);
		
		textTitle.setText(UserInfo.getID() + "的通讯录");
		buttonBack.setOnClickListener(this);
		buttonAdd.setOnClickListener(this);
		buttonGroup.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		
		list = new ArrayList<FriendEntity>();
		adapter = new FriendEntityViewAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonAdd)
		{
			if(editText.getText().toString().length() > 0)
			{
				FriendEntity fe = new FriendEntity();
				fe.setID(editText.getText().toString());
				
				list.add(fe);
				adapter.notifyDataSetChanged();//通知ListView，数据已发生改变
				listView.setSelection(listView.getCount() - 1);//发送一条消息时，ListView显示选择最后一项
				
				editText.setText("");
			}		
		}
		else if(v.getId() == R.id.buttonGroup)
		{		
			toChatFragment("Group");
		}
		else if(v.getId() == R.id.buttonBack)
		{
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.hide(this);
			ft.show(fm.findFragmentByTag("MainFragment"));
			ft.commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) 
	{
		toChatFragment(list.get(position).getID());
	}
	
	private void toChatFragment(String tag)
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction(); 
		ft.hide(this);
		//保证只初始化一次
		//这里ChatFragment作为一个Fragment，有一个tag，方便Fragment之间的切换
		//同时ChatFragment作为一个聊天窗口，也有一个tag，表示跟谁的聊天，如tag为11，表示跟11的聊天
		//这样ChatFragment在接受信息时只接受跟自己tag相等的，就可以将对应的信息显示在对应的窗口
		if(fm.findFragmentByTag(tag) == null)
		{
			ft.add(R.id.content, new ChatFragment(tag), tag);
		}
		else 
		{  
			ft.show(fm.findFragmentByTag(tag));
		}        
		ft.addToBackStack(null);
		ft.commit();
	}
}

