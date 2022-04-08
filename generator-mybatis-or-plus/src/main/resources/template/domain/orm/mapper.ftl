<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package}.${simpleName}">

    <resultMap id="${model.varName}Map" type="${model.package}.${model.simpleName}">
        <#list model.fields as field>
        <#if field.id>
        <id property="${field.name}" column="${field.columnName}"/>
        <#else>
        <result property="${field.name}" column="${field.columnName}"/>
        </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list model.fields as field><#if field_index!=0>,</#if>${field.columnName}</#list>
    </sql>

    <!--通过ID查询单个${model.comment}-->
    <select id="find${model.simpleName}ById" resultMap="${model.varName}Map">
        SELECT
            <include refid="Base_Column_List"/>
        FROM ${model.tableName}
        WHERE <#list model.fields as field><#if field.id>${field.columnName}</#if></#list>=<#noparse>#{id}</#noparse>
    </select>

    <!--多条件查询${model.simpleName}列表-->
    <select id="list${model.simpleName}" resultMap="${model.varName}Map">
        SELECT
            <include refid="Base_Column_List"/>
        FROM ${model.tableName}
        WHERE 1=1
        <#list model.fields as field>
        <if test="${field.name} != null">
            and ${field.columnName}=<#noparse>#{</#noparse>${field.name}}
        </if>
        </#list>
    </select>

    <!--新增${model.comment}-->
    <insert id="insert${model.simpleName}">
        INSERT INTO ${model.tableName}(<#list model.fields as field><#if field_index!=0>,</#if>${field.columnName}</#list>)
        VALUES (<#list model.fields as field><#if field_index!=0>,</#if><#noparse>#{</#noparse>${field.name}}</#list>)
    </insert>

    <!--修改${model.comment}-->
    <update id="update${model.simpleName}">
        UPDATE ${model.tableName} SET <#list model.fields as field><#if !field.id><#if model.fields[0].id&&field_index!=1>,</#if>${field.columnName}=<#noparse>#{</#noparse>${field.name}}</#if></#list>
        WHERE <#list model.fields as field><#if field.id>${field.columnName}=<#noparse>#{</#noparse>${field.name}}</#if></#list>
    </update>

    <!--通过ID删除单个${model.comment}-->
    <delete id="delete${model.simpleName}ById">
        DELETE FROM ${model.tableName}
        WHERE <#list model.fields as field><#if field.id>${field.columnName}</#if></#list>=<#noparse>#{id}</#noparse>
    </delete>

</mapper>