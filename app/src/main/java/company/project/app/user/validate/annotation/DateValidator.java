package company.project.app.user.validate.annotation;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author huang
 */
public class DateValidator implements ConstraintValidator<Date, String> {

    private String dateFormat;

    @Override
    public void initialize(Date constraintAnnotation) {

        // 获取时间格式
        this.dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (StrUtil.isBlank(value)) {
            return true;
        }
        try {
            java.util.Date date = DateUtil.parse(value, dateFormat);
            return date != null;
        } catch (Exception e) {
            return false;
        }
    }
}
