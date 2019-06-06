package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 文件上传返回结果
 * @author: LiChen
 * @create: 2019-06-06 12:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileUploadResultVo {

    @ApiModelProperty("文件上传至服务器后的名字")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String url;
}