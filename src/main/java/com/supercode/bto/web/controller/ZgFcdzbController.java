package com.supercode.bto.web.controller;

import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.entity.ZgFcdzb;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScJldjbService;
import com.supercode.bto.web.service.IZgFcdzbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/20 11:02
 */
@RestController
@RequestMapping(value = "fcdzb")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "废次品原因对照表", tags = { "废次品原因对照表" })
public class ZgFcdzbController {
    private Logger logger = LoggerFactory.getLogger(ZgFcdzbController.class);

    @Autowired
    private IZgFcdzbService zgFcdzbService;

    @ApiOperation(value = "添加废次品原因对照记录",notes = "添加废次品原因对照录")
    @ApiResponses({@ApiResponse(code = 10000, message = "添加成功"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "insert" ,method = RequestMethod.POST)
    public RestResult insert(@RequestBody ZgFcdzb zgFcdzb){
        try {

            logger.info("insert zgFcdzb {}",zgFcdzb);
            return zgFcdzbService.insert(zgFcdzb);
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }


    }
}
