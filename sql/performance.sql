-- 删除 mytest 数据库
drop database if exists mytest;

-- 创建 mytest 数据库
CREATE DATABASE mytest;

-- 使用 mytest 数据库
use mytest;

-- 删除已有的 tb_user 表格
DROP TABLE IF EXISTS `tb_user`;

-- 创建tb_user表格
create table tb_user
(
  id            int                   auto_increment primary key,
  userId        varchar(255) not null unique key,
  userName      varchar(255) not null,
  userPassword  varchar(50)  not null,
  employeeLevel varchar(255) null,
  userAvatar    varchar(255) not null default '/static/image/avatar.jpg',
  userCookie    varchar(255) null,
  userRole      int          null,
  createrId     varchar(255) null,
  createtime    timestamp    null
)
  charset = utf8;

-- 插入管理员用户
insert into tb_user (userId, userName, userPassword, employeeLevel, userRole, createrId, createtime)
values ('admin', '系统管理员', 'admin', 'A', 1, 'admin', now());

-- 删除已有的 tb_role 表格
DROP TABLE IF EXISTS `tb_role`;

-- 创建 tb_role 表格
create table tb_role
(
  id        int auto_increment primary key,
  roleLevel int         not null unique key,
  roleName  varchar(50) not null unique key
)
  charset = utf8;

-- 设置相应权限
insert into tb_role (roleLevel, roleName) values (1, 'Admin');
insert into tb_role (roleLevel, roleName) values (2, 'Boss');
insert into tb_role (roleLevel, roleName) values (3, 'ARC');
insert into tb_role (roleLevel, roleName) values (4, 'SPDM');
insert into tb_role (roleLevel, roleName) values (5, 'RTL');
insert into tb_role (roleLevel, roleName) values (6, 'FGL');
insert into tb_role (roleLevel, roleName) values (7, 'FO');
insert into tb_role (roleLevel, roleName) values (8, 'Guest');

-- 删除已有的 tb_permission_name 表格
DROP TABLE IF EXISTS `tb_permission_name`;

-- 创建 tb_permission_name 表格
create table tb_permission_name
(
  id             int auto_increment primary key,
  permissionCode int         not null unique key,
  permissionName varchar(50) not null unique key
)
  charset = utf8;

-- 设置权限号码对应的权限名称
insert into tb_permission_name (permissionCode, permissionName) values (0, '查看用户数据');
insert into tb_permission_name (permissionCode, permissionName) values (1, '修改用户数据');
insert into tb_permission_name (permissionCode, permissionName) values (2, '查看用户角色');
insert into tb_permission_name (permissionCode, permissionName) values (3, '修改用户角色');
insert into tb_permission_name (permissionCode, permissionName) values (4, '查看权限名称');
insert into tb_permission_name (permissionCode, permissionName) values (5, '修改权限名称');
insert into tb_permission_name (permissionCode, permissionName) values (6, '查看角色权限');
insert into tb_permission_name (permissionCode, permissionName) values (7, '修改角色权限');
insert into tb_permission_name (permissionCode, permissionName) values (8, '查看特定用户权限');
insert into tb_permission_name (permissionCode, permissionName) values (9, '修改特定用户权限');
insert into tb_permission_name (permissionCode, permissionName) values (10, '查看用户组权限');
insert into tb_permission_name (permissionCode, permissionName) values (11, '修改用户组权限');
insert into tb_permission_name (permissionCode, permissionName) values (12, '查看用户组成员权限');
insert into tb_permission_name (permissionCode, permissionName) values (13, '修改用户组成员权限');

-- 删除已有的 tb_role_permission 表格
DROP TABLE IF EXISTS `tb_role_permission`;

-- 创建 tb_role_permission 表格
create table tb_role_permission
(
  id                int          auto_increment primary key,
  roleLevel         int not null unique key,
  permissionAllCode int not null default 0
)
  charset = utf8;

-- 设置角色所拥有的权限
-- admin权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  1,
  1048575 #设置20个权限全部为1
);

-- Boss权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  2,
  0
);

-- ARC权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  3,
  0
);

-- SPDM权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  4,
  0
);

-- RTL权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  5,
  0
);

-- FGL权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  6,
  0
);

-- FO权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  7,
  0
);

-- Guest权限
insert into tb_role_permission (
  roleLevel,
  permissionAllCode
) values (
  8,
  0
);

-- 删除已有的 tb_user_permission 表格
DROP TABLE IF EXISTS `tb_user_permission`;

-- 创建 tb_user_permission 表格，该表格用来记录，是否更改某个特定用户，在其对应角色权限下的某个权限
create table tb_user_permission
(
  id                int                   auto_increment primary key,
  userId            varchar(255) not null unique key,
  permissionAllCode int          not null default 0
)
  charset = utf8;

-- 设置特定用户拥有的权限
insert into tb_user_permission (
  userId,
  permissionAllCode
) values (
  'admin',
  0 #在二进制中，0表示不修改权限，1表示修改权限
);

-- 删除已有的 tb_group 表格
DROP TABLE IF EXISTS `tb_group`;

-- 创建 tb_group 表格
create table tb_group
(
  id        int auto_increment primary key,
  groupName varchar(255) not null unique key
)
  charset = utf8;

-- -- 创建一个group
-- insert into tb_group (groupName) values ('管理员小组');

-- 删除已有的 tb_group_member 表格
DROP TABLE IF EXISTS `tb_group_member`;

-- 创建 tb_group_member 表格，isLeader代表职位，0是FO，1是FGL，2是Boss
create table tb_group_member
(
  id          int                   auto_increment primary key,
  groupName   int          not null,
  groupMember varchar(255) not null,
  isLeader    int          not null default 0
)
  charset = utf8;

-- -- 添加一个成员
-- insert into tb_group_member (groupName, groupMember, isLeader) values (1, 'admin', 1);

-- 删除已有的 tb_project 表格
DROP TABLE IF EXISTS `tb_project`;

-- 创建 tb_project 表格
create table tb_project
(
  id          int auto_increment primary key,
  projectName varchar(255) not null unique key
)
  charset = utf8;

-- -- 创建一个project
-- insert into tb_project (projectName) values ('管理员项目');

-- 删除已有的 tb_project_member 表格
DROP TABLE IF EXISTS `tb_project_member`;

-- 创建 tb_project_member 表格
create table tb_project_member
(
  id            int                   auto_increment primary key,
  projectName   int          not null,
  projectMember varchar(255) not null,
  roleName      int          not null default 0
)
  charset = utf8;

-- -- 添加一个成员
-- insert into tb_project_member (projectName, projectMember, roleName) values (1, 'admin', 6);

-- 删除已有的 tb_score_weight 表格
DROP TABLE IF EXISTS `tb_score_weight`;

-- 创建 tb_score_weight 表格
create table tb_score_weight
(
  id         int auto_increment primary key,
  weightName int not null unique key,
  scoreValue int not null
)
  charset = utf8;

-- 添加给FO打分绩效的权重
-- FO自己部分的分数，即个人项目完成度
insert into tb_score_weight (weightName, scoreValue) values (0, 25);
-- RTL给FO打分
insert into tb_score_weight (weightName, scoreValue) values (1, 35);
-- FGL给FO打分
insert into tb_score_weight (weightName, scoreValue) values (2, 40);

-- 添加给FGL打分绩效的权重
-- FGL自己部分的分数，即个人项目完成度
insert into tb_score_weight (weightName, scoreValue) values (3, 35);
-- RTL给FGL打分
insert into tb_score_weight (weightName, scoreValue) values (4, 25);
-- SPDM给FGL打分
insert into tb_score_weight (weightName, scoreValue) values (5, 40);


-- RTL打分权重
-- RTL组员自评平均分
insert into tb_score_weight (weightName, scoreValue) values (6, 55);
-- BOSS给RTL打分
insert into tb_score_weight (weightName, scoreValue) values (10, 45);

-- 添加给SPDM，ARC打分绩效的权重
-- SPDM或ARC自己部分的分数，即个人项目完成度
insert into tb_score_weight (weightName, scoreValue) values (7, 60);
-- BOSS级别人物给SPDM或ARC打分
insert into tb_score_weight (weightName, scoreValue) values (8, 40);



-- 组长给小组成员打分
-- 删除已有的 tb_group_scoring_member 表格
DROP TABLE IF EXISTS `tb_group_scoring_member`;

-- 创建 tb_group_scoring_member 表格
create table tb_group_scoring_member
(
  id               int                   auto_increment primary key,
  userId           varchar(255) not null,
  currentGroupName varchar(255) not null,
  currentRole      int,
  ratingUserId     varchar(255) not null,
  ratingUserRole   int,
  score            int          not null default 0,
  weight             int          not null default 0,
  scoreTime        varchar(255) not null,
  updateTime       timestamp    not null
  on update current_timestamp            default current_timestamp
)
  charset = utf8;



-- 项目组领导人给项目组成员打分
-- 删除已有的 tb_project_scoring_member 表格
DROP TABLE IF EXISTS `tb_project_scoring_member`;

-- 创建 tb_project_scoring_member 表格
create table tb_project_scoring_member
(
  id                 int                   auto_increment primary key,
  userId             varchar(255) not null,
  currentProjectName varchar(255) not null,
  currentRole        int,
  ratingUserId       varchar(255),
  ratingUserRole     int,
  weight             int          not null default 0,
  score              int          not null default 0,
  scoreTime          varchar(255) not null,
  updateTime         timestamp    not null
  on update current_timestamp              default current_timestamp
)
  charset = utf8;


-- 项目组成员自我项目完成度打分
-- 删除已有的 tb_project_own_score 表格
DROP TABLE IF EXISTS `tb_project_own_score`;

-- 创建 tb_project_own_score 表格
create table tb_project_own_score
(
  id                 int                   auto_increment primary key,
  userId             varchar(20) not null,
  currentProjectName varchar(50) ,
  userRole           int          ,
  workContent        varchar(800) ,
  weight             int          not null default 0,
  score              int          default 0,
  scoreTime          varchar(50) not null,
  updateTime         timestamp    not null
    on update current_timestamp              default current_timestamp
)
  charset = utf8;


-- 打分历史记录
-- 删除已有的 tb_score_history 表格
DROP TABLE IF EXISTS `tb_score_history`;

-- 创建 tb_score_history 表格
-- memberAllScore       int          not null default 0,
-- memberAllScoreWeight int          not null default 0,
create table tb_score_history
(
  id                   int    auto_increment primary key,
  userId               varchar(255) not null,
  groupScore           int          not null default 0,
  groupScoreWeight     int          not null default 0,
  projectScore         int          not null default 0,
  projectScoreWeight   int          not null default 0,
  ownScore             int          not null default 0,
  ownScoreWeight       int          not null default 0,
  totalScore           int          not null default 0,
  scoreTime            varchar(255) not null,
  updateTime           timestamp    not null
  on update current_timestamp default current_timestamp
)
  charset = utf8;


-- 绩效打分的时间记录并标记是否可以打分
-- 删除已有的 tb_scored_state_mode_time 表格
DROP TABLE IF EXISTS `tb_scored_state_mode_time`;

-- 创建 tb_scored_time 表格
create table tb_scored_state_mode_time
(
  id           int    auto_increment primary key,
  scoreTime    varchar(255) not null unique key,
  isScored     int not null default 0,
  updateTime   timestamp    not null
  on update current_timestamp default current_timestamp
)
  charset = utf8;


#自己填写直系领导考核表
DROP TABLE IF EXISTS `tb_yourself_write`;
CREATE TABLE tb_yourself_write
(
    id int PRIMARY KEY AUTO_INCREMENT,#自增id
    userId varchar(10) NOT NULL,#工号
    userName varchar(25) NOT NULL,#姓名
    assessMouth date NOT NULL ,#考核月份
    createTime datetime ,#提交时间
    projectName  varchar(100) NOT NULL,#项目名称
    workContent varchar(500) NOT NULL,#工作内容
    score double NOT NULL,#评分
    weights double NOT NULL ,#权重
    update_userId varchar(10),#更新工号
    update_userName varchar(25),#更新人
    update_date datetime #更新时间
)
  charset = utf8;

#权限角色表
drop table if exists `tb_sys_role`;

create table tb_sys_role(
roleId int not null auto_increment primary key,
roleName varchar(50) not null unique key
)
 charset = utf8;

#100200300400500600700800900901
insert into tb_sys_role(roleName) values("管理员权限");
#100200300400500600700800900901
insert into tb_sys_role(roleName) values("boss权限");
#100200
insert into tb_sys_role(roleName) values("架构师权限");
#100200400600
insert into tb_sys_role(roleName) values("项目管理权限");
#100200400600
insert into tb_sys_role(roleName) values("功能组长权限");
#100200300500
insert into tb_sys_role(roleName) values("直系领导权限");
#100200
insert into tb_sys_role(roleName) values("项目成员权限");
#100
insert into tb_sys_role(roleName) values("游客权限");

#用户角色表
drop table if exists `tb_sys_user_role`;
create table tb_sys_user_role(
id int not null auto_increment primary key,
roleId int not null,
moduleCode varchar(10) ,
backupsModuleCode varchar(10) not null,
isModified char not null)
charset = utf8;
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"","200",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"","300",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"","400",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"500","500",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"600","600",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"700","700",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"800","800",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"900","900",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(1,"","901",0);


insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"100","100",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"","200",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"300","300",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"","400",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"500","500",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"600","600",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"700","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"800","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"900","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(2,"901","901",0);

insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"200","200",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","300",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","400",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","500",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","600",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(3,"","901",0);

insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"200","200",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","300",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"400","400",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","500",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"600","600",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(4,"","901",0);


insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"200","200",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","300",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"400","400",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","500",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"600","600",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(5,"","901",0);

insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"200","200",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"300","300",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","400",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"500","500",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","600",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(6,"","901",0);

insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"100","100",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"200","200",1);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","300",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","400",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","500",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","600",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","700",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","800",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","900",0);
insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(7,"","901",0);

insert into tb_sys_user_role(roleId,moduleCode,backupsModuleCode,isModified)values
(8,"100","100",0);

#系统菜单表
drop table if exists `tb_sys_menu`;
create table tb_sys_menu(
id int not null auto_increment primary key,
menuCode varchar(20) not null unique key,
menuName varchar(20) not null unique key)
charset = utf8;
insert into tb_sys_menu(menuCode,menuName)values
("100","个人首页");
insert into tb_sys_menu(menuCode,menuName)values
("200","绩效填写");
insert into tb_sys_menu(menuCode,menuName)values
("300","小组考核");
insert into tb_sys_menu(menuCode,menuName)values
("400","项目考核");
insert into tb_sys_menu(menuCode,menuName)values
("500","小组管理");
insert into tb_sys_menu(menuCode,menuName)values
("600","项目管理");
insert into tb_sys_menu(menuCode,menuName)values
("700","用户管理");
insert into tb_sys_menu(menuCode,menuName)values
("800","权限管理");
insert into tb_sys_menu(menuCode,menuName)values
("900","考核控制");
insert into tb_sys_menu(menuCode,menuName)values
("901","项目Schedule");


#角色菜单表
drop table if exists `tb_sys_role_menu`;
create table tb_sys_role_menu(
id int not null auto_increment primary key,
roleId int not null unique key,
allMenuCode varchar(200) not null )
charset = utf8;
insert into tb_sys_role_menu(roleId,allMenuCode)values
(1,"100500600700800900");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(2,"100300500600700800900901");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(3,"100200");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(4,"100200400600");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(5,"100200400600");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(6,"100200300500");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(7,"100200");
insert into tb_sys_role_menu(roleId,allMenuCode)values
(8,"100");

#schedule表
drop table if exists `tb_schedule`;
create table tb_schedule(
id int not null auto_increment primary key,
case_id varchar(25) not null unique key,
userId varchar(25) not null,
project_name varchar(50) not null,
case_name varchar(800) not null,
case_start_time varchar(20) not null,
case_total_time int not null,
case_process double not null,
case_update_time timestamp )
charset = utf8;
