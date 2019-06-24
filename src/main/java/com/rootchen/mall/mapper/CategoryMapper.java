package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rootchen.mall.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查找同一个父节点下的所有子节点
     *
     * @param parentId 父节点id
     * @return
     */
    List<Category> selectByParentId(@Param("parentId") Integer parentId);

    /**
     * 查询父节点
     *
     * @param id 子节点id
     * @return
     */
    Category selectByCategoryId(@Param("id") Integer id);

}
