package sku.moamoa.domain.post.exception;

import sku.moamoa.global.error.ErrorCode;
import sku.moamoa.global.error.exception.BusinessException;

public class TechStackNotFoundException extends BusinessException {
    public TechStackNotFoundException() {super(ErrorCode.TECH_STACK_NOT_FOUND_ERROR);}
}
