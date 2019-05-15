package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectByParentId(@Param("parentId") Integer parentId);

}
