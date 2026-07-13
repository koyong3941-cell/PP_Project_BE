package com.kh.pp.dash.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.dash.model.dto.DashBoardDto;

@Mapper
public interface DashBoardMapper {

	@Select("""
			SELECT
			     COUNT(*) AS totalMembers,
	        SUM(CASE 
	                WHEN TRUNC(ENROLL_DATE) = TRUNC(SYSDATE) 
	                THEN 1 
	                ELSE 0 
	            END) AS todayJoined,
	        SUM(CASE 
	                WHEN TRUNC(EDIT_DATE) = TRUNC(SYSDATE) 
	                THEN 1 
	                ELSE 0 
	            END) AS leaveDate
		    FROM
		        MEMBER
			""")
	DashBoardDto dashBoardResponse();

}
