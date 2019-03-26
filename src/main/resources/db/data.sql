insert into TIMESHEET_DB.status (status_id, status_name)
values (1, 'SAVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (2, 'SUBMITTED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (3, 'APPROVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (4, 'REJECTED');


insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (1,'7067a443-d7a1-4dde-b38f-76ce5494978c','179ba7f8-574b-4c69-8039-d3f75065d643', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-08','','2019-W10');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (2,'7067a443-d7a1-4dde-b38f-76ce5494978c','d3d1f7b4-006c-4701-9282-13bb6a64e280', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-11','','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (3,'7067a443-d7a1-4dde-b38f-76ce5494978c','9fe6c388-c518-45dc-a34c-df78142de5fa', 'af197078-ef3e-46e6-893f-e016e05c895f', 1,4.5,'2019-03-12','','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (4,'8b03fb47-3467-43a1-a4d0-b029002b0de9','bb0d49d4-2cd1-4fe0-a052-4acdb0541249', '7041cb03-200d-457c-84a9-a4881527448f', 3,4.5,'2019-03-08','','2019-W10');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (5,'8b03fb47-3467-43a1-a4d0-b029002b0de9','598aa49c-958d-44c0-87bd-19729259f02d', '7041cb03-200d-457c-84a9-a4881527448f', 3,4.5,'2019-03-11','','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (6,'8b03fb47-3467-43a1-a4d0-b029002b0de9','172d6fcf-2b10-452a-a59f-c62b76b726eb', '7041cb03-200d-457c-84a9-a4881527448f', 2,4.5,'2019-03-12','','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (7,'7067a443-d7a1-4dde-b38f-76ce5494978c','1e5e34a3-cf14-4b9f-90b0-25888033ebd8', 'a724077c-3526-4948-8a4f-66826bcfa968', 4,4.5,'2019-03-08','Something is wrong with that entry. Check it again.','2019-W10');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (8,'7067a443-d7a1-4dde-b38f-76ce5494978c','348045a6-9d1b-44e1-b104-6fd4e3686128', 'a724077c-3526-4948-8a4f-66826bcfa968', 2,4.5,'2019-03-11','','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (9,'7067a443-d7a1-4dde-b38f-76ce5494978c','40217d59-870d-43d1-a0c9-7fe7daf20ed3', 'a724077c-3526-4948-8a4f-66826bcfa968', 4,8,'2019-03-12','Time is incorrect','2019-W11');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (10,'8b03fb47-3467-43a1-a4d0-b029002b0de9','69454576-9931-40db-bd5f-63ef427ed837', 'af197078-ef3e-46e6-893f-e016e05c895f', 2,4.5,'2019-04-08','','2019-W15');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (11,'8b03fb47-3467-43a1-a4d0-b029002b0de9','718a28d5-73cd-4ea1-9eab-7f651d6b4d43', 'af197078-ef3e-46e6-893f-e016e05c895f', 2,4.5,'2019-04-09','','2019-W15');
insert into TIMESHEET_DB.time_entry (time_entry_id, project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week)
values (12,'8b03fb47-3467-43a1-a4d0-b029002b0de9','d652df0a-43f5-4109-af28-65920b18658f', 'af197078-ef3e-46e6-893f-e016e05c895f', 4,4.5,'2019-04-16','Check this entry again','2019-W16');