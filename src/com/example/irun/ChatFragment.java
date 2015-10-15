package com.example.irun;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ListView;
import android.widget.TextView;

//�������
public class ChatFragment extends Fragment implements OnClickListener {

	private EditText editText;//����Ҫ���͵���Ϣ
	private Button sendButton;//���Ͱ�ť
	private Button backButton;//���ذ�ť
	private TextView titleText;//����
	
	private ListView listView;//������Ϣ�Ļ����б�
	private ChatMsgListViewAdapter adapter;
	private List<ChatMsgEntity> list;

	private String toID;//��ʾ��˭����Ĵ���
	private MessageReceiver messageReceiver;//���ܼ����Ķ���
	
	public ChatFragment(String toID)
	{
		this.toID = toID;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		
		editText = (EditText)view.findViewById(R.id.editText);
		sendButton = (Button)view.findViewById(R.id.btn_send);
		backButton = (Button)view.findViewById(R.id.btn_back);
		titleText = (TextView)view.findViewById(R.id.title);
		listView = (ListView)view.findViewById(R.id.listview);
		
		sendButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		titleText.setText("��" + toID + "������");
		
		list = new ArrayList<ChatMsgEntity>();
		adapter = new ChatMsgListViewAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		
		initMessageReceiver();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_send) {
			send();
		}
		else if(v.getId() == R.id.btn_back) {
			FragmentManager fm = getFragmentManager();  
	        FragmentTransaction tx = fm.beginTransaction();  
	        tx.hide(this);
	        tx.show(fm.findFragmentByTag("ChooseChatFragment"));
	        tx.commit();
		}
	}
	
	private void send()
	{
		String content = editText.getText().toString();
		if(content.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName(UserInfo.getID());
			entity.setDate(getDate());
			entity.setMessage(content);
			entity.setMsgType(true);
			
			list.add(entity);
			adapter.notifyDataSetChanged();//֪ͨListView�������ѷ����ı�
			listView.setSelection(listView.getCount() - 1);//����һ����Ϣʱ��ListView��ʾѡ�����һ��
			
			editText.setText("");
			
			try {
				JSONObject root = new JSONObject();
				root.put("content", content);
				root.put("fromID", UserInfo.getID());
				root.put("toID", toID);
				SocketService.send(root.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}	
	}
	
	private String getDate() 
	{
		long time = System.currentTimeMillis();;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date(time);  
        return format.format(d);  
    } 
	
	private void initMessageReceiver()
	{
		messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SocketService");
        getActivity().registerReceiver(messageReceiver,filter);
	}
		
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
						
			String content = intent.getStringExtra("jsonString");
			try 
			{
				JSONObject root = new JSONObject(content);
				
				//����root.getString("toID").equalsIgnoreCase(UserInfo.getID())
				//��Ϊ����ÿһ��������˵��UserInfo.getID()��һ��
				//toID��ʾ����Ϣ��˭
				boolean a = (root.getString("toID").equalsIgnoreCase("Group")) && (toID.equalsIgnoreCase("Group"));
				boolean b = (!root.getString("toID").equalsIgnoreCase("Group")) && (toID.equalsIgnoreCase(root.getString("fromID")));
				if(a || b)
				{
					if(content != null) 
					{
						ChatMsgEntity entity = new ChatMsgEntity();
						entity.setName(root.getString("fromID"));
						entity.setDate(getDate());
						entity.setMessage(root.getString("content"));
						entity.setMsgType(false);
						
						list.add(entity);
						adapter.notifyDataSetChanged();//֪ͨListView�������ѷ����ı�
						listView.setSelection(listView.getCount() - 1);//����һ����Ϣʱ��ListView��ʾѡ�����һ��
					}
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}		
    }
}
