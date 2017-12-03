package tw.org.iii.androidlittlehappy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class JsonFactoryForChat {

	public JsonFactoryForChat() {
	}

	public CMessage parse(String myJarray) {
		//org.json 解析JSON字串，把JSON內容丟給CMessage物件
		JSONArray jarray;
		CMessage msgObj = null;
		try {
			jarray = new JSONArray(myJarray);
			for(int i=0; i<jarray.length(); i++){
				JSONObject obj = jarray.getJSONObject(i);
				msgObj = new CMessage();
				msgObj.setfaID(obj.getString("faID"));
				msgObj.setfChatFrom(obj.getString("fChatFrom"));
				msgObj.setfChatTo(obj.getString("fChatTo"));
				msgObj.setfMessage(obj.getString("fMessage"));
				msgObj.setfRead(obj.getString("fRead"));
				msgObj.setfUpdateTime(obj.getString("fUpdateTime"));
				return msgObj;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgObj;
	}

	public Hashtable<String, List<CMessage>> parseToHashtable(String myJarray) {
		//org.json 解析JSON字串，把JSON內容丟給CMessage物件
		JSONArray jarray;
		Hashtable<String, List<CMessage>> Hashtable_UserNameToCMessage = new Hashtable<String, List<CMessage>>();
		List<CMessage> msgObjList = new ArrayList<>();
		CMessage msgObj = null;
		try {
			jarray = new JSONArray(myJarray);
			for(int i=0; i<jarray.length(); i++){
				JSONObject obj = jarray.getJSONObject(i);
				msgObjList.clear();
				msgObj = new CMessage();
				//取得JSON格式中hashmap的CMessage JSON
				msgObj = parse(obj.getString("fMessageObjecJSON"));
				//如果Hashtable裡面已經有KEY，代表已經有其他的List<CMessage> 在裡面，因此要先讀出來後再加入新的CMessage
				if (Hashtable_UserNameToCMessage.containsKey(obj.getString("faID")+"withUser"+obj.getString("f2ndUser")))
				{
					msgObjList = Hashtable_UserNameToCMessage.get(obj.getString("faID")+"withUser"+obj.getString("f2ndUser"));
				}
				msgObjList.add(msgObj);
				Hashtable_UserNameToCMessage.put(obj.getString("faID")+"withUser"+obj.getString("f2ndUser"), msgObjList);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Hashtable_UserNameToCMessage;
	}

	public List<CMessage> parseToList(String myJarray) {
		//org.json 解析JSON字串，把hashmap格式的JSON內容丟給List<CMessage>物件
		JSONArray jarray;
		List<CMessage> msgObjList = new ArrayList<>();
		CMessage msgObj = null;
		try {
			jarray = new JSONArray(myJarray);
			msgObjList.clear();
			//Log.i("Async", String.valueOf(jarray.length()));
			for(int i=0; i<jarray.length(); i++){
				JSONObject obj = jarray.getJSONObject(i);
				msgObj = new CMessage();
				//取得JSON格式中hashmap的CMessage JSON
				msgObj = parse(obj.getString("fMessageObjecJSON"));
				msgObjList.add(msgObj);
			//	Log.i("Async", msgObj.getfMessage());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("Async", String.valueOf(msgObjList.size()));
		return msgObjList;
	}




	public String stringify(CMessage myMessage) {
		//使用org.json API 製作 JSON字串
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("faID", myMessage.getfaID());
			obj.put("fChatFrom", myMessage.getfChatFrom());
			obj.put("fChatTo", myMessage.getfChatTo());
			obj.put("fMessage", myMessage.getfMessage());
			obj.put("fRead", myMessage.getfRead());
			obj.put("fUpdateTime", myMessage.getfUpdateTime());
			ary.put(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ary.toString();
	}

	//把List<CMessage>分類後轉為JSON
	//格式為{faID(活動ID): string_myfaID, f2ndUser(對話第二使用者): string_myf2ndUser, fMessageObjecJSON: string in JSON CMessage }
	public String stringifyFromList(List<CMessage> myMessageList, String mainUser) {
		//使用org.json API 製作 JSON字串
		JSONArray ary = new JSONArray();
		String f2ndUser = "";



		for (int i=0;i<myMessageList.size();i++)
		{
			JSONObject obj = new JSONObject();
			if (myMessageList.get(i).getfChatFrom().equals(mainUser))
			{
				f2ndUser = myMessageList.get(i).getfChatTo();
			}
			else
			{
				f2ndUser = myMessageList.get(i).getfChatFrom();
			}
			try {
				obj.put("faID", myMessageList.get(i).getfaID());
				obj.put("f2ndUser", f2ndUser);
				obj.put("fMessageObjecJSON", stringify(myMessageList.get(i)));
				ary.put(obj);
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ary.toString();
	}
}