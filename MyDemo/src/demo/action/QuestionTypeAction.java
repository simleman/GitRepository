package demo.action;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.entity.Questiontype;
import demo.service.QuestionTypeService;
import demo.util.PageModel;
import demo.util.TypeUtil;

/*
 * 题目类型的增、删、改、查
 */
public class QuestionTypeAction extends ActionSupport {

	private QuestionTypeService qSer;
 
	private Questiontype qtype;

	public Questiontype getQtype() {
		return qtype;
	}

	public void setQtype(Questiontype qtype) {
		this.qtype = qtype;
	}

	public QuestionTypeService getqSer() {
		return qSer;
	}

	public void setqSer(QuestionTypeService qSer) {
		this.qSer = qSer;
	}

	public String queryPage() throws Exception {
		String json = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (request.getParameter("start") != null&& request.getParameter("limit") != null) {
			int pageNo = Integer.parseInt(request.getParameter("start"));//获得每页开始记录
			int pageSize = Integer.parseInt(request.getParameter("limit"));//获得每页的记录数
			PageModel<Questiontype> page = qSer.getPageModel(pageNo, pageSize);
			Integer count = page.getCount();
			List<Questiontype> list = page.getPageData();
			json = TypeUtil.ObjectToJson(count, list);
		} else {
			List<Questiontype> list = qSer.list();
			json = TypeUtil.ObjectToJson(null, list);
		}
		out.println(json);
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
		PageModel<Questiontype> page = qSer.getPageModelByType(pageNo,
				pageSize, type);
		Integer count = page.getCount();
		List<Questiontype> list = page.getPageData();
		String json = TypeUtil.ObjectToJson(count, list);
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
		try {
			String[] ids = request.getParameterValues("ids");
			Integer[] integerIds = TypeUtil.StringArray2IntegerArray(ids);
			for (int i = 0; i < integerIds.length; i++) {
				if(qSer.getTqnumByqtid(integerIds[i])!=0){
					out.println("{success:false, msg:'对不起，题目类型删除失败！请先删除已关联的试题！'}");
					return SUCCESS;
				}
			}
			qSer.delete(ids);
			out.println("{success:true, msg:'恭喜，题目类型删除成功！'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，题目类型删除失败！'}");
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
			qSer.update(qtype);
			out.println("{success:true, msg:'恭喜，题目类型修改成功！'}");
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
		try {
			qSer.save(qtype);
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
}
