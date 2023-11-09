package com.naidelii.data.domain.vo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author naidelii
 * 请求分页
 */
@Data
@ApiModel("基础翻页请求")
public class BasePageRequest<T> {

    @ApiModelProperty("页面大小")
    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    @ApiModelProperty("页面索引（从1开始）")
    private Integer pageNo = 1;

    /**
     * 获取MybatisPlus的page
     *
     * @return Page<T>
     */
    public Page<T> plusPage() {
        return new Page<>(pageNo, pageSize);
    }

}
