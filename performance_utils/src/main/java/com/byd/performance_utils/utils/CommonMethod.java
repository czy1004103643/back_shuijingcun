package com.byd.performance_utils.utils;

import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.*;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonMethod {

    public static String TIME_FORMAT_yyyy_MM = "yyyy-MM";
    public static String NORMAL_TIME_FORMAT = "yyyy-MM-dd";

    /**
     * 获取当前时间
     */
    public static Date getCurrentDate() {
        Date date = new Date();
        return date;
    }

    /**
     * 获取上个月时间
     */
    public static String getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(CommonMethod.TIME_FORMAT_yyyy_MM);
        String lastMonthTime = sdf.format(calendar.getTime());
        return lastMonthTime;
    }

    /**
     * 获取上个月时间戳
     */
    public static long getLastMonthTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 转换时间为对应的格式
     */
    public static String transformScoreTime(long scoreTime, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(scoreTime);
        return dateFormat.format(date);
    }

    /**
     * 转换时间为时间戳
     */
    public static long transformScoreTimeToTimeStamp(String scoreTime, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(scoreTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            long timestamp = calendar.getTimeInMillis();
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParamInvalidException("The scoreTime is invalid. scoreTime: " + scoreTime);
        }
    }

    /**
     * 获取当前时间戳
     */
    public static long getCurrentTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }

    /**
     * 处理请求发生异常时，返回给前端的json信息
     */
    public static Map<String, Object> formatJsonMessage(Exception e) {
        Map<String, Object> map;
        if (e.getClass().getSimpleName().equals("DuplicateKeyException")) {
            map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, StateCode.DATABASE_ERROR_MESSAGE + ": Duplicate Key Exception", null);
        } else if (e.getClass().getSimpleName().equals("BadSqlGrammarException")) {
            map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, StateCode.DATABASE_ERROR_MESSAGE + ":Bad Sql Grammar Exception", null);
        } else if (e.getClass().getSimpleName().equals(MissingServletRequestParameterException.class.getSimpleName())
                || e.getClass().getSimpleName().equals(NecessaryParameterException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.MISS_PARAMETER, StateCode.MISS_PARAMETER_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(AccountOrPasswordInvalidException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.ACCOUNT_OR_PASSWORD_FAILED, StateCode.ACCOUNT_OR_PASSWORD_FAILED_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(CookieInvalidException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.COOKIE_INVALID, StateCode.COOKIE_INVALID_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(PermissionDeniedException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.PERMISSION_DENIED, StateCode.PERMISSION_DENIED_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(UserIdDuplicateException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.USERID_DUPLCATE, StateCode.USERID_DUPLCATE_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(GroupMemberDuplicateException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.GROUP_MEMBER_DUPLCATE, StateCode.GROUP_MEMBER_DUPLCATE_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(ParamInvalidException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, StateCode.PARAM_INVALID_MESSAGE + ": " + e.getMessage(), null);
        } else if (e.getClass().getSimpleName().equals(DatabaseOperationFailedException.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.DATABASE_OPERATION_FAILED, StateCode.DATABASE_OPERATION_FAILED_MESSAGE + ": " + e.getMessage(), null);
        } else if(e.getClass().getSimpleName().equals(UserIdInvalid.class.getSimpleName())) {
            map = CommonMethod.formatJsonMessage(StateCode.SUBMIT_FAILURE, StateCode.SUBMIT_FAILURE_MESSAGE + ": " + e.getMessage(), null);
        } else {
            map = CommonMethod.formatJsonMessage(StateCode.UNKNOWN_ERROR, StateCode.UNKNOWN_ERROR_MESSAGE + ": " + e.getClass().getSimpleName() + " " + e.getMessage(), null);
        }
        return map;
    }

    /**
     * 在网络请求处理成功后，返回的标准json格式
     */
    public static Map<String, Object> formatJsonMessage(int code, String message, Map<String, Object> details) {
        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        String code_message = "";
        if (code == StateCode.SUCCESS_PROCESS) {
            code_message = StateCode.SUCCESS_PROCESS_MESSAGE;
            if (message != null && !message.isEmpty()) {
                code_message = code_message + ": " + message;
            }
        } else if (code == StateCode.FAIL_PROCESSED) {
            code_message = StateCode.FAIL_PROCESSED_MESSAGE;
            if (message != null && !message.isEmpty()) {
                code_message = code_message + ": " + message;
            }
        } else {
            if (message != null && !message.isEmpty()) {
                code_message += message;
            }
        }

        map.put("message", code_message);
        map.put("detail", details);
        return map;
    }

    /**
     * 判断对应角色是否拥有对应权限
     */
    public static boolean verifyPermissionState(Integer allPermissionCode, int permission_code) {
        if ((allPermissionCode & transformStandardPermissionCode(permission_code)) != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否对用户的权限进行了修改
     */
    public static boolean verifyUserPermissionIsModified(Integer userAllPermissionCode, int permission_code) {
        if ((userAllPermissionCode & CommonMethod.transformStandardPermissionCode(permission_code)) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将权限码转换为对应的二进制码
     * 注意：位运算只能进行到30位，所以设置数量最多只能为30
     */
    public static Integer transformStandardPermissionCode(int permission_code) {
        return 1 << permission_code;
    }

    /**
     * 将角色码转换为对应的二进制码
     * 注意：位运算只能进行到30位，所以设置数量最多只能为30
     */
    public static Integer transformStandardRoleCode(int roleCode) {
        return 1 << roleCode;
    }

    /**
     * 判断用户的角色权限都有哪些
     */
    public static ArrayList<String> transformStandardRoleLevel(int roleLevel) {
        ArrayList<String> roleLevels = new ArrayList<>();
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_ADMIN_LEVEL)) > 0) {
            roleLevels.add("Admin");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_BOSS_LEVEL)) > 0) {
            roleLevels.add("Boss");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_BOSS_LEVEL)) > 0) {
            roleLevels.add("ARC");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_SPDM_LEVEL)) > 0) {
            roleLevels.add("SPDM");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_RTL_LEVEL)) > 0) {
            roleLevels.add("RTL");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_FGL_LEVEL)) > 0) {
            roleLevels.add("FGL");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_FO_LEVEL)) > 0) {
            roleLevels.add("FO");
        }
        if ((roleLevel & transformStandardRoleCode(PermissionRoleCode.ROLE_GUEST_LEVEL)) > 0) {
            roleLevels.add("Guest");
        }
        return roleLevels;
    }


    /**
     * 检测角色权限是否正确，不正确按照Guest权限进行操作
     */
    public static Integer checkIsBadRoleLevel(Integer roleLevel) {
        if (roleLevel == null) {
            return PermissionRoleCode.ROLE_GUEST_LEVEL;
        }

        if (roleLevel < 1 || roleLevel > 8) {
            return PermissionRoleCode.ROLE_GUEST_LEVEL;
        } else {
            return roleLevel;
        }

    }

    /**
     * 将十进制数字转化为二进制数字
     */
    public static String fromDecimalToBinaryString(Integer decimal_num) {
        String binary_num = Integer.toBinaryString(decimal_num);
        return binary_num;
    }

    /**
     * 将二进制数字转化为十进制数字
     */
    public static Integer fromBinaryToDecimalString(String binary_num) {
        BigInteger bigInteger = new BigInteger(binary_num, 2);
        return bigInteger.intValue();
    }

    /**
     * 将字节单位根据文件大小，自动转换为合适的大小单位
     */
    public static String bytesTransform(long file_bytes) {
        if (file_bytes < 1024) {
            return file_bytes + "bit";
        }

        double kb = file_bytes / 1024.00;

        if (kb < 1024) {
            return ((int) (kb * 100)) / 100.00 + "K";
        }

        double mb = kb / 1024.00;

        if (mb < 1024) {
            return ((int) (mb * 100)) / 100.00 + "M";
        }

        double gb = mb / 1024.00;
        return ((int) (gb * 100)) / 100.00 + "G";
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        if (fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    /**
     * 通过文件的后缀名，判断文件是否为图片文件
     */
    public static Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化上传文件
     */
    public static boolean uploadFiles(MultipartFile[] files, String relativePath, String image, String file, String max_file_size) {
        for (int i = 0; i < files.length; i++) {
            long size = files[i].getSize();
            if (size / 1024.00 / 1024.00 > Integer.valueOf(max_file_size.trim().replace("MB", ""))) {
                return false;
            }
        }

        //这里可以支持多文件上传
        if (files.length >= 1) {
            for (int p = 0; p < files.length; p++) {
                try {
                    String fileName = files[0].getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && CommonMethod.isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(ResourceUtils.getURL("classpath:").getPath() + relativePath + image + "/" + UUID.randomUUID().toString() + CommonMethod.getFileType(fileName));
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                    } else {
                        //创建输出文件对象
                        File outFile = new File(ResourceUtils.getURL("classpath:").getPath() + relativePath + file + "/" + fileName);
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 初始化上传文件夹
     */
    public static HashMap<String, File> initUploadFile(String relativePath, String image, String file) throws FileNotFoundException {
        HashMap<String, File> files = new HashMap<>();
        File relativePathFileDir = new File(ResourceUtils.getURL("classpath:").getPath() + relativePath);
        if (!relativePathFileDir.exists()) {
            relativePathFileDir.mkdirs();
        }
        files.put(relativePath, relativePathFileDir);

        File fileDir = new File(ResourceUtils.getURL("classpath:").getPath() + relativePath + file);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        files.put(file, fileDir);

        File imageDir = new File(ResourceUtils.getURL("classpath:").getPath() + relativePath + image);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        files.put(image, imageDir);
        return files;
    }

    /**
     * 获取小组角色名称
     */
    public static String transformGroupRole(Integer groupRole) {
        if (groupRole == null) {
            groupRole = GroupRoleCode.FO;
        }
        if (groupRole == GroupRoleCode.FO) {
            return ProjectRoleCode.FO_NAME;
        } else if (groupRole == GroupRoleCode.RTL) {
            return GroupRoleCode.RTL_NAME;
        } else if (groupRole == GroupRoleCode.BOSS) {
            return GroupRoleCode.BOSS_NAME;
        } else if (groupRole == GroupRoleCode.REAL_BOSS) {
            return GroupRoleCode.REAL_BOSS_NAME;
        } else {
            return ProjectRoleCode.FO_NAME;
        }
    }

    /**
     * 获取小组角色码
     */
    public static int transformGroupRoleCode(String groupRole) {
        if (groupRole == null || groupRole.isEmpty()) {
            groupRole = GroupRoleCode.FO_NAME;
        }
        if (groupRole.equals(GroupRoleCode.FO_NAME)) {
            return GroupRoleCode.FO;
        } else if (groupRole.equals(GroupRoleCode.RTL_NAME)) {
            return GroupRoleCode.RTL;
        } else if (groupRole.equals(GroupRoleCode.BOSS_NAME)) {
            return GroupRoleCode.BOSS;
        } else if (groupRole.equals(GroupRoleCode.REAL_BOSS_NAME)) {
            return GroupRoleCode.REAL_BOSS;
        } else {
            return GroupRoleCode.FO;
        }
    }

    /**
     * 获取项目组角色名称
     */
    public static String transformProjectRole(Integer projectRole) {
        if (projectRole == null) {
            projectRole = ProjectRoleCode.UNKNOWN;
        }
        if (projectRole == ProjectRoleCode.FO) {
            return ProjectRoleCode.FO_NAME;
        } else if (projectRole == ProjectRoleCode.FGL) {
            return ProjectRoleCode.FGL_NAME;
        } else if (projectRole == ProjectRoleCode.SPDM) {
            return ProjectRoleCode.SPDM_NAME;
        } else if (projectRole == ProjectRoleCode.ARC) {
            return ProjectRoleCode.ARC_NAME;
        } else if (projectRole == ProjectRoleCode.BOSS) {
            return ProjectRoleCode.BOSS_NAME;
        } else if (projectRole == ProjectRoleCode.REAL_BOSS) {
            return ProjectRoleCode.REAL_BOSS_NAME;
        } else {
            return ProjectRoleCode.UNKNOWN_NAME;
        }
    }

    /**
     * 获取项目组角色码
     */
    public static int transformProjectRoleCode(String projectRole) {
        if (projectRole == null || projectRole.isEmpty()) {
            projectRole = ProjectRoleCode.UNKNOWN_NAME;
        }
        if (projectRole.equals(ProjectRoleCode.FO_NAME)) {
            return ProjectRoleCode.FO;
        } else if (projectRole.equals(ProjectRoleCode.FGL_NAME)) {
            return ProjectRoleCode.FGL;
        } else if (projectRole.equals(ProjectRoleCode.SPDM_NAME)) {
            return ProjectRoleCode.SPDM;
        } else if (projectRole.equals(ProjectRoleCode.ARC_NAME)) {
            return ProjectRoleCode.ARC;
        } else if (projectRole.equals(ProjectRoleCode.BOSS_NAME)) {
            return ProjectRoleCode.BOSS;
        } else if (projectRole.equals(ProjectRoleCode.REAL_BOSS_NAME)) {
            return ProjectRoleCode.REAL_BOSS;
        } else {
            return ProjectRoleCode.UNKNOWN;
        }
    }

    /**
     * 判断字符串是否为null，为null返回""
     */
    public static String judgeStringIsNull(String message) {
        if (message == null || message.trim().isEmpty()) {
            message = "";
        } else {
            message = message.trim();
        }
        return message;
    }


    /**
     * 判断日期格式是否符合对应规范
     */
    public static boolean isValidDate(String time, String TIME_FORMAT) {
        boolean isValid = true;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
        }

        return isValid;
    }


    /**
     * 检测分数是否有效
     */
    public static Integer checkScoreValueValid(String scoreResult) {
        try {
            Integer score = Integer.valueOf(scoreResult);
            return checkScoreValueValid(score);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("scoreResult");
        }
    }

    /**
     * 检测分数是否有效
     */
    public static Integer checkScoreValueValid(Integer score) {
        if (score < 0 || score > 100) {
            throw new ParamInvalidException("scoreResult");
        }
        return score;
    }
}
