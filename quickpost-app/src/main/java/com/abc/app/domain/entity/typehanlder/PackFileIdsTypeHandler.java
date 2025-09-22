package com.abc.app.domain.entity.typehanlder;

import cn.hutool.json.JSONUtil;
import com.abc.app.domain.entity.typehanlder.details.FileIdsArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(FileIdsArray.class)
public class PackFileIdsTypeHandler extends BaseTypeHandler<FileIdsArray> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FileIdsArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONUtil.toJsonStr(parameter));
    }

    @Override
    public FileIdsArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSONUtil.toBean(rs.getString(columnName), FileIdsArray.class);
    }

    @Override
    public FileIdsArray getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSONUtil.toBean(resultSet.getString(i), FileIdsArray.class);
    }

    @Override
    public FileIdsArray getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSONUtil.toBean(callableStatement.getString(i), FileIdsArray.class);
    }
}