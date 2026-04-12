# International School TA Recruitment System  
## 使用与说明手册（Servlet/JSP 版）

版本：Web 轻量版  
适用项目：`EBU6304-group34`

---
# 以下文件地址根据自己的实际情况书写 
## 1. 文档目的

本文档面向课程组成员与后续维护同学，提供：

1. 项目部署与运行步骤（本地 Tomcat）
2. 系统功能使用说明（TA / MO / Admin）
3. 路由与数据文件说明
4. 常见问题排查
5. 日常维护建议

---

## 2. 系统概览

本系统是一个轻量 Java Web 应用，使用 Servlet + JSP 架构实现教学助理招聘流程。

核心约束：

1. 不使用数据库
2. 仅使用 JSON 文件持久化
3. 不使用 Spring Boot、ORM、前端重框架

技术栈：

1. Java 17（或更高版本）
2. Maven
3. Servlet 4.0 + JSP + JSTL
4. Gson
5. Tomcat 9

---

## 3. 当前角色与业务规则

### 3.1 角色

1. `TA`
2. `MO`
3. `ADMIN`

### 3.2 登录规则

1. 登录页不提供角色下拉框
2. 用户通过“用户名或邮箱 + 密码”认证
3. 系统自动识别账号角色
4. 若账号同时拥有 TA + MO，登录后进入角色选择页

### 3.3 注册规则

1. 仅允许注册 `TA` 或 `MO`
2. `ADMIN` 账号仅种子数据预置，不可自注册
3. 注册 `MO` 时 `workUnit` 必填
4. 同邮箱跨角色注册会触发确认提示
5. 同邮箱重复注册同角色会被拒绝

---

## 4. 目录结构（核心）

```text
src/main/java/com/example/tarecruitment/
├─ model        # 领域模型
├─ service      # 业务逻辑
├─ storage      # JSON 仓储
├─ servlet      # 控制器
└─ util         # 工具和启动容器

src/main/webapp/
├─ WEB-INF/jsp  # JSP 页面
└─ assets/css   # 样式资源

data/           # JSON 数据文件
```

---

## 5. 从开始到运行（完整流程）

以下命令在 PowerShell 中执行，路径按你本机修改。

### 5.1 构建与打包

```powershell
Set-Location "E:\大三下\EBU6304-group34"
mvn "-Dmaven.repo.local=.m2" clean test
mvn "-Dmaven.repo.local=.m2" package -DskipTests
```

产物：

`target\ta-recruitment-system-1.0.0-SNAPSHOT.war`

### 5.2 部署到 Tomcat

```powershell
$project = "E:\大三下\EBU6304-group34"
$tomcat  = "C:\Users\赵培志\apache-tomcat-9.0.112-windows-x64\apache-tomcat-9.0.112"
$appName = "ta-recruitment-system-1.0.0-SNAPSHOT"

Copy-Item -LiteralPath "$project\target\$appName.war" -Destination "$tomcat\webapps" -Force
```

### 5.3 启动 Tomcat

如果提示 `CATALINA_HOME environment variable is not defined correctly`，使用下面方式启动：

```powershell
$tomcat = "C:\Users\赵培志\apache-tomcat-9.0.112-windows-x64\apache-tomcat-9.0.112"
$env:CATALINA_HOME = $tomcat
$env:CATALINA_BASE = $tomcat
& "$tomcat\bin\startup.bat"
```

### 5.4 访问系统

1. 若 Tomcat 是默认端口，访问：  
`http://localhost:8080/ta-recruitment-system-1.0.0-SNAPSHOT/`
2. 若日志显示 `http-nio-8081`，访问：  
`http://localhost:8081/ta-recruitment-system-1.0.0-SNAPSHOT/`

### 5.5 停止 Tomcat

```powershell
& "$tomcat\bin\shutdown.bat"
```

---

## 6. 默认演示账号

1. TA：`ta1 / 123456`
2. MO：`mo1 / 123456`
3. TA+MO 双角色：`dual1 / 123456`
4. Admin：`admin / admin123`

来源：`DataInitializer` 与 `data/users.json`

---

## 7. 功能使用说明

### 7.1 TA 使用流程

1. 登录进入 TA Dashboard
2. 在 `Jobs` 页面查看开放岗位
3. 对岗位提交申请（同岗位不可重复申请）
4. 在 `My Applications` 查看状态
5. 在 `Profile` 更新个人信息、技能、CV 路径与摘要

### 7.2 MO 使用流程

1. 登录进入 MO Dashboard
2. 在 `Post Job` 发布岗位
3. 在 `Manage Applicants` 查看某岗位申请人
4. 对申请执行 `ACCEPTED / REJECTED / PENDING`
5. 在 `Profile` 维护工作单位、职称、简介

### 7.3 Admin 使用流程

1. 登录进入 Admin Dashboard
2. 查看系统统计（用户数、岗位数、申请数）
3. 查看 TA 工作量汇总（接受岗位数、总工时、待处理申请）

---

## 8. 演示建议顺序（课堂/答辩）

1. 展示注册页：注册 TA
2. 展示注册页：同邮箱注册 MO，触发跨角色确认
3. 用 TA 登录，浏览岗位并申请
4. 用 MO 登录，发布岗位并审批申请
5. 用 Admin 登录，展示工作量统计
6. 用双角色账号 `dual1` 登录，演示角色选择页

---

## 9. 路由总览（当前代码）

| URL | 说明 | 角色 |
|---|---|---|
| `/` | 首页重定向 | 登录后用户 |
| `/login` | 登录 | 公开 |
| `/register` | 注册 | 公开 |
| `/logout` | 退出登录 | 已登录 |
| `/role-select` | 选择会话角色 | 双角色账号 |
| `/dashboard` | 统一分发到角色仪表盘 | 已登录 |
| `/ta/dashboard` | TA 仪表盘 | TA |
| `/jobs` | 岗位列表 | TA |
| `/jobs/apply` | 投递岗位 | TA |
| `/my-applications` | 我的申请 | TA |
| `/mo/dashboard` | MO 仪表盘 | MO |
| `/mo/post-job` | 发布岗位 | MO |
| `/mo/manage-applicants` | 管理申请人 | MO |
| `/mo/update-application-status` | 更新申请状态 | MO |
| `/admin/dashboard` | 管理员看板 | ADMIN |
| `/profile` | 个人资料（TA/MO） | TA 或 MO |

---

## 10. 数据文件与持久化说明

### 10.1 数据文件

`data/` 目录下：

1. `users.json`
2. `profiles.json`（TA）
3. `mo_profiles.json`（MO）
4. `jobs.json`
5. `applications.json`

### 10.2 数据目录解析优先级

系统启动时按以下顺序确定数据目录：

1. JVM 参数：`-Dtarecruitment.dataDir=...`
2. `web.xml` 的 `context-param dataDir`
3. 默认相对路径：`data`

### 10.3 初始化规则

`DataInitializer.initializeIfEmpty()` 的行为：

1. 仅当某类文件为空时写入种子数据
2. 不会覆盖已有数据
3. 可用于首次部署快速演示

---

## 11. 维护与开发说明

### 11.1 新增字段时的改动清单

例如为 `User` 增加字段：

1. 修改 `model` 类
2. 修改对应 `JSP` 表单与 Servlet 参数读取
3. 修改 `service` 校验与更新逻辑
4. 修改种子数据与已有 JSON 样例
5. 补充测试

### 11.2 权限控制位置

统一在 `BaseServlet.requireRole(...)` 中校验。  
新增页面时建议继承 `BaseServlet` 并复用该方法。

### 11.3 业务规则主要位置

1. 注册规则：`RegistrationService`
2. 登录规则：`AuthService`
3. 投递规则：`ApplicationService`
4. 工作量统计：`AdminService`

---

## 12. 测试与质量检查

当前已有测试：

1. `ApplicationServiceTest`
2. `AdminServiceTest`

建议每次提交前执行：

```powershell
mvn "-Dmaven.repo.local=.m2" clean test
```

---

## 13. 常见问题排查

### 13.1 `CATALINA_HOME environment variable is not defined correctly`

原因：Tomcat 环境变量未设置或路径错误。  
处理：按“5.3 启动 Tomcat”中的命令临时设置并启动。

### 13.2 浏览器 404

排查顺序：

1. WAR 是否已复制到 `webapps`
2. `webapps` 下是否自动解压出同名目录
3. URL 是否带上下文路径 `ta-recruitment-system-1.0.0-SNAPSHOT`
4. 端口是否是 8080 或 8081

### 13.3 端口不一致

日志中若出现 `http-nio-8081`，说明服务监听端口是 8081。  
请按日志端口访问，不要固定使用 8080。

### 13.4 日志中文乱码

常见于控制台编码差异，不影响服务运行。  
优先以“是否启动成功”和“是否可访问”作为判断标准。

### 13.5 数据未更新

常见原因是读取/写入了错误的数据目录。  
请检查 `tarecruitment.dataDir`、`web.xml` 的 `dataDir`、以及当前工作目录。

---

## 14. 课程组协作建议

1. 按模块分支开发（auth / ta / mo / admin / ui / storage）
2. 小步提交，提交信息写清“改了什么 + 为什么”
3. 合并前跑测试并手测关键流程
4. 避免多人同时修改同一 JSON 文件造成冲突

---

如后续要升级 Version 2，建议优先做：

1. 密码加密存储
2. 简历文件上传
3. 更细粒度权限控制
4. 完整集成测试与接口级测试

