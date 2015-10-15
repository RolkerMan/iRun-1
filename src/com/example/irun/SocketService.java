package com.example.irun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SocketService extends Service {
	
	private static Socket socket;
	private static BufferedWriter bWriter;//����������͡�д����Ϣ
	private static BufferedReader bReader;//�����������ܡ���ȡ��Ϣ
	
	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(new Runnable() {
			public void run() {
				try {
					socket = new Socket("172.25.225.24",1989);
					bWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
					bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
					
					//readLine()��һ��������������û�����ݶ�ȡʱ����һֱ���������ǣ������Ƿ���null
					String s;
					while ((s = bReader.readLine()) != null) {
						Intent intent = new Intent();
						intent.putExtra("jsonString", s);
						intent.setAction("SocketService");
						sendBroadcast(intent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		}).start();
	}
	
	public static void send(String jsonString)
	{
		jsonString += "\n";
		try {
			bWriter.write(jsonString);
			bWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}

