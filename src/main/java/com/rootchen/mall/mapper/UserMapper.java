package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 用户名登陆
     * @param userName
     * @param password
     * @return
     */
    User selectByUserName(@Param("userName") String userName, @Param("password") String password);

    /***
     * 邮箱登陆
     * @param userName
     * @param password
     * @return
     */
    User selectByEmail(@Param("email") String userName, @Param("password") String password);

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    Integer checkUserName(@Param("userName") String userName);

    /**
     * 检查邮箱是否存在
     * @param email
     * @return
     */
    Integer checkEmail(@Param("email") String email);

}
