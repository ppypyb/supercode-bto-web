package com.supercode.bto.web.utils.restful;


import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;

public class ResultUtil {
	
    public static RestResult success(Object object){
        RestResult result = new RestResult(ResultCodeEnum.SUCCESS,object);
        return result;
    }

    public static RestResult success(String msg){
        RestResult result = new RestResult(msg);
        return result;
    }

    public static RestResult success(){
        RestResult result = new RestResult(ResultCodeEnum.SUCCESS);
        return result;
    }

    public static RestResult error(String code,String msg,Object body){

        RestResult.RestMeta meta = new RestResult().new RestMeta(code,msg);
        RestResult result = new RestResult(meta,body);

        return result;
    }

    public static RestResult error(ResultCodeEnum resultCodeEnum){
        return new RestResult(resultCodeEnum);
    }

    public static RestResult error(ResultCodeEnum resultCodeEnum, Object body){
        return new RestResult(resultCodeEnum, body);
    }

    public static RestResult illegalArgument_error(){
        RestResult result = new RestResult(ResultCodeEnum.ILLEGAL_FORMAT_ERROR);
        return result;
    }

    public static RestResult empty_file_error() {
        RestResult result = new RestResult(ResultCodeEnum.EMPTY_FILE_ERROR);
        return result;
    }

    public static RestResult illegal_format_error() {
        RestResult result = new RestResult(ResultCodeEnum.ILLEGAL_FORMAT_ERROR);
        return result;
    }
    public static RestResult unknow_error(){
        RestResult result = new RestResult(ResultCodeEnum.UNKNOW_ERROR);
        return result;
    }
}
