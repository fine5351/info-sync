package com.example.infosync.mapper;

import com.example.infosync.model.RequestData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RequestDataMapper {
    @Insert("INSERT INTO request_data (name, roc_id, address) VALUES (#{name}, #{rocId}, #{address})")
    void insert(RequestData data);

    @Select("SELECT id, name, roc_id, address FROM request_data ORDER BY id DESC LIMIT 1")
    RequestData selectLatest();
} 