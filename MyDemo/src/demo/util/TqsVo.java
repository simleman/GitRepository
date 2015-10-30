package demo.util;

/*
 * 用于前后台传递数据的vo类
 */
public class TqsVo {

	private Integer tqid;//试题的id

	private String correct;//正确答案

	private String questionname;//试题名称

	private String questionoption;//选择题的选项

	private Integer stationid;//岗位id

	private String stationname;//岗位名称

	private Integer qtid;//题型ID

	private String qttype;//题型名称
	
	private int isjudge;//是否为判断题

	private long num;

	private Integer paperid;//试卷id

	private String papername;//试卷名称

	private String makedate;//试卷制作日期

	private String memo;//试卷备注
	
	private String[] stationids;//多选时所选的岗位
	

	public TqsVo() {
		super();
	}
	
	public TqsVo(Integer qtid, String qttype, long num) {
		this.qtid = qtid;
		this.qttype = qttype;
		this.num = num;
	}

	public TqsVo(Integer tqid, String correct, String questionname,
			String questionoption, Integer stationid, String stationname,
			Integer qtid, String qttype,int isjudge) {
		super();
		this.tqid = tqid;
		this.correct = correct;
		this.questionname = questionname;
		this.questionoption = questionoption;
		this.stationid = stationid;
		this.stationname = stationname;
		this.qtid = qtid;
		this.qttype = qttype;
		this.isjudge = isjudge;
	}

	public TqsVo(Integer stationid,String stationname, Integer paperid, String papername,
			String makedate, String memo) {
		this.stationid = stationid;
		this.stationname = stationname;
		this.paperid = paperid;
		this.papername = papername;
		this.makedate = makedate;
		this.memo = memo;
	}
	
	public int getIsjudge() {
		return isjudge;
	}

	public void setIsjudge(int isjudge) {
		this.isjudge = isjudge;
	}
	
	public String[] getStationids() {
		return stationids;
	}

	public void setStationids(String[] stationids) {
		this.stationids = stationids;
	}

	public Integer getTqid() {
		return tqid;
	}

	public void setTqid(Integer tqid) {
		this.tqid = tqid;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public String getQuestionname() {
		return questionname;
	}

	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}

	public String getQuestionoption() {
		return questionoption;
	}

	public void setQuestionoption(String questionoption) {
		this.questionoption = questionoption;
	}

	public Integer getStationid() {
		return stationid;
	}

	public void setStationid(Integer stationid) {
		this.stationid = stationid;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public Integer getQtid() {
		return qtid;
	}

	public void setQtid(Integer qtid) {
		this.qtid = qtid;
	}

	public String getQttype() {
		return qttype;
	}

	public void setQttype(String qttype) {
		this.qttype = qttype;
	}

	public Integer getPaperid() {
		return paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid = paperid;
	}

	public String getPapername() {
		return papername;
	}

	public void setPapername(String papername) {
		this.papername = papername;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

}
