package company.project.app.user.web;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import company.project.app.user.excel.UserExportVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("excel")
public class ExcelController {

    @RequestMapping("export")
    public void export(HttpServletResponse response){
        List<UserExportVo> userExportVoList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            UserExportVo userExportVo = UserExportVo.builder()
                    .age(100+i)
                    .username("用户"+i)
                    .build();
            userExportVoList.add(userExportVo);
        }

        Workbook workbook = DefaultExcelBuilder.of(UserExportVo.class).build(userExportVoList);
        AttachmentExportUtil.export(workbook, "用户列表",response);

    }
}
