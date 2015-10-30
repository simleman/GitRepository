package demo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.entity.Station;
import demo.service.StationService;
import demo.util.PageModel;
import demo.util.TypeUtil;

/*
 * 岗位类型的增、删、改、查
 */
public class StationAction extends ActionSupport {
	private StationService sSer;

	private Station stype;

	public Station getStype() {
		return stype;
	}

	public void setStype(Station stype) {
		this.stype = stype;
	}

	public StationService getsSer() {
		return sSer;
	}

	public void setsSer(StationService sSer) {
		this.sSer = sSer;
	}

	public String queryPage() throws Exception {
		String json = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (request.getParameter("start") != null
				&& request.getParameter("limit") != null) {
			int pageNo = Integer.parseInt(request.getParameter("start"));
			int pageSize = Integer.parseInt(request.getParameter("limit"));
			PageModel<Station> page = sSer.getPageModel(pageNo, pageSize);
			Integer count = page.getCount();
			List<Station> list = page.getPageData();
			json = TypeUtil.ObjectToJson2(count, list);
		}else{
			List<Station> list = sSer.list();
			json = TypeUtil.ObjectToJson2(null, list);
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
		PageModel<Station> page = sSer.getPageModelByName(pageNo, pageSize,
				type);
		Integer count = page.getCount();
		List<Station> list = page.getPageData();
		String json = TypeUtil.ObjectToJson2(count, list);
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
				if(sSer.getTqnumBystid(integerIds[i])!=0){
					out.println("{success:false, msg:'对不起，题目类型删除失败！请先删除已关联的试题！'}");
					return SUCCESS;
				}
			}
			sSer.delete(ids);
			out.println("{success:true, msg:'恭喜，岗位类型已删除成功！'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，岗位类型删除失败！'}");
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
			sSer.update(stype);
			out.println("{success:true, msg:'恭喜，岗位类型修改成功！'}");
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
			sSer.save(stype);
			out.println("{success:true, msg:'恭喜，岗位类型添加成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false, msg:'对不起，岗位类型添加失败！'}");
		} finally {
			out.flush();
			out.close();
		}
		return SUCCESS;
	}

}
