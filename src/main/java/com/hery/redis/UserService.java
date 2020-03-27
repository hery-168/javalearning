package com.hery.redis;

/**
 * @author heng
 * @date 2020-03-02 17:11
 * @desc
 */
public interface UserService {

    User save(User user);

    void delete(int id);

    User get(Integer id);
}