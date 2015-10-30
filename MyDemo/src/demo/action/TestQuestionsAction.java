package demo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.entity.Questiontype;
import demo.entity.Station;
import demo.entity.Testquestions;
import demo.service.QuestionTypeService;
import demo.service.StationService;
import demo.service.TestQuestionsService;
import demo.util.PageModel;
import demo.util.TqsVo;
import demo.util.TypeUtil;

/*
 * 题目的增、删、改、查
 */
public class TestQuestionsAction extends ActionSupport {

	private TestQuestionsService tqSer;

	private StationService sSer;

	private QuestionTypeService qtSer;

	private TqsVo tqsVo;

	private String option;

	public TqsVo getTqsVo() {
		return tqsVo;
	}

	public void setTqsVo(TqsVo tqsVo) {
		this.tqsVo = tqsVo;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public TestQuestionsService getTqSer() {
		return tqSer;
	}

	public void setTqSer(TestQuestionsService tqSer) {
		this.tqSer = tqSer;
	}

	public StationService getsSer() {
		return sSer;
	}

	public void setsSer(StationService sSer) {
		this.sSer = sSer;
	}

	public QuestionTypeService getQtSer() {
		return qtSer;
	}

	public void setQtSer(QuestionTypeService qtSer) {
		this.qtSer = qtSer;
	}

	public String queryPage() throws Exception {
		String json = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (request.getParameter("start") != null
				&& request.getParameter("limit") != null) {//判断是否分页
			int pageNo = Integer.parseInt(request.getParameter("start"));
			int pageSize = Integer.parseInt(request.getParameter("limit"));
			PageModel<TqsVo> page = tqSer.getPageModel(pageNo, pageSize);
			Integer count = page.getCount();
			List<TqsVo> list = page.getPageData();
			json = TypeUtil.ObjectToJson3(count, list);
		} else if ((request.getParameter("sid") != null && !request
				.getParameter("sid").equals(""))
				|| (request.getParameter("qtid") != null && !request
						.getParameter("qtid").equals(""))) {//判断是否选择岗位与题目类型
			TqsVo tqs = new TqsVo();
			if (request.getParameter("sid") != null
					&& !request.getParameter("sid").equals("")) {
				String[] sids = request.getParameter("sid").split(",");
				if (sids.length == 1) {//判断岗位是否为多选
					int stationid = Integer.parseInt(request
							.getParameter("sid"));
					if (request.getParameter("qtid") != null) {
						int qtid = Integer.parseInt(request
								.getParameter("qtid"));
						tqs.setStationid(stationid);
						tqs.setQtid(qtid);
					} else {
						tqs.setStationid(stationid);
					}
				} else if (sids.length > 1) {
					if (request.getParameter("qtid") != null) {
						int qtid = Integer.parseInt(request
								.getParameter("qtid"));
						tqs.setQtid(qtid);
						tqs.setStationids(sids);
					} else {
						tqs.setStationids(sids);
					}
				}
			} else {
				int qtid = Integer.parseInt(request.getParameter("qtid"));
				tqs.setQtid(qtid);
			}
			List<TqsVo> list = tqSer.list(tqs);
			if (request.getParameter("removeid") != null//移除选框中已经存在的记录
					&& !request.getParameter("removeid").equals("")) {
				String[] removeids = request.getParameter("removeid")
						.split(",");
				for (int i = 0; i < removeids.length; i++) {
					int tqid = Integer.parseInt(removeids[i]);
					for (TqsVo vo : list) {
						if (vo.getTqid() == tqid)
							list.remove(vo);
					}
				}
			}
			json = TypeUtil.ObjectToJson3(null, list);//工具类中，解析json，练练拼写字符串，所以没用jar包自动解析
		} else {
			List<TqsVo> list = tqSer.list(null);
			json = TypeUtil.ObjectToJson3(null, list);
		}
		out.println(json);
		System.out.println(json);
		out.flush();
		out.close();
		return SUCCESS;
	}

	public String queryPageByName() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNo = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		String type = request.getParameter("type");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PageModel<TqsVo> page = tqSer.getPageModelByName(pageNo, pageSize, type);
		String json = JSONObject.fromObject(page).toString();
		out.println(json);
		out.flush();
		out.close();
		return SUCCESS;
	}

	public String deleteByIds() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String[] ids = request.getParameterValues("ids");
		try {
			Integer[] integerIds = TypeUtil.StringArray2IntegerArray(ids);
			for (int i = 0; i < integerIds.length; i++) {
				if(tqSer.getPapernumBytqid(integerIds[i])!=0){
					out.println("{success:false, msg:'对不起，题目类型删除失败！请先删除已关联的试题！'}");
					return SUCCESS;
				}
			}
			tqSer.delete(ids);
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
		Questiontype qt = qtSer.getOneById(tqsVo.getQtid());
		Station station = sSer.getOneById(tqsVo.getStationid());
		Testquestions tq = new Testquestions();
		if (this.getOption() != null && !this.getOption().equals("")) {
			String questionoption = TypeUtil.convert(this.getOption());
			tq.setQuestionoption(questionoption);
		}
		tq.setId(tqsVo.getTqid());
		tq.setCorrect(tqsVo.getCorrect());
		tq.setQuestionname(tqsVo.getQuestionname());
		tq.setQuestiontype(qt);
		tq.setStation(station);
		try {
			tqSer.update(tq);
			out.println("{success:true, msg:'恭喜，试题修改成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}

	public String add() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		Questiontype qt = qtSer.getOneById(tqsVo.getQtid());
		Station station = sSer.getOneById(tqsVo.getStationid());
		Testquestions tq = new Testquestions();
		if (this.getOption() != null && !this.getOption().equals("")) {
			String questionoption = TypeUtil.convert(this.getOption());
			tq.setQuestionoption(questionoption);
		}
		tq.setCorrect(tqsVo.getCorrect());

		tq.setQuestionname(tqsVo.getQuestionname());
		tq.setQuestiontype(qt);
		tq.setStation(station);
		try {
			tqSer.save(tq);
			out.println("{success:true, msg:'恭喜，题目类型添加成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，题目类型添加失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}

	//系统生成中根据岗位动态生成相应题目类型中题目个数的方法
	public String addComBox() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		StringBuffer json = new StringBuffer("[");
		if (request.getParameter("stationid") != null) {
			String[] sids = request.getParameter("stationid").split(",");
			tqsVo = new TqsVo();
			if (sids.length == 1) {
				Integer stationid = Integer.parseInt(request.getParameter("stationid"));
				tqsVo.setStationid(stationid);
			}else if(sids.length > 1){
				tqsVo.setStationids(sids);
			}
			List<TqsVo> list = tqSer.getComBox(tqsVo);
			for (int i = 0; i < list.size(); i++) {
				json.append("{");
				json.append("'qtid':" + list.get(i).getQtid() + ",");
				json.append("'num':" + list.get(i).getNum() + ",");
				json.append("'type':'" + list.get(i).getQttype() + "'");
				json.append("}");
				if (i != list.size() - 1) {
					json.append(",");
				}
			}
			json.append("]");
		}
		System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		return SUCCESS;
	};
}
