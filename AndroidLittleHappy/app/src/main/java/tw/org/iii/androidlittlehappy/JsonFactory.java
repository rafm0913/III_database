package tw.org.iii.androidlittlehappy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JsonFactory {
	
	public JsonFactory() {
	}
	
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
	      		activity.setType(obj.getInt("type"));
	            activity.setTitle(obj.getString("title"));
	            activity.setContent(obj.getString("content"));
	            activity.setCreateTime(obj.getString("createTime"));
	            activity.setLimitTime(obj.getString("limitTime"));
	            activity.setLimitStar(obj.getInt("limitStar"));
	            activity.setGpsX(obj.getDouble("gpsX"));
	            activity.setGpsY(obj.getDouble("gpsY"));
	            activity.setState(obj.getString("state"));
	            activity.setCreator(obj.getString("creator"));
	              //factory.GetAll().add(activity);
	              return activity;
	      	  }	 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return activity;
	}
	
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
}
