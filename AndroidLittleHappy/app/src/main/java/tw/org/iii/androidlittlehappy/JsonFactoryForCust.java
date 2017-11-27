package tw.org.iii.androidlittlehappy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonFactoryForCust {

	public JsonFactoryForCust() {
	}

	public CCustomers parse(String myJarray) {
		//org.json 解析JSON字串，把JSON內容丟給CCustomer物件
		JSONArray jarray;
		CCustomers cust = null;
		try {
			jarray = new JSONArray(myJarray);
			for(int i=0; i<jarray.length(); i++){
				JSONObject obj = jarray.getJSONObject(i);
				cust = new CCustomers();
				cust.setfID(obj.getInt("fID"));
				cust.setfUserName(obj.getString("fUserName"));
				cust.setfPassword(obj.getString("fPassword"));
				cust.setfEmail(obj.getString("fEmail"));
				cust.setfNickName(obj.getString("fNickName"));
				cust.setfMascot(obj.getString("fMascot"));
				cust.setfDefaultStar(obj.getString("fDefaultStar"));
				cust.setfDefaultTime(obj.getString("fDefaultTime"));
				cust.setfStar(obj.getString("fStar"));
				return cust;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cust;
	}

	public String stringify(CCustomers myCust) {
		//使用org.json API 製作 JSON字串
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("fID", myCust.getfID());
			obj.put("fUserName", myCust.getfUserName());
			obj.put("fPassword", myCust.getfPassword());
			obj.put("fEmail", myCust.getfEmail());
			obj.put("fNickName", myCust.getfNickName());
			obj.put("fMascot", myCust.getfMascot());
			obj.put("fDefaultStar", myCust.getfDefaultStar());
			obj.put("fDefaultTime", myCust.getfDefaultTime());
			obj.put("fStar", myCust.getfStar());
			ary.put(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ary.toString();
	}
}
