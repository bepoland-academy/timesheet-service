insert into TIMESHEET_DB.status (status_id, status_name)
values (1, 'SAVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (2, 'SUBMITTED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (3, 'APPROVED');
insert into TIMESHEET_DB.status (status_id, status_name)
values (4, 'REJECTED');


insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('88a9a983-8337-4462-9383-faf747c24b46','1d2dbc5b-2795-467e-9c82-7c4f2d94e86f','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-25','','2019-W09'),
 ('88a9a983-8337-4462-9383-faf747c24b46','820f86d9-8464-447f-a632-a5fe254859c2','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-26','','2019-W09'),
 ('88a9a983-8337-4462-9383-faf747c24b46','c3717bc0-f8e1-41d3-b634-f52b7aed45ee','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-27','','2019-W09'),
 ('88a9a983-8337-4462-9383-faf747c24b46','c4f9a0a4-ed59-4af7-bd25-3ee06708a783','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-28','','2019-W09'),
 ('88a9a983-8337-4462-9383-faf747c24b46','5684c1f9-ea89-4281-84fd-984347c7d29d','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-01','','2019-W09'),
 ('88a9a983-8337-4462-9383-faf747c24b46','01df8c11-662d-4ae9-ab51-f868cade1154','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-04','','2019-W10'),
 ('88a9a983-8337-4462-9383-faf747c24b46','3d6f33a9-d2fe-4939-a503-4f1e2c54adbb','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-05','','2019-W10'),
 ('88a9a983-8337-4462-9383-faf747c24b46','d1bac640-5d6e-4012-9a01-845979bfffad','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-06','','2019-W10'),
 ('88a9a983-8337-4462-9383-faf747c24b46','360e0303-7146-41db-913c-83df19db55d7','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-07','','2019-W10'),
 ('88a9a983-8337-4462-9383-faf747c24b46','19307dec-526a-4b89-86f0-d69318bd909e','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-08','','2019-W10'),
 ('88a9a983-8337-4462-9383-faf747c24b46','c0a89f7b-5395-4b75-9b80-a5cfa07e5f50','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-11','','2019-W11'),
 ('88a9a983-8337-4462-9383-faf747c24b46','b1a9cdf5-65ba-424c-831f-897759acd141','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-12','','2019-W11'),
 ('88a9a983-8337-4462-9383-faf747c24b46','d5c0ec56-b169-443e-a1c4-feeec4c0103c','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-13','','2019-W11'),
 ('88a9a983-8337-4462-9383-faf747c24b46','92770515-1352-430c-bd1c-d50c9d222185','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-14','','2019-W11'),
 ('88a9a983-8337-4462-9383-faf747c24b46','52414c28-9c72-43ce-b9b3-ee2d4a31a6b7','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-15','','2019-W11'),
 ('88a9a983-8337-4462-9383-faf747c24b46','d795ac7d-800d-4a82-badf-b9bbfd07ebf0','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-18','','2019-W12'),
 ('88a9a983-8337-4462-9383-faf747c24b46','6c63d539-c21a-4686-a71f-b49989a346fc','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-19','','2019-W12'),
 ('88a9a983-8337-4462-9383-faf747c24b46','d52d3360-2527-45bb-b680-b2de62aa3226','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-20','','2019-W12'),
 ('88a9a983-8337-4462-9383-faf747c24b46','6275e229-6c3d-4b28-a476-8d6510fef933','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-21','','2019-W12'),
 ('88a9a983-8337-4462-9383-faf747c24b46','e1ca779b-40c1-4599-bcc7-db2e35d5dbad','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-22','','2019-W12'),
 ('88a9a983-8337-4462-9383-faf747c24b46','7a77a42b-e6f3-4cb9-946a-a11229db7cfa','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-25','','2019-W13'),
 ('88a9a983-8337-4462-9383-faf747c24b46','7199f4b5-f3d5-4f05-a8a5-dcf738e834ce','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-26','','2019-W13'),
 ('88a9a983-8337-4462-9383-faf747c24b46','35db7bd3-c292-4617-8025-580076123eaa','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-27','','2019-W13'),
 ('88a9a983-8337-4462-9383-faf747c24b46','38a63f53-fc63-41d6-8c6d-a9fe15b17174','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-28','','2019-W13'),
 ('88a9a983-8337-4462-9383-faf747c24b46','e3b754eb-112d-44f6-ae89-08b900a9ad4f','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-29','','2019-W13');

insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','989e3009-3be4-4ea9-940a-1480cd2596a9','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-25','','2019-W09'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','1d461879-2c21-4ba1-953c-6f30fd29b36d','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-26','','2019-W09'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','c0752d2f-62cf-4d4b-b205-68b1d00bc3d9','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-27','','2019-W09'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','6806a4d4-b551-4de8-be75-c840c0aa1ef0','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-02-28','','2019-W09'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','daba4145-393e-4978-9163-3cf3f59b3952','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-01','','2019-W09'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','fe5cf3f1-fda7-40fb-9831-5e103b973408','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-04','','2019-W10'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','57ff6ba0-63c7-42ef-b806-115c8ad9117d','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-05','','2019-W10'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','d59550b7-eb0a-411c-8cad-961bf744b1a7','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-06','','2019-W10'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','cdb286bd-5363-4cf1-a0db-e1aad2d81ee8','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-07','','2019-W10'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','6f0cbd49-6ef5-4dd1-9816-d761eadcb898','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-08','','2019-W10'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','d9f07df7-764f-4cd2-8ece-4ddf1d0a7e1d','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-11','','2019-W11'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','439d00f0-6d5e-4913-ab49-34fbf37ee508','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-12','','2019-W11'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','076ebe31-5bd8-4f33-95eb-13a5bca24e53','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-13','','2019-W11'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','9390a15c-6eab-43a9-9c8e-20f885337cc3','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-14','','2019-W11'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','9e0cb484-bf41-4e80-9a46-81283b2b6db2','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-15','','2019-W11'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','3efae909-40b7-4ee2-8efb-408da48911f0','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-18','','2019-W12'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','a4e26a21-a724-4f68-b221-a752869e7331','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-19','','2019-W12'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','add7176e-a463-45ee-a5f5-85b20b6f339f','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-20','','2019-W12'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','8983db59-600a-41a3-a4c7-8b056e66ce02','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-21','','2019-W12'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','964d2324-75a5-47ad-b70b-f82edc713436','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-22','','2019-W12'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','f30caceb-93d4-4737-812a-aab7bd28e8a6','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-25','','2019-W13'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','1fc330c3-ad87-4d01-a076-5ff8ef1d08f4','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-26','','2019-W13'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','cc02594d-99aa-48a7-9117-209fd9c26979','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-27','','2019-W13'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','2c719a2c-081a-46f4-ad87-825782c35946','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-28','','2019-W13'),
 ('7f7f01aa-f49b-465a-80d4-05890de1a74c','b49dd8dc-0cd0-428c-a861-71385166fdcd','1e3185a2-ba44-4709-b8de-a1b0a7ad15f5',1,0.0,'2019-03-29','','2019-W13');

insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','331fe979-8cf7-4658-ad7b-ec8f30430a7a','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-25','','2019-W09'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','6b7d8316-d759-4ce8-b503-ef60c768de08','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-26','','2019-W09'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','f0c3db0f-93c5-4848-a9b5-3d61c510f142','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-27','','2019-W09'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','12092841-f1cb-4837-95e4-ab3dc9eb2fca','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-28','','2019-W09'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','02fd3494-919a-48dc-b96a-7a4ac8f4d8f2','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-01','','2019-W09'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','2d4107e3-4b6e-4a76-b63b-45b2781657ba','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-04','','2019-W10'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','1ac11f53-03a6-41d5-a94d-eebf13c1528e','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-05','','2019-W10'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','a70b8654-673f-4e04-a9d2-858f2e5168a4','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-06','','2019-W10'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','7effa044-dfc7-4062-a736-6d1f3ce05db0','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-07','','2019-W10'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','7c7e6f10-ebe0-42d5-aaa8-8f78cdb51345','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-08','','2019-W10'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','4cc84f48-523f-4c1e-bb6e-1c44e813cf6f','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-11','','2019-W11'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','6ed953d1-6a69-459d-a248-161c0ce21a83','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-12','','2019-W11'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','804cfd3f-50cd-4497-bba6-5624153a2113','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-13','','2019-W11'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','b11592bf-e227-4c73-9e12-427a35b6052c','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-14','','2019-W11'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','b771f96c-d591-4246-8ac0-310488f16947','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-15','','2019-W11'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','98463f06-491a-4f44-a1c4-f72e8c759912','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-18','','2019-W12'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','77706f83-dc22-4d9c-b37b-b6d3d3cf0ddc','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-19','','2019-W12'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','260ff4cd-70b4-4ed8-85e1-03b6c361a890','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-20','','2019-W12'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','c267bd54-9c0d-4d6a-b012-0041b3a226ff','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-21','','2019-W12'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','e154b000-e90a-4f51-a746-ff6cc62601d0','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-22','','2019-W12'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','d78708b5-6378-4bbc-b409-d133ec7bab72','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-25','','2019-W13'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','b4cb06c9-ace6-4225-8370-aea43c49894a','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-26','','2019-W13'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','12f9b090-b03b-4f07-98db-f0d642fac094','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-27','','2019-W13'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','0d573b66-44c2-4d33-a40d-47f2568dc03e','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-28','','2019-W13'),
 ('954928d4-b2d5-4e30-ae39-d2516c57ce2e','8ee27246-3ea6-4239-8d44-8ee9244932cf','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-29','','2019-W13');

insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('f69b330a-f184-44ea-8849-8006af8f1089','df3c7c9b-27b2-401a-8c84-6ba578a4646b','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-25','','2019-W09'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','2f4e7451-c2bb-4e19-8ab3-e230ea80dd19','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-26','','2019-W09'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','9b5ef3f9-5f6c-43e4-a05b-7e612debebe6','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-27','','2019-W09'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','6ce3ae7f-a8fd-4922-8c84-1911b4377663','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-28','','2019-W09'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','1fa3a0e5-b3d0-4913-8581-e1cad89f349c','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-01','','2019-W09'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','ece87b72-5b9d-4b80-88de-bc2df74a13dd','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-04','','2019-W10'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','37a7f754-596a-4a13-b746-fd0c35325674','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-05','','2019-W10'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','efa6c600-05a2-4936-91d2-6c0dda986d2d','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-06','','2019-W10'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','ef220bc1-4962-4db2-b64e-f48f0a51f049','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-07','','2019-W10'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','9e7f8414-7a09-4e3f-a948-e33b66496972','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-08','','2019-W10'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','1296c49f-cb5c-405e-9d84-8f72345edcae','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-11','','2019-W11'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','d78b5dca-3da5-4878-9ea3-88022f6ba82e','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-12','','2019-W11'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','291c96ef-1cd1-4ac6-84c1-14aa4c9544e1','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-13','','2019-W11'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','de9da12a-6f8d-457e-be77-e33964fce3e1','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-14','','2019-W11'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','6aadc732-05d4-4a31-813e-151cccb5ba45','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-15','','2019-W11'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','b156d2f0-fb0e-4a86-9c0b-244c11236283','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-18','','2019-W12'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','c47cb04b-1785-4e1c-8c35-ed0f56daed08','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-19','','2019-W12'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','9e475fdb-6a60-4cd8-8dea-a68d5fdaf5a5','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-20','','2019-W12'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','47cc61e4-1619-4b03-8bc2-9d066110b812','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-21','','2019-W12'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','fff947a0-3b2c-4b61-a5bf-35d1a1883a39','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-22','','2019-W12'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','f2e386dc-31ca-48c0-b7e0-0c782cba35d4','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-25','','2019-W13'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','bf0049b0-ff3a-446d-8a8e-7d997b5ea9cd','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-26','','2019-W13'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','ab18b9a2-85a3-4606-9fda-850233590624','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-27','','2019-W13'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','08229e1a-78c2-41da-8da6-2d9aa8c655a6','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-28','','2019-W13'),
 ('f69b330a-f184-44ea-8849-8006af8f1089','9889f0dc-db9a-4a5a-81d9-5402ab3314c2','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-29','','2019-W13');

insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','ad98a1c4-80ac-40a1-a427-0697335da197','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-25','','2019-W09'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','c6fdbc6f-a9ee-4e17-803d-79a0d01463a1','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-26','','2019-W09'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','7e9c4d83-2686-43f0-b689-8c4c7dadff99','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-27','','2019-W09'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','9c58e308-213a-469f-b005-d49365a28b7b','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-02-28','','2019-W09'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','3a8d2973-48a3-46bc-8d9e-0fa37f918534','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-01','','2019-W09'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','a358c79d-1ce6-4caa-a87c-1304ef6ee630','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-04','','2019-W10'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','9e56fbfd-d02a-4eea-b08e-f43db2b21c01','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-05','','2019-W10'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','1993b879-59d8-4bf1-9002-8dc1db231cc3','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-06','','2019-W10'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','8407118e-d006-4e13-bac1-76d65a7d6bc9','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-07','','2019-W10'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','620cb80a-c516-47aa-8806-b951b4898b33','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-08','','2019-W10'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','0434b507-cb72-47e5-b051-81cc4ddf4f55','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-11','','2019-W11'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','6bce0fd4-f368-4961-913c-c73be0297396','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-12','','2019-W11'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','30e1897d-dc0d-47df-855a-a747a972ac08','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-13','','2019-W11'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','994973d5-509f-4afa-a0c0-f07f483faf17','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-14','','2019-W11'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','16388a4d-3d55-4b48-b047-e9de580cb798','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-15','','2019-W11'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','04cd9546-aa62-4aa8-b71d-124998918c4b','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-18','','2019-W12'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','88b04a12-9e2f-4b76-bb02-ae2e6802eb44','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-19','','2019-W12'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','9bb24e6d-b407-4be5-bc71-55fada8287de','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-20','','2019-W12'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','1614776b-aaf4-4ae6-b577-1360a9abf04e','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-21','','2019-W12'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','40a4b10a-8099-4656-9a29-cf2f0af6cdb2','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-22','','2019-W12'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','aa050cf4-c95a-4b99-a7e8-9f1fa9211562','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-25','','2019-W13'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','b196e1c5-d5be-4e2b-baf3-61fc0411b411','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-26','','2019-W13'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','00fe068e-666f-40e2-b399-cd20fe398759','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-27','','2019-W13'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','b43d3f9c-abd5-4e4d-8bd5-d1c48dc3fdad','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-28','','2019-W13'),
 ('a4a0aad6-4f79-4543-903e-347c9edd7b96','7b4325b6-7d4b-41c7-870e-4867d3ddc72f','7041cb03-200d-457c-84a9-a4881527448f',1,0.0,'2019-03-29','','2019-W13');

insert into TIMESHEET_DB.time_entry (project_guid,time_entry_guid, user_guid, status_id, hours_number, date, comment, week) values
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','baecfa2f-1197-4842-8d28-a263e56585c0','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-02-25','','2019-W09'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','1b540a61-534b-4324-8dae-62d94bd11586','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-02-26','','2019-W09'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','03764006-41ad-49ae-8185-4667c257f6eb','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-02-27','','2019-W09'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','d5454e99-b082-47e5-8013-8cda402ed207','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-02-28','','2019-W09'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','52dd3c62-bd37-43be-aa1d-31a0db343af0','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-01','','2019-W09'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','b86277e8-a1e0-44f0-a4db-8f0635f15cfb','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-04','','2019-W10'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','85664443-16fe-4bd1-b627-2476868fea15','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-05','','2019-W10'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','a479d236-df70-4968-bf8c-1bf7ea0c8367','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-06','','2019-W10'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','b25731a8-fb5b-4fd9-a613-480ff0d287c8','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-07','','2019-W10'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','0b9c652c-a096-467d-a2f9-c3c722837ac1','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-08','','2019-W10'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','46ef1af8-9b44-4763-a413-a352a72d82ca','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-11','','2019-W11'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','ed6a5f94-3f06-421a-895f-245425f86113','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-12','','2019-W11'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','a78534b4-8a1a-489d-abe4-11a27268fd88','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-13','','2019-W11'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','b8b1fb8a-5970-45ac-8207-296b02bfd202','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-14','','2019-W11'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','06d92663-9664-44ac-98c4-5d850a56a742','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-15','','2019-W11'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','24388b20-c27c-44d5-9be5-4e282c785ea1','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-18','','2019-W12'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','b97c5af6-05b4-4364-b2ed-eadd3f7a2338','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-19','','2019-W12'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','43d2a8dd-99aa-4eaa-987c-b93558982f89','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-20','','2019-W12'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','c68dc898-b9ce-40b4-bcb3-1f74b43b3134','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-21','','2019-W12'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','29147575-cf99-46d5-b787-93c55ad91979','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-22','','2019-W12'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','61a92e5b-2638-42e9-9641-eab070194abd','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-25','','2019-W13'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','194e5f62-39cb-4a31-98e1-5f8e7f4c8970','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-26','','2019-W13'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','8a1590fb-b0dd-4d66-b1f5-455483c68b34','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-27','','2019-W13'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','853167ba-4ed3-400e-a425-c46a73ca1b89','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-28','','2019-W13'),
 ('666536ba-906a-4fc3-85bf-e68e3e88c28c','55c3c27b-bc40-477c-85de-9ed0991c2e3f','64616e59-86cd-432e-a534-ad2ec72a006a',1,0.0,'2019-03-29','','2019-W13');