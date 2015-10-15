package com.example.irun;

public class ChatMsgEntity {

	private String name;//��Ϣ����  
    private String date;//��Ϣ����  
    private String message;//��Ϣ����  
    private boolean isSend = true;//�Ƿ�Ϊ���͵���Ϣ  
    
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getDate() {  
        return date;  
    }  
  
    public void setDate(String date) {  
        this.date = date;  
    }  
  
    public String getMessage() {  
        return message;  
    }  
  
    public void setMessage(String message) {  
        this.message = message;  
    }  
 
    public boolean getMsgType() {  
        return isSend;  
    }  
  
    public void setMsgType(boolean isSend) {  
        this.isSend = isSend;  
    } 
    
    public ChatMsgEntity() {
    	
    }
    
    public ChatMsgEntity(String name, String date, String message, boolean isSend) {  
        this.name = name;  
        this.date = date;  
        this.message = message;
        this.isSend = isSend;
    }
}
