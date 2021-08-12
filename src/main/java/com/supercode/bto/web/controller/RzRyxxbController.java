package com.supercode.bto.web.controller;

import com.supercode.bto.web.entity.RzRyxxb;
import com.supercode.bto.web.interceptor.PassToken;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IBtoWebService;
import com.supercode.bto.web.service.IRzRyxxbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/8 21:06
 */
@RestController
@RequestMapping(value = "rzryxxb")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "人员信息表", tags = { "人员信息表" })
public class RzRyxxbController {
    private Logger logger = LoggerFactory.getLogger(RzRyxxbController.class);

    @Autowired
    IRzRyxxbService rzRyxxbService;

    @ApiOperation(value = "根据人员编号查询人员信息",notes = "根据人员编号查询人员信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rybh"  , value = "人员编号",paramType = "query" , required = true , dataType = "String")
    })
    @PassToken
    @RequestMapping(value = "queryRyxxbByRybh",method = RequestMethod.GET)
    public RestResult queryRyxxbByRybh(@RequestParam(name = "rybh")String rybh){
        try {
            logger.info("queryRyxxbByRybh rybh {}",rybh);
            List<RzRyxxb> rzRyxxbList = rzRyxxbService.selectByRybh(rybh);
            return ResultUtil.success(rzRyxxbList);
        } catch (Exception e) {
            logger.error("queryRyxxbByRybh {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }




}
