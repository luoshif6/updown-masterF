package com.updown.search.service.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * indexName = 主键名称
 * type = 类型，可以自己写
 * shards = 分片数量，默认是5，这里改成1
 * replicas = 复制，默认是1
 *
 * @auther: 闫昊
 * @date: 2019/8/23
 */

@Document(indexName = "SearchData", type = "Data", shards = 1, replicas = 1)
public class SearchData {
    @Id
    @Field(type = FieldType.Long)
    private Long file_id;  //文件id

    @Field(type = FieldType.Keyword)
    private String file_name;   //文件名称

    @Field(type = FieldType.Keyword)
    private Long user_name;  //用户id上传者id




    public Long getFile_id() {
        return file_id;
    }

    public void setFile_id(Long file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Long getUser_name() {
        return user_name;
    }

    public void setUser_name(Long user_name) {
        this.user_name = user_name;
    }
}
