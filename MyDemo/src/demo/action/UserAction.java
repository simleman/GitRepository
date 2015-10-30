package demo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.entity.Mydemo;
import demo.entity.Userclass;
import demo.service.UserService;
/*
 * 与试卷系统无关
 */
public class UserAction extends ActionSupport {

	private UserService userser;

	private Mydemo mydemo;

	private List<Mydemo> userList;

	private List<Userclass> classList;

	private String message;

	private Integer classid;

	public void setUserser(UserService userser) {
		this.userser = userser;
	}

	public String login() throws Exception {
		Mydemo user = userser.getUserByUAndP(mydemo.getName(),
				mydemo.getPassword());
		if (user != null) {
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("loginUser", user);
			message = "true";
			return SUCCESS;
		} else {
			message = "用户名或密码错误";
			return INPUT;
		}
	}

	public String show() throws Exception {
		userList = userser.list();
		return SUCCESS;
	}

	public String preadd() throws Exception {
		classList = userser.classList();
		return SUCCESS;
	}

	public String add() throws Exception {
		Userclass uclass = userser.getClassById(classid);
		mydemo.setUserclass(uclass);
		mydemo.setPassword("111111");
		userser.save(mydemo);
		return SUCCESS;
	}

	public String preupdate() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer id = Integer.parseInt(request.getParameter("id"));
		mydemo = userser.getOneById(id);
		classList = userser.classList();
		return SUCCESS;
	}

	public String update() throws Exception {
		Userclass uclass = userser.getClassById(classid);
		mydemo.setUserclass(uclass);
		mydemo.setPassword("111111");
		userser.update(mydemo);
		return SUCCESS;
	}

	public String delete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer id = Integer.parseInt(request.getParameter("id"));
		mydemo = userser.getOneById(id);
		userser.delete(mydemo);
		return SUCCESS;
	}
	
	public String logout() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("loginUser");
		return INPUT;
	}

	public List<Mydemo> getUserList() {
		return userList;
	}

	public void setUserList(List<Mydemo> userList) {
		this.userList = userList;
	}

	public Mydemo getMydemo() {
		return mydemo;
	}

	public void setMydemo(Mydemo mydemo) {
		this.mydemo = mydemo;
	}

	public List<Userclass> getClassList() {
		return classList;
	}

	public void setClassList(List<Userclass> classList) {
		this.classList = classList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

}
