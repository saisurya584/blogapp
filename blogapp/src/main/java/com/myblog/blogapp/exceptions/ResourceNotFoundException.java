package com.myblog.blogapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
     private String resourceName;
     private String field;
     private long fieldValue;

     public ResourceNotFoundException(String resourceName,String field,long fieldValue)
     {
          super(String.format("%s not found with %s:'%s'",resourceName,field,fieldValue));
          this.resourceName=resourceName;
          this.field=field;
          this.fieldValue=fieldValue;
     }

     public String getResourceName() {
          return resourceName;
     }

     public String getField() {
          return field;
     }

     public long getFieldValue() {
          return fieldValue;
     }
}
