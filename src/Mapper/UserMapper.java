package Mapper;

import utils.User;

public interface UserMapper {
    public User findUserById(int id) throws Exception;

    public void insertUser(User user) throws Exception;

    public void deleteUser(int id) throws Exception;

    public void updateUser(User user)throws Exception;
}
