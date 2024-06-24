INSERT INTO tpo_pro.role (role_id, role)
VALUES (1, 'ADMIN');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (2, 'HR');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (3, 'USER');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (4, 'DEV');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (5, 'PM');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (6, 'TEAM_LEAD');
INSERT INTO tpo_pro.role (role_id, role)
VALUES (7, 'MARK_SPEC');


INSERT INTO Task (task_id, task_type, title, description, status, created_by, working_on)
VALUES (1, 'DEBUG', 'Fix Null Pointer Exception', 'Resolve the null pointer exception in the login module', 'TODO',
        'admin', NULL),
       (2, 'UPDATE', 'Upgrade to Spring Boot 2.5', 'Update the application to use the latest version of Spring Boot',
        'ONGOING', 'admin', NULL),
       (3, 'IMPROVE', 'Optimize Database Queries', 'Improve the performance of database queries', 'DONE', 'admin',
        NULL),
       (4, 'DEBUG', 'Fix UI Rendering Issue', 'Address the UI rendering issue on the dashboard', 'TODO', 'admin', NULL),
       (5, 'UPDATE', 'Add New Features to Task Management', 'Enhance the task management module with new features',
        'ONGOING', 'admin', NULL),
       (6, 'IMPROVE', 'Improve Security Measures',
        'Enhance the security of the application by implementing best practices', 'DONE', 'admin', NULL),
       (7, 'DEBUG', 'Resolve Memory Leak', 'Identify and fix the memory leak in the application', 'TODO', 'admin',
        NULL),
       (8, 'UPDATE', 'Refactor Legacy Code', 'Clean up and refactor the legacy codebase for better maintainability',
        'DONE', 'admin', NULL),
       (9, 'IMPROVE', 'Enhance User Experience', 'Implement new UI/UX improvements based on user feedback', 'ONGOING',
        'admin', NULL),
       (10, 'DEBUG', 'Fix Calendar Sync Issue', 'Resolve the synchronization issues with the calendar', 'TODO', 'admin',
        NULL);



