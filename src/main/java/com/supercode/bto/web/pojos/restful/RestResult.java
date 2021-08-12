/***********************************************************************
 * Module:  RestResult.java
 * Author:  Sun
 * Purpose: Defines the Class RestResult
 ***********************************************************************/

package com.supercode.bto.web.pojos.restful;

/**
 * restful返回结果
 * RestResult */
public class RestResult {
   private RestMeta meta;
   private Object data;

   public RestResult() {
   }

   public RestResult(RestMeta meta, Object data) {
      this.meta = meta;
      this.data = data;
   }

   public RestResult(String msg) {
      this.meta = new RestMeta(ResultCodeEnum.SUCCESS.getCode(), msg);
   }

   public RestResult(ResultCodeEnum resultCodeEnum) {
      this.meta = new RestMeta(resultCodeEnum.getCode(),resultCodeEnum.getMessage());
   }

   public RestResult(ResultCodeEnum resultCodeEnum, Object data) {
      this(resultCodeEnum);
      this.data = data;
   }

   public RestMeta getMeta() {
      return meta;
   }

   public void setMeta(RestMeta meta) {
      this.meta = meta;
   }

   public Object getData() {
      return data;
   }

   public void setData(Object data) {
      this.data = data;
   }

   /**
    *   
    * 
    * RestMeta */
   public class RestMeta {
      private String code;
      private String message;

      public RestMeta() {

      }

      public RestMeta(String code, String message) {
         this.code = code;
         this.message = message;
      }

      public String getCode() {
         return code;
      }

      public void setCode(String code) {
         this.code = code;
      }

      public String getMessage() {
         return message;
      }

      public void setMessage(String message) {
         this.message = message;
      }
   }
}