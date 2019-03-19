insert into TIMESHEET_DB.status (status_id, status_name)
values (1, 'NEW');
insert into TIMESHEET_DB.status (status_id, status_name)
values (2, 'SAVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (3, 'SUBMITTED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (4, 'APPROVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (5, 'REJECTED');


insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (1,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-10','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (2,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-11','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (3,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-12','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (4,'8b03fb47-3467-43a1-a4d0-b029002b0de9', '7041cb03-200d-457c-84a9-a4881527448f', 1,4.5,'2019-03-10','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (5,'8b03fb47-3467-43a1-a4d0-b029002b0de9', '7041cb03-200d-457c-84a9-a4881527448f', 1,4.5,'2019-03-11','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (6,'8b03fb47-3467-43a1-a4d0-b029002b0de9', '7041cb03-200d-457c-84a9-a4881527448f', 1,4.5,'2019-03-12','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (7,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'a724077c-3526-4948-8a4f-66826bcfa968', 1,4.5,'2019-03-10','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (8,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'a724077c-3526-4948-8a4f-66826bcfa968', 1,4.5,'2019-03-11','');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid, user_guid, status_id, hours_number, date, comment)
values (9,'7067a443-d7a1-4dde-b38f-76ce5494978c', 'a724077c-3526-4948-8a4f-66826bcfa968', 5,8,'2019-03-12','Time is incorrect');