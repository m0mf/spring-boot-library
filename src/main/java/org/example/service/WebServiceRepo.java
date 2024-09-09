package org.example.service;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("org.example.service.WebServiceRepo")
public interface WebServiceRepo {

    String getMessage(@Param("message") String message);
}
