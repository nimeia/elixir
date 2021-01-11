package company.project.service.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户定时任务
 *
 * @author huang
 */
@Slf4j
public class UserSyncJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        log.info("任务完成");
        ReturnT<String> t = new ReturnT<>(200, "任务完成");
        return t;
    }
}
