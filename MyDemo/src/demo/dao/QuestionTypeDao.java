package demo.dao;

import java.util.List;

import demo.entity.Questiontype;
/*
 * 题目类型的Dao类
 */
public interface QuestionTypeDao {
	
	int getTqnumByqtid(Integer qtid) throws Exception;
	
	List listquestiontype(Integer paperid)throws Exception;
	
    List <Questiontype> list(int pageNo,int pageSize)throws Exception;
  
    List <Questiontype> listByType(int pageNo,int pageSize,String type)throws Exception;
    
    int getCount()throws Exception;

    int getCountByType(String type)throws Exception;
	
	Questiontype getOneById(Integer id)throws Exception;
	
	void save(Questiontype type)throws Exception;
	
	void delete(Questiontype type)throws Exception;
	
	void update(Questiontype type)throws Exception;
}
