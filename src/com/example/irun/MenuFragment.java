package com.example.irun;

import java.util.ArrayList;

import com.unity3d.player.UnityPlayer;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//侧滑菜单
public class MenuFragment extends Fragment implements OnClickListener,OnItemClickListener {

    private ListView drawerList;  
    private ArrayList<String> list;  
    private ArrayAdapter<String> adapter; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		
		drawerList = (ListView) view.findViewById(R.id.drawerList);
		list = new ArrayList<String>();
		list.add("天气");
		list.add("签到");
		list.add("成就");
		list.add("背包");
		list.add("商店");
		list.add("排行");
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_string, list);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(this);
		
		Button loginButton = (Button) view.findViewById(R.id.drawerLogin);
		loginButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.drawerLogin)
		{
			MainActivity.bottomBar.setVisibility(View.GONE);
			MainFragment.drawerLayout.closeDrawers();
			
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.hide(fm.findFragmentByTag("MainFragment"));
			
			if(fm.findFragmentByTag("LoginFragment") == null)
			{
				ft.add(R.id.content,new LoginFragment(), "LoginFragment");
			}
			else 
			{
				ft.show(fm.findFragmentByTag("LoginFragment"));				
			}		
			ft.commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position == 1) {
			MainFragment.canRandom = false;
			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE01");
		}
		else if(position == 2) {
			MainFragment.canRandom = false;
			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE02");
		}
		else if(position == 3) {
			MainFragment.canRandom = false;
			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE03");
		}
		else if(position == 4) {
			MainFragment.canRandom = false;
			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE04");
		}
		else if(position == 5) {
			MainFragment.canRandom = false;
			UnityPlayer.UnitySendMessage("unitychan","PlayAnim","1_POSE05");
		}
	}
}
