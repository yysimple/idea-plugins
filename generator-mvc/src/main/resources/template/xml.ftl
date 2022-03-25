<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${_package}.mapper.DemoMapper">

    <resultMap id="BaseResultMap" type="${_package}.domain.Demo">
        <id property="id" column="id" jdbcType="BIGINT"/>
        <result property="demoDesc" column="demo_desc" jdbcType="VARCHAR"/>
    </resultMap>

    <sql id="Base_Column_List">
        id,demo_desc
    </sql>
</mapper>