package com.supercode.bto.web.controller;

import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScScjlbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/18 11:58
 */
@RestController
@RequestMapping(value = "scjlb")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "生产记录表", tags = { "生产记录表" })
public class ScScjlbController {
    private Logger logger = LoggerFactory.getLogger(ScScjlbController.class);

    @Autowired
    private IScScjlbService scScjlbService;

    @ApiOperation(value = "添加生产记录",notes = "添加生产聚集了")
    @ApiResponses({@ApiResponse(code = 10000, message = "添加成功"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "insert" ,method = RequestMethod.POST)
    public RestResult insert(@RequestBody ScScjlb scScjlb){
        try {
            Date now = new Date();
            logger.info("insert {}",now.getTime());
            scScjlb.setDj_jlbh(String.valueOf(now.getTime()));
            scScjlbService.insert(scScjlb);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }


    }


}
