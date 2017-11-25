package tw.org.iii.androidlittlehappy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class JsonFactory {
	
	public JsonFactory() {
	}
	//一個物件
	public CActivitys parse(String myJarray) {
		JSONArray ary2;
		CActivitys activity = null;
		try {
			ary2 = new JSONArray(myJarray);
	    	  for(int i=0; i<ary2.length(); i++){
	      		JSONObject obj = ary2.getJSONObject(i);
	      		//System.out.printf("id=%d \n type=%s \n title=%s \n content=%s \n createTime=%s \n limitTime=%s \n limitStar=%d \n gpsX=%.9f \n gpsY=%.9f \n " + "state=%s \n createor=%s \n",
	      		//obj.getInt("id"), obj.getInt("type"), obj.getString("title"), obj.getString("content"), obj.getString("creatTime"),  obj.getString("limitTime"), obj.getInt("limitStar"), obj.getDouble("gpsX"), obj.getDouble("gpsY"), obj.getString("state"), obj.getString("createor"));
	      		activity = new CActivitys();
	      		activity.setId(obj.getInt("id"));
	      		activity.setType(obj.getString("type"));
	            activity.setTitle(obj.getString("title"));
	            activity.setContent(obj.getString("content"));
	            activity.setCreateTime(obj.getString("createTime"));
	            activity.setLimitTime(obj.getString("limitTime"));
	            activity.setLimitStar(obj.getString("limitStar"));
	            activity.setGpsX(obj.getString("gpsX"));
	            activity.setGpsY(obj.getString("gpsY"));
	            activity.setState(obj.getString("state"));
	            activity.setCreator(obj.getString("creator"));
	      	  }	 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return activity;
	}
	
	//一個物件
	public String stringify(CActivitys myAct) {
        //使用org.json API 製作 JSON字串
        JSONArray ary = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
			obj.put("id", myAct.getId());
	        obj.put("type", myAct.getType());
	        obj.put("title", myAct.getTitle());
	        obj.put("content", myAct.getContent());
	        obj.put("createTime", myAct.getCreateTime());
	        obj.put("limitTime", myAct.getLimitTime());
	        obj.put("limitStar", myAct.getLimitStar());
	        obj.put("gpsX", myAct.getGpsX());
	        obj.put("gpsY", myAct.getGpsY());
	        obj.put("state", myAct.getState());
	        obj.put("creator", myAct.getCreator());
	        ary.put(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ary.toString();
	}

	/*
	//多個物件
	public String stringifyList(List<CActivitys> myList) {
        //使用org.json API 製作 JSON字串
        JSONArray ary = new JSONArray();
        try {
        	for (int i = 0; i < myList.size(); i++) {
        		JSONObject obj = new JSONObject();
    			obj.put("id", myList.get(i).getId());
    	        obj.put("type", myList.get(i).getType());
    	        obj.put("title", myList.get(i).getTitle());
    	        obj.put("content", myList.get(i).getContent());
    	        obj.put("createTime", myList.get(i).getCreateTime());
    	        obj.put("limitTime", myList.get(i).getLimitTime());
    	        obj.put("limitStar", myList.get(i).getLimitStar());
    	        obj.put("gpsX", myList.get(i).getGpsX());
    	        obj.put("gpsY", myList.get(i).getGpsY());
    	        obj.put("state", myList.get(i).getState());
    	        obj.put("creator", myList.get(i).getCreator());
    	        ary.put(obj);	
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ary.toString();
	}
	
	//多個物件
	public List<CActivitys> parseList(String myJarray) {
		JSONArray ary2;
		List<CActivitys> list = new ArrayList<CActivitys>();
		try {
			ary2 = new JSONArray(myJarray);
	    	  for(int i=0; i<ary2.length(); i++){
	      		JSONObject obj = ary2.getJSONObject(i);
	      		CActivitys act1 = new CActivitys();
	      		act1.setId(obj.getInt("id"));
	      		act1.setType(obj.getString("type"));
	            act1.setTitle(obj.getString("title"));
	            act1.setContent(obj.getString("content"));
	            act1.setCreateTime(obj.getString("createTime"));
	            act1.setLimitTime(obj.getString("limitTime"));
	            act1.setLimitStar(obj.getString("limitStar"));
	            act1.setGpsX(obj.getString("gpsX"));
	            act1.setGpsY(obj.getString("gpsY"));
	            act1.setState(obj.getString("state"));
	            act1.setCreator(obj.getString("creator"));
	            list.add(act1);
	      	  }	 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list;
	}*/

	//搜尋活動Stringfy
	public String searchSringfy(String myUserName, String gpsX, String gpsY, String selfStar, String limitDis){
		JSONObject objSearch = new JSONObject();
		JSONObject chkKey = new JSONObject();
		try {
			//
			objSearch.put("userName", myUserName);
			objSearch.put("gpsX", gpsX);
			objSearch.put("gpsY", gpsY);
			objSearch.put("selfStar", selfStar);
			objSearch.put("limitDis", limitDis);

			//Key
			chkKey.put(Dictionary.key, objSearch);
		}
		catch (JSONException e){
			System.out.println("錯誤" +  e.toString());
		}
		return chkKey.toString();
	}

	//搜尋活動parse
	public void searchParse(String myJosn, List<CActivitys> myInitiate, List<CActivitys> myJoin, List<CActivitys> myCan_see){
		try {

			JSONObject chkKey = new JSONObject(myJosn);
			JSONObject clientStart = chkKey.getJSONObject(Dictionary.key);

			JSONArray objInitiate = clientStart.getJSONArray("Initiate");
			JSONArray objJoin = clientStart.getJSONArray("Join");
			JSONArray objCan_see = clientStart.getJSONArray("Can_see");
			//發起
			for (int i = 0; i < objInitiate.length(); i++) {
				JSONObject obj = objInitiate.getJSONObject(i);
				CActivitys act = new CActivitys();
				act.setId(obj.getInt("id"));
				act.setType(obj.getString("type"));
				act.setTitle(obj.getString("title"));
				act.setContent(obj.getString("content"));
				act.setCreateTime(obj.getString("createTime"));
				act.setLimitTime(obj.getString("limitTime"));
				act.setLimitStar(obj.getString("limitStar"));
				act.setGpsX(obj.getString("gpsX"));
				act.setGpsY(obj.getString("gpsY"));
				act.setState(obj.getString("state"));
				act.setCreator(obj.getString("creator"));
				act.setLastTime(obj.getString("lastTime"));
				myInitiate.add(act);
			}
			//參加
			for (int i = 0; i < objJoin.length(); i++) {
				JSONObject obj = objJoin.getJSONObject(i);
				CActivitys act = new CActivitys();
				act.setId(obj.getInt("id"));
				act.setType(obj.getString("type"));
				act.setTitle(obj.getString("title"));
				act.setContent(obj.getString("content"));
				act.setCreateTime(obj.getString("createTime"));
				act.setLimitTime(obj.getString("limitTime"));
				act.setLimitStar(obj.getString("limitStar"));
				act.setGpsX(obj.getString("gpsX"));
				act.setGpsY(obj.getString("gpsY"));
				act.setState(obj.getString("state"));
				act.setCreator(obj.getString("creator"));
				act.setLastTime(obj.getString("lastTime"));
				myJoin.add(act);
			}

			//看的到+星等
			for (int i = 0; i < objCan_see.length(); i++) {
				JSONObject obj = objCan_see.getJSONObject(i);
				CActivitys act = new CActivitys();
				act.setId(obj.getInt("id"));
				act.setType(obj.getString("type"));
				act.setTitle(obj.getString("title"));
				act.setContent(obj.getString("content"));
				act.setCreateTime(obj.getString("createTime"));
				act.setLimitTime(obj.getString("limitTime"));
				act.setLimitStar(obj.getString("limitStar"));
				act.setGpsX(obj.getString("gpsX"));
				act.setGpsY(obj.getString("gpsY"));
				act.setState(obj.getString("state"));
				act.setCreator(obj.getString("creator"));
				act.setLastTime(obj.getString("lastTime"));
				myCan_see.add(act);
			}
		}
		catch (JSONException e){
			System.out.println("錯誤" +  e.toString());
		}
	}

	//參加活動stringfy
	public String joinStringfy(int myAid, String userName){
		JSONObject objJoin = new JSONObject();
		JSONObject chkKey = new JSONObject();
		try {
			//
			objJoin.put("id", myAid);
			objJoin.put("userName", userName);
			//Key
			chkKey.put(Dictionary.key, objJoin);
		}
		catch (JSONException e){
			System.out.println("錯誤" +  e.toString());
		}
		return chkKey.toString();
	}
}
