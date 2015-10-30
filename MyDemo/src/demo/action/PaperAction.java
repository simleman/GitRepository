package demo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.entity.Paper;
import demo.entity.Questiontype;
import demo.entity.Station;
import demo.entity.Testquestions;
import demo.service.PaperService;
import demo.service.QuestionTypeService;
import demo.service.StationService;
import demo.service.TestQuestionsService;
import demo.util.JavaExportDoc;
import demo.util.PageModel;
import demo.util.TqsVo;
/*
 * 试卷的增、删、查的方法
 */
public class PaperAction extends ActionSupport {

	private PaperService paperService;

	private StationService sser;//岗位的service方法

	private TestQuestionsService tqSer;//题目的service方法

	private QuestionTypeService qtSer;//题目类型的service方法

	private String count;//选择的题目类型对应的题目个数

	private String qtid;//题目类型ID

	private Paper paperList;

	private List<Questiontype> qtList;

	private TqsVo tqsVo;//前后台交互的vo类

	public List<Questiontype> getQtList() {
		return qtList;
	}

	public void setQtList(List<Questiontype> qtList) {
		this.qtList = qtList;
	}

	public QuestionTypeService getQtSer() {
		return qtSer;
	}

	public void setQtSer(QuestionTypeService qtSer) {
		this.qtSer = qtSer;
	}

	public Paper getPaperList() {
		return paperList;
	}

	public void setPaperList(Paper paperList) {
		this.paperList = paperList;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getQtid() {
		return qtid;
	}

	public void setQtid(String qtid) {
		this.qtid = qtid;
	}

	public TestQuestionsService getTqSer() {
		return tqSer;
	}

	public void setTqSer(TestQuestionsService tqSer) {
		this.tqSer = tqSer;
	}

	public TqsVo getTqsVo() {
		return tqsVo;
	}

	public void setTqsVo(TqsVo tqsVo) {
		this.tqsVo = tqsVo;
	}

	public PaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}

	public StationService getSser() {
		return sser;
	}

	public void setSser(StationService sser) {
		this.sser = sser;
	}

	//分页查询试卷
	public String queryPage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNo = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PageModel<TqsVo> page = paperService.getPageModel(pageNo, pageSize);
		String json = JSONObject.fromObject(page).toString();
		out.println(json);
		out.flush();
		out.close();
		return SUCCESS;
	}

	//条件分页查询试卷
	public String queryPageByName() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNo = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		String type = request.getParameter("type");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PageModel<TqsVo> page = paperService.getPageModelByName(pageNo,
				pageSize, type);
		String json = JSONObject.fromObject(page).toString();
		out.flush();
		out.close();
		return SUCCESS;
	}

	//批量删除
	public String deleteByIds() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String[] ids = request.getParameterValues("ids");
		try {
			paperService.delete(ids);
			out.println("{success:true, msg:'恭喜，试题删除成功！'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，试题删除失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}

	public String update() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("{success:true, msg:'恭喜，试题修改成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}
	
	
	//人工生成
	public String addByMan() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		Paper paper = new Paper();
		Station station = new Station();
		String[] stationids = request.getParameter("sid").split(",");
		if (stationids.length == 1) {
			station = sser.getOneById(tqsVo.getStationid());
			paper.setStation(station);
		}
		String[] tqids = request.getParameter("tqids").split(",");
		for (int i = 0; i < tqids.length; i++) {
			int tqid = Integer.parseInt(tqids[i]);
			Testquestions tq = tqSer.getOneById(tqid);
			paper.getTestquestions().add(tq);
		}
		paper.setMakedate(tqsVo.getMakedate());
		paper.setMemo(tqsVo.getMemo());
		paper.setPapername(tqsVo.getPapername());

		try {
			paperService.save(paper);
			out.println("{success:true, msg:'恭喜，试卷添加成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，试卷添加失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}
	
	
	//系统随机生成
	public String addBySystem() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String[] qtids = qtid.split(",");
		String[] counts = count.split(",");
		Paper paper = new Paper();
		Station station = new Station();
		String[] stationids = request.getParameter("stid").split(",");
		if (stationids.length == 1) {
			station = sser.getOneById(tqsVo.getStationid());
			paper.setStation(station);
		}else if(stationids.length > 1){
			tqsVo.setStationids(stationids);
			tqsVo.setStationid(null);
		}
		for (int i = 0; i < qtids.length; i++) {
			int id = Integer.parseInt(qtids[i].trim());
			int count = Integer.parseInt(counts[i].trim());
			List list = null;
			list = tqSer.RandTestquestions(tqsVo, id, count);
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					Integer tqid = null;
					if (list.get(j) != null)
						tqid = Integer.parseInt(list.get(j).toString());
					Testquestions tq = tqSer.getOneById(tqid);
					paper.getTestquestions().add(tq);
				}
			}
		}
		paper.setPapername(tqsVo.getPapername());
		paper.setMemo(tqsVo.getMemo());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		paper.setMakedate(format.format(new Date()).toString());
		try {
			paperService.save(paper);
			out.println("{success:true, msg:'恭喜，试卷添加成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，试卷添加失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}

	//预览的方法
	public String preview() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer paperid = null;
		if (request.getParameter("paperid") != null)
			paperid = Integer.parseInt(request.getParameter("paperid"));
		paperList = paperService.getOneById(paperid);
		List list = qtSer.listquestiontype(paperid);
		qtList = new ArrayList<Questiontype>();
		if (list != null) {
			for (int j = 0; j < list.size(); j++) {
				Integer qtid = null;
				if (list.get(j) != null)
					qtid = Integer.parseInt(list.get(j).toString());
				Questiontype qt = qtSer.getOneById(qtid);
				qtList.add(qt);
			}
		}
		return SUCCESS;

	}

	//文件导出与下载
	public String printpreview() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		Integer paperid = null;
		if (request.getParameter("paperid") != null) {
			paperid = Integer.parseInt(request.getParameter("paperid"));
		} 
		paperList = paperService.getOneById(paperid);
		String filename = paperList.getId()+".doc";
/*		String name=new String(filename.getBytes("iso8859-1"),"UTF-8");//处理中文文件名的问题
*/		List list = qtSer.listquestiontype(paperid);
		qtList = new ArrayList<Questiontype>();
		if (list != null) {
			for (int j = 0; j < list.size(); j++) {
				Integer qtid = null;
				if (list.get(j) != null)
					qtid = Integer.parseInt(list.get(j).toString());
				Questiontype qt = qtSer.getOneById(qtid);
				qtList.add(qt);
			}
		}
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("paperList", paperList);
		datamap.put("qtList", qtList);
		JavaExportDoc dh = new JavaExportDoc();
		String filepath="D:\\Program Files (x86)\\apache-tomcat-7.0.61\\webapps\\MyDemo\\download\\";
		String jsonfilepath = "./download/";
		dh.createDoc(filename,filepath, datamap);
		try {
/*			out.println("{success:true, msg:'恭喜，导出word成功！'}");
*/			out.println("{'path':'"+jsonfilepath+filename+"'}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，导出word失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;

	}
}
