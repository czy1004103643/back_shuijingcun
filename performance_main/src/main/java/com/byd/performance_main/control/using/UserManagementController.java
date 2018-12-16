package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.*;
import com.byd.performance_utils.code.ProjectRoleCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.code.SysRoleCode;
import com.byd.performance_utils.exception.ExcelParseException;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.ltgfmt.FileDesc;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/userManagement")
public class UserManagementController extends BaseController {
    /*
    用户管理查询数据
     */
    @ResponseBody
    @GetMapping(value = "/query")
    public Map<String, Object> userManagementQuery(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/userManagement/query");
        Map<String, Object> details = new HashMap<>();
        ArrayList allUserInfo = new ArrayList();
        List<UserBean> userBean = userService.findAllUser();
        if (userBean != null && userBean.size() > 0) {
            for (int i = 0; i < userBean.size(); i++) {
                HashMap<String, Object> userInfo = new HashMap<>();
                //姓名及工号
                String userName = userBean.get(i).getUserName();
                String userId = userBean.get(i).getUserId();
                if (userId.equals("admin")) {
                    continue;
                }
                //所在小组名称及角色
                List<Integer> allGroupName = groupMemberService.findGroupNameFromGroupMember(userId);
                Integer groupRole = groupMemberService.findGroupNameRole(userId);
                if (groupRole != null) {
                    if (userService.findUserFromUserId(userId).getUserRole() != SysRoleCode.AdminCode && userService.findUserFromUserId(userId).getUserRole() != SysRoleCode.BossCode) {
                        if (groupRole == 1) {
                            userInfo.put("groupRole", "组长");
                        } else {
                            userInfo.put("groupRole", "组员");
                        }
                    } else {
                        userInfo.put("groupRole", null);
                    }
                } else {
                    userInfo.put("groupRole", null);
                }
                if (allGroupName != null && allGroupName.size() > 0) {
                    for (int j = 0; j < allGroupName.size(); j++) {
                        GroupNameBean groupNameBean = groupNameService.findGroupNameFromId(allGroupName.get(j));
                        if (groupNameBean != null) {
                            String groupName = groupNameBean.getGroupName();
                            userInfo.put("groupName", groupName);
                        } else {
                            userInfo.put("groupName", null);
                        }
                    }
                } else {
                    userInfo.put("groupName", null);
                }

                //用户所属的项目
                ArrayList projectInfo = new ArrayList();
                List<Integer> allProjectName = projectMemberService.findProjectNameFromProjectMember(userId);
                if (allProjectName.size() > 0) {
                    for (int k = 0; k < allProjectName.size(); k++) {
                        Map<String, Object> project = new HashMap<>();
                        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(allProjectName.get(k));
                        Integer projectRole = projectMemberService.findProjectNameRole(allProjectName.get(k), userId);
                        if (projectRole != null) {
                            if (projectRole.equals(ProjectRoleCode.FO) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.FO_NAME);
                                projectInfo.add(project);
                            } else if (projectRole.equals(ProjectRoleCode.FGL) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.FGL_NAME);
                                projectInfo.add(project);
                            } else if (projectRole.equals(ProjectRoleCode.SPDM) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.SPDM_NAME);
                                projectInfo.add(project);
                            } else if (projectRole.equals(ProjectRoleCode.ARC) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.ARC_NAME);
                                projectInfo.add(project);
                            } else if (projectRole.equals(ProjectRoleCode.BOSS) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.BOSS_NAME);
                                projectInfo.add(project);
                            } else if (projectRole.equals(ProjectRoleCode.REAL_BOSS) && projectNameBean != null) {
                                String projectName = projectNameBean.getProjectName();
                                project.put("projectName", projectName);
                                project.put("projectRole", ProjectRoleCode.REAL_BOSS_NAME);
                                projectInfo.add(project);
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }

                userInfo.put("projectInfo", projectInfo);
                userInfo.put("userName", userName);
                userInfo.put("userId", userId);
                allUserInfo.add(userInfo);
            }
        } else {
            throw new ParamInvalidException("userBean");
        }
        details.put("allUserInfo", allUserInfo);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    /*
    用户管理导入Excel
     */
    @ResponseBody
    @PostMapping(value = "/excelParse")

    public Map<String, Object> uploadAndParse(@RequestParam("file") MultipartFile file,
                                              @RequestParam("cookie") String cookie
    ) {
        urlLogInfo("/userManagement/excelParse");
        String userNo = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(userNo);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.UserManagementCode);
        boolean ImportResult = false;
        String fileName = file.getOriginalFilename();
        try {
            Map<String, Object> responseResult = excelParse(fileName, file, cookie);
            String returnCode = responseResult.get("code").toString();
            if (returnCode.equals("0")) {
                return userManagementQuery(cookie);
            } else {
                boolean result = false;
                details.put("result", result);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, responseResult.get("message").toString(), details);
                return map;
            }
        } catch (Exception e) {
            String message = e.getMessage();
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
            return map;
        }
    }

    public Map<String, Object> excelParse(String fileName, MultipartFile file, String cookie) throws Exception {

        String message = null;
        boolean parseResult = false;
        List<UserBean> userList = new ArrayList<UserBean>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new ExcelParseException("文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null && sheet.getLastRowNum() > 0) {
            parseResult = true;
        } else {
            throw new ExcelParseException("导入失败，没有一条记录");
        }
        UserBean userBean;
        if (sheet.getRow(0).getPhysicalNumberOfCells() <= 2) {
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                userBean = new UserBean();
                String userId = "";
                if ((row.getCell(0) == null) || (row.getCell(0).equals(""))) {
                    throw new ExcelParseException("导入失败(第" + r + "行，工号不能为空)");
                } else {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String userId1 = row.getCell(0).getStringCellValue();
                    for (int w = userId1.length(); --w >= 0; ) {
                        int chr = userId1.charAt(w);
                        if (chr >= 48 && chr <= 57) {
                            userId = row.getCell(0).getStringCellValue();
                        } else {
                            throw new ExcelParseException("导入失败(第" + r + "行，工号格式不正确)");
                        }
                    }
                }
                String userName = "";
                if ((row.getCell(1) == null) || (row.getCell(1).equals(""))) {
                    throw new ExcelParseException("导入失败(第" + r + "行，姓名不能为空)");
                } else {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String userName1 = row.getCell(1).getStringCellValue();
                    for (int t = userName1.length(); --t >= 0; ) {
                        int chr = userName1.charAt(t);
                        if ((chr >= 65 && chr <= 90) || (chr >= 97 && chr <= 122) || (chr >= 19968 && chr <= 40869)) {
                            userName = row.getCell(1).getStringCellValue();
                        } else {
                            throw new ExcelParseException("导入失败(第" + r + "行，姓名格式不正确)");
                        }
                    }
                    userBean.setUserName(userName);
                    userBean.setUserId(userId);
                    userBean.setUserRole(8);
                    userBean.setUserPassword(userId);
                    userBean.setCreaterId(checkCookieValid(cookie));
                    userList.add(userBean);
                }
            }
        } else {
            throw new ExcelParseException("导入失败(数据不能多于两列)");

        }
        Integer addResult = 0;
        String resultMessage = "";
        int p = 0;
        for (UserBean userRecord : userList) {
            String userId = userRecord.getUserId();
            UserBean userInfo = userService.findUserFromUserId(userId);
            if (userInfo == null) {
                addResult = userService.addUser(userRecord);
                p++;
            } else {
                resultMessage += "导入失败(第" + (p + 1) + "行，工号已存在)";
                p++;
            }

        }
        Map<String, Object> details = new HashMap<>();
        if (addResult == 1) {
            details.put("result", resultMessage);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, resultMessage, details);
            return map;
        } else {
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, resultMessage, details);
            return map;
        }
    }

    /*
    用户管理删除用户
     */
    @ResponseBody
    @PostMapping(value = "/deleteUser")

    public Map<String, Object> userManagementDelete(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/userManagement/deleteUser");
        logger.info(jsonParam.toJSONString());
        String cookie = jsonParam.getString("cookie");
        checkCookieValid(cookie);
        String userNo = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(userNo);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.UserManagementCode);
        JSONArray allUserId = jsonParam.getJSONArray("groupData");
        Integer deleteUserTable = null;
        if (allUserId.size() > 0) {
            for (int j = 0; j < allUserId.size(); j++) {
                JSONObject jsonObject = (JSONObject) allUserId.get(j);
                String deleteId = jsonObject.getString("userId");
                deleteUserTable = userService.deleteUser(deleteId);//用户表
                //所属小组
                List<Integer> allGroupName = groupMemberService.findGroupNameFromGroupMember(deleteId);
                if (allGroupName != null && allGroupName.size() > 0) {
                    for (int k = 0; k < allGroupName.size(); k++) {
                        Integer idGroupMember = groupMemberService.findIdFromGroupNameAndGroupMember(allGroupName.get(k), deleteId);
                        Integer deleteGroupMember = groupMemberService.delGroupMember(idGroupMember);
                    }
                }
                //所在项目
                List<Integer> allProjectName = projectMemberService.findProjectNameFromProjectMember(deleteId);
                if (allProjectName != null && allProjectName.size() > 0) {
                    for (int i = 0; i < allProjectName.size(); i++) {
                        ProjectMemberBean projectMemberBean = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(allProjectName.get(i), deleteId);
                        Integer idProjectMember = projectMemberBean.getId();
                        Integer deleteProjectMemberResult = projectMemberService.delProjectMember(idProjectMember);
                    }
                }
            }
        } else {
            message = "请至少选中一条数据！";
            details.put("deleteResult", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
            return map;
        }
        if (deleteUserTable == 1) {
            message = "删除成功";
            details.put("deleteResult", true);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
            return map;
        } else {
            message = "删除失败";
            details.put("deleteResult", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
            return map;

        }
    }
}
