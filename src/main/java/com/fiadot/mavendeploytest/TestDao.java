package com.fiadot.mavendeploytest;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao {
		
	@Select("EXEC [DB].[dbo].[SP_MULTIPLE_DB_TEST]")
	int sp_multiple_db_test();
	
}
