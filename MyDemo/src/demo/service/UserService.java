package demo.service;

import java.util.List;

import demo.entity.Mydemo;
import demo.entity.Userclass;

public interface UserService {

	 List<Mydemo> list()throws Exception;
	
	 Mydemo getUserByUAndP(String username, String password)throws Exception;

	 List<Userclass> classList()throws Exception;

	 Mydemo getOneById(Integer id)throws Exception;

	 void save(Mydemo mydemo)throws Exception;

	 void delete(Mydemo mydemo)throws Exception;

	 void update(Mydemo mydemo)throws Exception;
	
	 Userclass getClassById(Integer id)throws Exception;

}
