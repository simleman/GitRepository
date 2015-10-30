package demo.util;

import java.util.List;

import demo.entity.Questiontype;
import demo.entity.Station;

public class TypeUtil {
	//批量删除时，将字符串数组转化为integer数组
	public static Integer[] StringArray2IntegerArray(String[] ids) {
		Integer[] nums = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++)
			if (ids[i] != null)
				nums[i] = Integer.parseInt(ids[i]);
		return nums;
	}

	//选项字符串的拼接
	public static String convert(String option) {
		StringBuffer s = new StringBuffer("");
		String[] optionValues = option.split(",");
		if (optionValues != null) {
			for (int i = 0; i < optionValues.length; i++) {
				s = s.append((char) (65 + i) + "、" + optionValues[i] + ".");
			}
		}
		return s.toString().trim();
	}

	//将questiontype类转化为json数组
	public static String ObjectToJson(Integer count, List<Questiontype> list) {
		StringBuffer json = new StringBuffer();
		if (count != null) {
			json = json.append("{'count':" + count + ",'pageData':[");
		} else {
			json = json.append("[");
		}
		for (int i = 0; i < list.size(); i++) {
			json.append("{");
			json.append("'ischoose':" + list.get(i).getIschoose() + ",");
			json.append("'isjudge':" + list.get(i).getIsjudge() + ",");
			json.append("'type':'" + list.get(i).getType() + "',");
			json.append("'anwsercount':" + list.get(i).getAnwsercount() + ",");
			json.append("'id':" + list.get(i).getId());
			json.append("}");
			if (i != list.size() - 1) {
				json.append(",");
			}
		}
		if (count != null) {
			json.append("]}");
		} else {
			json.append("]");
		}
		return json.toString();
	}
	
	//将station类转化为json数组
	public static String ObjectToJson2(Integer count, List<Station> list) {
		StringBuffer json = new StringBuffer();
		if (count != null) {
			json = json.append("{'count':" + count + ",'pageData':[");
		} else {
			json = json.append("[");
		}
		for (int i = 0; i < list.size(); i++) {
			json.append("{");
			json.append("'name':'" + list.get(i).getName() + "',");
			json.append("'id':" + list.get(i).getId());
			json.append("}");
			if (i != list.size() - 1) {
				json.append(",");
			}
		}
		if (count != null) {
			json.append("]}");
		} else {
			json.append("]");
		}
		return json.toString();
	}

	//将testquestions类转化为json数组
	public static String ObjectToJson3(Integer count, List<TqsVo> list) {
		StringBuffer json = new StringBuffer();
		if (count != null) {
			json = json.append("{'count':" + count + ",'pageData':[");
		} else {
			json = json.append("[");
		}
		for (int i = 0; i < list.size(); i++) {
			json.append("{");
			json.append("'correct':'" + list.get(i).getCorrect() + "',");
			json.append("'isjudge':'" + list.get(i).getIsjudge() + "',");
			json.append("'qtid':" + list.get(i).getQtid() + ",");
			json.append("'qttype':'" + list.get(i).getQttype() + "',");
			json.append("'questionname':'" + list.get(i).getQuestionname()
					+ "',");
			json.append("'questionoption':'" + list.get(i).getQuestionoption()
					+ "',");
			json.append("'stationid':" + list.get(i).getStationid() + ",");
			json.append("'stationname':'" + list.get(i).getStationname() + "',");
			json.append("'tqid':" + list.get(i).getTqid());
			json.append("}");
			if (i != list.size() - 1) {
				json.append(",");
			}
		}
		if (count != null) {
			json.append("]}");
		} else {
			json.append("]");
		}
		return json.toString();
	}
}
