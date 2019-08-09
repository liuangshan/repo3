package org.lyy.Test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.lyy.dao.UserDao;
import org.lyy.entity.User;


import java.io.InputStream;
import java.util.List;

public class DaoTest {

    @Test
    public void testQueryAll() throws Exception{
        //加载配置文件
        InputStream is= Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建工厂
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(is);
        //创建sqlSession对象
        SqlSession session=factory.openSession();
        //获取代理对象
        UserDao dao=session.getMapper((UserDao.class));

        User user=new User();
        user.setName("fff");
        user.setAge(58);
        dao.insertUser(user);

        //测试queryAll
        List<User> userList=dao.queryAll();
        for(User user1:userList)
        {
            System.out.println(user1.getId()+" "+user1.getName()+" "+user1.getAge());
        }
        session.close();
        is.close();
    }
}
