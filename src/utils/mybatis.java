package utils;

import Mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;

public class mybatis {
    private static SqlSessionFactory sqlSessionFactory;
    static  {
        try {
            String resource="SqlMapConfig.xml";
            //得到配置文件流
            InputStream inputStream= Resources.getResourceAsStream(resource);

            // 创建会话工厂，传入mybatis的配置文件信息
            sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        //mybatis配置文件

    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void updateUser(User user)throws Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        // 调用UserMapper的方法
        //User user=new User();
        userMapper.updateUser(user);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(user);
    }

    public void insertUser(User user)throws Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        // 调用UserMapper的方法
        //User user=new User();
        userMapper.insertUser(user);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(user);
    }
}
