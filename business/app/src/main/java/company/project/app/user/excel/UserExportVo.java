package company.project.app.user.excel;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.Builder;
import lombok.Data;

@Data
@ExcelModel(sheetName = "用户信息")
@Builder
public class UserExportVo {

    @ExcelColumn(title = "用户ID")
    private Long id;

    @ExcelColumn(title = "年龄")
    private Integer age;

    @ExcelColumn(title = "用户名称")
    private String username;

}
