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

//ѡ������ģʽ�Ľ��棬��ΪȺ�ĺ�һ��һ����
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
		
		textTitle.setText(UserInfo.getID() + "��ͨѶ¼");
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
				adapter.notifyDataSetChanged();//֪ͨListView�������ѷ����ı�
				listView.setSelection(listView.getCount() - 1);//����һ����Ϣʱ��ListView��ʾѡ�����һ��
				
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
		//��ֻ֤��ʼ��һ��
		//����ChatFragment��Ϊһ��Fragment����һ��tag������Fragment֮����л�
		//ͬʱChatFragment��Ϊһ�����촰�ڣ�Ҳ��һ��tag����ʾ��˭�����죬��tagΪ11����ʾ��11������
		//����ChatFragment�ڽ�����Ϣʱֻ���ܸ��Լ�tag��ȵģ��Ϳ��Խ���Ӧ����Ϣ��ʾ�ڶ�Ӧ�Ĵ���
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

