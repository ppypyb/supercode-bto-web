package com.supercode.bto.web.controller;

import com.supercode.bto.web.entity.ScCpgxde;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScCpgxdeService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/20 10:51
 */
@RestController
@RequestMapping(value = "cpgxde")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "产品工序定额表", tags = { "产品工序定额表" })
public class ScCpgxdeController {
    private Logger logger = LoggerFactory.getLogger(ScCpgxdeController.class);

    @Autowired
    private IScCpgxdeService scCpgxdeService;

    @ApiOperation(value = "添加产品工序定额记录",notes = "添加产品工序定额记录")
    @ApiResponses({@ApiResponse(code = 10000, message = "添加成功"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "insert" ,method = RequestMethod.POST)
    public RestResult insert(@RequestBody ScCpgxde scCpgxde){
        try {
            logger.info("insert scCpgxde {}",scCpgxde);
            return scCpgxdeService.insert(scCpgxde);
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }


    }
}
