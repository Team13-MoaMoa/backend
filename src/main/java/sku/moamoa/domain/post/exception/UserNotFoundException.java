package sku.moamoa.domain.post.exception;


import sku.moamoa.global.error.ErrorCode;
import sku.moamoa.global.error.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND_ERROR);
    }
}