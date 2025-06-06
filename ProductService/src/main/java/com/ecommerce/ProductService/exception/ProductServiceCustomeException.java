package com.ecommerce.ProductService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomeException extends RuntimeException{

    private String errorCode;

    public ProductServiceCustomeException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
