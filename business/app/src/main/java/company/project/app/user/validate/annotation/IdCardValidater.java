package company.project.app.user.validate.annotation;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCardValidater implements ConstraintValidator<IdCard, String> {

    @Override
    public void initialize(IdCard constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return IdcardUtil.isValidCard(value);
    }
}
