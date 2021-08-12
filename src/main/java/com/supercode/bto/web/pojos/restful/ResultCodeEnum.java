package com.supercode.bto.web.pojos.restful;

/**
 * @author: cj
 * @create: 2018-04-11 10:21
 * @description:返回码定义
 **/
public enum ResultCodeEnum {

    UNKNOW_ERROR("00000","未知错误"),

    /* 成功状态码:10000 */
    SUCCESS("10000","成功"),
    /*未登录*/
    UN_LOGIN("11000","未登录"),

    /*用户不存在*/
    USER_NON_EXISTENT("11001","登录失败，用户不存在！"),

    /*密码错误*/
    PASS_ERROR("11002","登录失败，密码错误！"),



    /* 文件状态码:20001-29999 */
    EMPTY_FILE_ERROR("20001", "空文件"),
    ILLEGAL_FORMAT_ERROR("20002", "文件格式非法"),
    GET_FILE_STREAM_ERROR("20003", "文件流获取失败"),
    BLANK_CELL_ERROR("20004", "非空字段不允许为空"),
    FILE_TEMPLATE_ERROR("20005", "导入模板生成异常"),
    FILE_EXPORT_ERROR("20006", "文件导出失败"),
    FILE_IMPORT_ERROR("20007", "文件导入失败"),
    FILE_CONTENT_ERROR("20008", "文件导入失败,可能原因：① 存在不相关sheet表格; ② 缺少有效的key标记"),
    ILLEGAL_UPLOAD_FORMAT_ERROR("20009", "上传文件非法,请上传zip压缩包"),
    ILLEGAL_UPLOAD_FILENAME_ERROR("200010", "上传文件非法,请上传文件名为rcaScript.zip"),

    /* 数据状态码:30001-39999 */
    DATA_NON_EXISTENT("30001", "数据不存在"),
    DATA_ALREADY_EXISTENT("30002", "数据已存在"),
    UNIFY_RULE_KEY_ALREADY_EXISTENT("30003", "标准化规则KEY已存在"),
    DATA_EXCEPTION("30004","数据异常"),
    FAILED_UPDATE("30005", "数据库更新错误"),
    REPEAT_SUBMIT("30006","重复提交"),

    /* 参数错误:40001-49999 */
    PARAM_EMPTY("40001", "参数为空"),
    PARAM_ERROR("40002", "参数错误"),

    /* 接口错误:50001-59999 */
    INSIDE_API_INVOKE_ERROR("50001", "内部接口调用异常"),
    OUTSIDE_API_INVOKE_ERROR("50002", "外部接口调用异常"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("50003", "不支持的HTTP请求方法"),

    /* 查询错误:60001-69999 */
    ALARM_NON_EXISTENT("60001", "此告警不存在"),
    SUBSYSTEMID_NON_EXISTENT("60002", "子网编号不存在"),
    ;

    private final String code;

    private final String message;

    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}