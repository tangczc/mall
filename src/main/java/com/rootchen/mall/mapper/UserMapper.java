package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
public interface UserMapper extends BaseMapper<User> {

    String selectByUserName(@Param("userName") String userName);

}
