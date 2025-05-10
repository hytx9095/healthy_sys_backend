package com.naiyin.healthy.mapper;

import com.naiyin.healthy.model.entity.DoctorDialogue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naiyin.healthy.model.vo.DoctorContact;
import com.naiyin.healthy.model.vo.UserContact;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【doctor_dialogue】的数据库操作Mapper
* @createDate 2025-04-23 12:27:34
* @Entity com.naiyin.healthy.model.entity.DoctorDialogue
*/
public interface DoctorDialogueMapper extends BaseMapper<DoctorDialogue> {

    @Select("SELECT " +
            "u.id AS user_id, " +
            "u.username, " +
            "u.user_avatar, " +
            "latest.content AS last_message, " +
            "DATE_FORMAT(latest.operation_time, '%H:%i') AS last_time, " +
            "COUNT(CASE WHEN d.status = 2 AND d.spokesman = 'user' THEN 1 ELSE NULL END) AS unread " +
            "FROM user u " +
            "JOIN (SELECT user_id, content, operation_time, " +
            "      ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY operation_time DESC) AS rn " +
            "      FROM doctor_dialogue " +
            "      WHERE doctor_id = #{doctorId}) latest " +
            "ON u.id = latest.user_id AND latest.rn = 1 " +
            "LEFT JOIN doctor_dialogue d ON u.id = d.user_id AND d.doctor_id = #{doctorId} " +
            "WHERE u.is_delete = 0 " +
            "GROUP BY u.id, u.username, u.user_avatar, latest.content, latest.operation_time " +
            "ORDER BY latest.operation_time DESC")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "userAvatar", column = "user_avatar"),
            @Result(property = "lastMessage", column = "last_message"),
            @Result(property = "lastTime", column = "last_time"),
            @Result(property = "unread", column = "unread")
    })
    List<UserContact> selectUserChatList(@Param("doctorId") Long doctorId);

    @Select("SELECT " +
            "    doctor_ids.doctor_id, " +
            "    u.username AS doctor_name, " +
            "    u.user_avatar AS doctor_avatar, " +
            "    (SELECT content FROM doctor_dialogue " +
            "     WHERE user_id = #{userId} AND doctor_id = doctor_ids.doctor_id " +
            "     ORDER BY create_time DESC LIMIT 1) AS last_message, " +
            "    DATE_FORMAT((SELECT create_time FROM doctor_dialogue " +
            "     WHERE user_id = #{userId} AND doctor_id = doctor_ids.doctor_id " +
            "     ORDER BY create_time DESC LIMIT 1), '%H:%i') AS last_time, " +
            "    (SELECT COUNT(*) FROM doctor_dialogue " +
            "     WHERE user_id = #{userId} AND doctor_id = doctor_ids.doctor_id " +
            "     AND status = 1 AND spokesman = 'doctor') AS unread " +
            "FROM " +
            "    (SELECT DISTINCT doctor_id FROM doctor_dialogue WHERE user_id = #{userId}) AS doctor_ids " +
            "JOIN " +
            "    user u ON doctor_ids.doctor_id = u.id " +
            "WHERE " +
            "    u.is_delete = 0 " +
            "ORDER BY " +
            "    (SELECT create_time FROM doctor_dialogue " +
            "     WHERE user_id = #{userId} AND doctor_id = doctor_ids.doctor_id " +
            "     ORDER BY create_time DESC LIMIT 1) DESC")
    List<DoctorContact> selectDoctorChatList(@Param("userId") Long userId);
}




