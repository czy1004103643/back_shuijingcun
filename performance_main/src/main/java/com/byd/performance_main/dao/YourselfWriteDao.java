package com.byd.performance_main.dao;

import com.byd.performance_main.model.YoursefWriteBean;
import org.apache.ibatis.annotations.Param;

public interface YourselfWriteDao {

    int addYourselfWriteBean(YoursefWriteBean yoursefWriteBean);
    int updateYourselfWriteBean(YoursefWriteBean yoursefWriteBean);
    Integer findIdFromUserIdAndAssessMouth(@Param("userId") String userId,
                                                            @Param("projectName") String projectName,
                                                            @Param("assessMouth") String assessMouth);
    YoursefWriteBean fineYoursefWriteBean(@Param("userId") String userId,
                                          @Param("currentProjectName") Integer currentProjectName,
                                          @Param("assessMouth") String assessMouth);
}
