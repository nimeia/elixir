package company.project.core.utils.jdbc;

import com.github.drinkjava2.jdialects.Dialect;
import company.project.core.base.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Roney on 2019/9/6.
 * Edited by huang 2020/12/1.
 * JDBC分页查询
 *
 * @author Roney
 * @date 2019-09-06 21:05
 *
 */
@Component
public class JdbcPage {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    /**
     * @param sql
     * @param elementType 返回需要封装的javaVo
     * @param page
     * @param pageSize
     * @param args        sql参数
     * @return PageVo
     */
    public <T> ResultPage<T> query(String sql, Class<T> elementType, Integer page, Integer pageSize, Object... args) throws Exception {
        SqlReCreateFactory sqlReCreateFactory = new SqlReCreateFactory<Object>(sql, page, pageSize, args).invoke();
        sql = sqlReCreateFactory.getSql();
        List<Object> argsList = sqlReCreateFactory.getArgsList();
        ResultPage<T> pageVo = sqlReCreateFactory.getJdbcResultPage();
        List<T> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(elementType), argsList.toArray());
        pageVo.setData(result);
        return pageVo;
    }

    /**
     * @param sql      默认封装成List<Map<String,Object>>
     * @param page
     * @param pageSize
     * @param args     sql参数
     * @return PageVo
     */
    public ResultPage query(String sql, Integer page, Integer pageSize, Object... args) throws Exception {
        SqlReCreateFactory sqlReCreateFactory = new SqlReCreateFactory<Object>(sql, page, pageSize, args).invoke();
        sql = sqlReCreateFactory.getSql();
        List<Object> argsList = sqlReCreateFactory.getArgsList();
        ResultPage<Map<String, Object>> pageVo = sqlReCreateFactory.getJdbcResultPage();
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, argsList.toArray());
        pageVo.setData(result);
        return pageVo;
    }

    private class
    SqlReCreateFactory<T> {
        private String sql;
        private int page;
        private int pageSize;
        private Object[] args;
        private ResultPage<T> JdbcResultPage;
        private List<Object> argsList;

        public SqlReCreateFactory(String sql, int page, int pageSize, Object... args) {
            this.sql = sql;
            this.page = page;
            this.pageSize = pageSize;
            this.args = args;
        }

        public SqlReCreateFactory invoke() {
            JdbcResultPage = new ResultPage<T>(new Long(0), new Long(pageSize));
            String sqlCount = "select count(*) from (" + sql + ")  as count_table";
            Integer count = jdbcTemplate.queryForObject(sqlCount, args, Integer.class);   //查询页数
            JdbcResultPage.setTotalCount(count);
            int pageNum = 0;
            if (count != null && count > 0) {
                if (count % pageSize == 0) {
                    pageNum = count / pageSize;
                } else {
                    pageNum = count / pageSize + 1;
                }
                JdbcResultPage.setPageNo(pageNum);
            }
            argsList = new ArrayList<>();
            for (Object arg : args) {
                argsList.add(arg);
            }

            Dialect dialect = Dialect.guessDialect(dataSource);

            sql = dialect.pagin(page, pageSize, sql);
            return this;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public ResultPage<T> getJdbcResultPage() {
            return JdbcResultPage;
        }

        public void setJdbcResultPage(ResultPage<T> jdbcResultPage) {
            JdbcResultPage = jdbcResultPage;
        }

        public List<Object> getArgsList() {
            return argsList;
        }

        public void setArgsList(List<Object> argsList) {
            this.argsList = argsList;
        }
    }
}