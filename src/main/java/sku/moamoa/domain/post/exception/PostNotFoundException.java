package sku.moamoa.domain.post.exception;

import sku.moamoa.global.error.ErrorCode;
import sku.moamoa.global.error.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(){super(ErrorCode.POST_NOT_FOUND_ERROR);}
}
