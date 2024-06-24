### Project done during TPO course at PJATK

# SyHub Task Manager

**SyHub** is a task management and meeting coordination system designed for corporate environments.  
It helps teams across various departments manage tasks and schedule meetings efficiently, ensuring timely completion of projects and effective communication.

### Functionalities
**User Registration and Authentication**
- Admins register users with randomly generated passwords
- Users can change their passwords after the first login
- Forgot password functionality with auto-generated passwords

**Role Management**
- Assign specific permissions to different roles
- Each role has access to certain functionalities

**Calendar Management**
- Basic calendar available for all users
- Registered users can create and manage meetings/events
- Send notifications to participants
 
**Task Management**
- Project Managers and Team Leaders can publish tasks
- Developers can take tasks and update their status
- Tasks can be tracked and approved

**Personal Account Management**
- Users can update personal information
- View tasks and event invitations  

**System Responsibilities**
- Managing user roles and permissions
- Providing a calendar for scheduling meetings and events
- Allowing users to create, view, and manage tasks
- Ensuring secure login and access control

**System Users**
- Developers (DEV)
- Managers (MANAGER)
- Human Resources personnel (HR)
- Team leaders (TEAM_LEAD)
- Project managers (PM)
- Marketing Specialists (MARK_SPEC)
- Admins (ADMIN)

## Example of Client-Side
Calendar  
![syhub-calendar](https://github.com/YuBerlinets/TaskManager/assets/123705268/838ab9a8-a351-4fa7-aa6e-da4267dad226)
Event details  
![syhub-event](https://github.com/YuBerlinets/TaskManager/assets/123705268/5a5286e2-6e21-48f1-a894-8146748537a0)
Tasks  
![syhub-tasks](https://github.com/YuBerlinets/TaskManager/assets/123705268/bef650e5-025e-4697-827e-d628ef49f553)
Admin Panel  
![syhub-adminPanel](https://github.com/YuBerlinets/TaskManager/assets/123705268/ff6e313d-b74e-4a30-b550-d8baeec642f3)

## Technologies used
- Java 17
- Spring Framework  
  Spring WEB  
  Spring Security  
  Spring JPA  
- Thymeleaf (Client-side)
- MySQL Database
- Lombok (Simplifying class writing)

