package com.supercode.bto.web.controller;

import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScJldjbService;
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
 * @date 2021/7/20 10:17
 */
@RestController
@RequestMapping(value = "jldjb")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "生产登记表", tags = { "生产登记表" })
public class ScJldjbController {
    private Logger logger = LoggerFactory.getLogger(ScJldjbController.class);

    @Autowired
    private IScJldjbService scJldjbService;

    @ApiOperation(value = "添加生产登记记录",notes = "添加生产登记记录")
    @ApiResponses({@ApiResponse(code = 10000, message = "添加成功"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "insert" ,method = RequestMethod.POST)
    public RestResult insert(@RequestBody ScJldjb scJldjb
    ){
        try {
            Date now = new Date();
            logger.info("insert {}",now.getTime());
            scJldjb.setDj_bh(String.valueOf(now.getTime()));
            return ResultUtil.success(scJldjbService.insert(scJldjb));
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }


    }
}
