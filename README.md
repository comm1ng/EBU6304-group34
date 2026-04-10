# EBU6304 - Group 34

International School Teaching Assistant Recruitment System (Lightweight Web Version)

## Overview
This project is now a lightweight Java Servlet/JSP web application for TA recruitment at BUPT International School.
It uses JSON file persistence only (no database) and supports three roles:

<<<<<<< Updated upstream
## Project Overview
This project develops a Teaching Assistant (TA) Recruitment System for BUPT International School. The system aims to simplify the recruitment process by allowing students to apply for positions, module organisers to manage applications, and administrators to monitor workload.
The current TA recruitment process for BUPT International School relies on manual forms and Excel files, which causes low efficiency, poor traceability, and lack of automated management.  
This project aims to build a streamlined TA recruitment system using Agile methods to replace manual work and improve the whole recruitment workflow.

---

## Core Functions (Planned)
### 👤 TA Functions
- Create applicant profile
- Upload CV
- Browse available jobs
- Apply for positions
- Check application status

### 🧑‍🏫 MO Functions
- Post new job positions
- View applicant list
- Select and manage applicants

### 🔧 Admin Functions
- Review TA resumes
- Monitor overall TA workload
- Manage user accounts
- View system logs
- Backup system data

### 🤖 AI-powered Features (Planned)
- Skill matching between jobs and applicants
- Identify missing skills for applicants
- Balance TA workload

---

## Prototype Pages (Iteration 1)
### TA Interface
- Homepage
- Job List Page
- My Applications Page
- Application Detail Page

### MO Interface
- MO Homepage
- Post Job Page
- Applicant List Page

### Admin Interface
- Admin Login Page
- Admin Homepage
- TA Resume Review Page
- Workload Management Page
- System Log Page
- Data Backup Page

---

## Iteration Plan (Agile Sprint)
| Iteration | Key Deliverables |
|----------|------------------|
| Iteration 1 | User registration, login, browse jobs |
| Iteration 2 | Job application, job posting |
| Iteration 3 | Applicant management, workload monitoring |
| Iteration 4 | AI skill matching & advanced features |

---

## Requirement Analysis
### Fact-Finding Techniques
- Group discussion & brainstorming
- Simulated user interviews
- Analysis of current manual recruitment process

### Prioritisation
- High Priority: Registration, login, job browse, job application
- Medium Priority: Application status check, workload monitoring
- Low Priority: AI-powered skill matching

### Estimation Method
- Small: Simple logic (login, registration)
- Medium: Moderate logic (job application)
- Large: Complex functions (skill matching)
=======
- TA (applicant)
- MO (module organiser)
- Admin
>>>>>>> Stashed changes

## Team Members (Updated)
- Github account:qmid(Lead/Member)
- qrsikno2: 190898878(Support TA)
- Utopianing:231221892(Member)
- comm1ng:231221467(Lead)
- Shanyu6:231220105(Member)
- ZXZ1219:231221146(Member)
- zpz999:231222958(Member)

## Tech Stack
- Java 17
- Maven
- Java Servlet + JSP + JSTL
- HTML/CSS (plus small JavaScript only where needed)
- Gson (JSON read/write)
- JUnit 5 (basic service-layer tests)

## Core Features
- Login without role dropdown (role auto-detected after authentication)
- Registration only for TA and MO
- Cross-role registration confirmation (TA <-> MO)
- MO registration requires work unit / organisation
- Optional post-login role selection when one account has both TA and MO
- TA profile management (including CV path and CV summary)
- TA job browsing and job application
- TA application status tracking
- MO job posting
- MO applicant review and status updates
- Admin workload summary and system statistics
- Persistent data in JSON files (`data/`)

## Default Accounts
- TA: `ta1` / `123456`
- MO: `mo1` / `123456`
- TA+MO: `dual1` / `123456`
- Admin: `admin` / `admin123`

## Project Structure
- `src/main/java/com/example/tarecruitment/model` - domain models and enums
- `src/main/java/com/example/tarecruitment/service` - business logic
- `src/main/java/com/example/tarecruitment/storage` - JSON repositories
- `src/main/java/com/example/tarecruitment/servlet` - servlet controllers + bootstrap listener
- `src/main/java/com/example/tarecruitment/util` - utility and seed initializer
- `src/main/webapp/WEB-INF/jsp` - JSP views
- `src/main/webapp/assets/css` - stylesheet
- `data/` - JSON persistence files
- `src/test/java/com/example/tarecruitment/service` - unit tests

## Run (Build + Test)
```bash
mvn "-Dmaven.repo.local=.m2" clean test
mvn "-Dmaven.repo.local=.m2" package
```

Generated WAR:
- `target/ta-recruitment-system-1.0.0-SNAPSHOT.war`

Deploy this WAR to Tomcat 9 (or compatible Servlet 4 container), then open:
- `http://localhost:8080/ta-recruitment-system-1.0.0-SNAPSHOT/`

## Notes
- If `data/*.json` is empty/missing, the app auto-seeds sample data on startup.
- Passwords are stored in plain text for prototype simplicity (coursework scope).
- Admin accounts are seed-only and cannot be self-registered.
---

## Team Members
- Github account:qmid(Lead/Member)
- qrsikno2: 190898878(Support TA)
- Utopianing:231221892(Member)
- comm1ng:231221467(Lead)
- Shanyu6:231220105(Member)
- ZXZ1219:231221146(Member)
- zpz999:231222958(Member)
<<<<<<< Updated upstream
- Asukaaa111:231220002(Member)
=======
- Asukaaa111:231220002(Member)
>>>>>>> Stashed changes
