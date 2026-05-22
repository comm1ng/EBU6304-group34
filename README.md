# International School Teaching Assistant Recruitment Platform

A lightweight Java Servlet/JSP web application for BUPT International School to manage TA recruitment.

## 1) Product Positioning
This project is designed as a practical internal recruitment platform (not a Swing demo), with role-based workflows for:
- TA (Teaching Assistant)
- MO (Module Organiser)
- Admin

## 2) Coursework Compliance
This implementation strictly follows coursework constraints:
- Java Servlet + JSP + JSTL
- HTML/CSS + small JavaScript
- JSON file persistence only (`data/*.json`)
- No Spring Boot
- No database / ORM
- No React/Vue/Angular

- ## Iteration Plan (Agile Sprint)
| Iteration | Key Deliverables |
|----------|------------------|
| Iteration 1 | User registration, login, browse jobs |
| Iteration 2 | Job application, job posting |
| Iteration 3 | Applicant management, workload monitoring |
| Iteration 4 | AI skill matching & advanced features |


## 3) Core Role Logic
- Login: no role dropdown before authentication.
- Role is auto-detected from account data.
- If an account has both TA and MO roles, a post-login role selection page is shown.
- Registration supports TA and MO only.
- Admin is seed-only (cannot self-register).
- Cross-role registration flow is implemented:
  - Existing MO registering as TA -> confirmation prompt.
  - Existing TA registering as MO -> confirmation prompt.
  - Duplicate same-role registration is blocked.
- MO registration requires `workUnit`.

## 4) Key Functional Modules
### TA
- Maintain profile and CV summary/path
- Browse open jobs with search/filter
- Apply to jobs
- Track application status

### MO
- Maintain MO profile
- Post jobs with title/description/skills/hours/deadline/location mode
- Manage job status (open/closed)
- Review applicants and update status (pending/accepted/rejected)

### Admin
- View dashboard metrics
- View system overview
- View TA workload summary
- View read-only user directory

## 5) Project Structure
```text
src/main/java/com/example/tarecruitment
  model/
  service/
  storage/
  servlet/
  util/

src/main/webapp
  assets/
    css/
      base.css
      layout.css
      components.css
      pages.css
    js/
      app.js
    images/
      bupt-is-logo.png
  WEB-INF/
    jsp/
      common/
        header.jspf
        footer.jspf
      login.jsp
      register.jsp
      roleSelect.jsp
      taDashboard.jsp
      moDashboard.jsp
      adminDashboard.jsp
      jobs.jsp
      myApplications.jsp
      postJob.jsp
      manageJobs.jsp
      manageApplicants.jsp
      profile.jsp
      workloadSummary.jsp
      systemOverview.jsp
      userManagementView.jsp
    web.xml

data/
  users.json
  profiles.json
  mo_profiles.json
  jobs.json
  applications.json
```

## 6) Demo Accounts
- TA: `ta1` / `123456`
- MO: `mo1` / `123456`
- TA+MO: `dual1` / `123456`
- Admin: `admin` / `admin123`

## 7) Build and Run
### Build
```bash
mvn "-Dmaven.repo.local=.m2" clean test
mvn "-Dmaven.repo.local=.m2" package
```

### Deploy
Deploy `target/ta-recruitment-system-1.0.0-SNAPSHOT.war` to Tomcat 9（The version cannot be higher than Tomcat 9）.

Then open:
```text
http://localhost:8080/ta-recruitment-system-1.0.0-SNAPSHOT/
```

## 8) Notes
- Data is persisted in local JSON files under `data/`.
- If files are missing/empty, seed data is initialized automatically.
- Passwords are plaintext for coursework simplicity.

## 9) Suggested Team Division (6 People)
1. Auth and registration (login, role-select, cross-role flow)
2. TA module (jobs, apply, applications, TA profile)
3. MO module (post/manage jobs, applicant review)
4. Admin module (dashboard, overview, workload, users)
5. UI/UX and design system (JSP layout, CSS architecture, branding)
6. JSON storage/integration/testing (repositories, seed data, test fixes)

## 10) Suggested Branch Plan
- `feature/auth-registration`
- `feature/ta-workflow`
- `feature/mo-workflow`
- `feature/admin-analytics`
- `feature/ui-design-system`
- `feature/storage-tests`
- `release/integration`

Use pull requests into `main` with at least one reviewer per branch.

## Team Members (Updated)
- Github account:qmid(Lead/Member)
- qrsikno2: 190898878(Support TA)
- Utopianing:231221892(Member)
- comm1ng:231221467(Lead)
- Shanyu6:231220105(Member)
- ZXZ1219:231221146(Member)
- zpz999:231222958(Member)

