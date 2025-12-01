/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
DELETE
FROM Customer;
INSERT INTO Customer (id, firstname, lastname)
VALUES (1, 'Dave', 'Matthews');
INSERT INTO Customer (id, firstname, lastname)
VALUES (2, 'Carter', 'Beauford');
INSERT INTO Customer (id, firstname, lastname)
VALUES (3, 'Stephan', 'Lassard');

INSERT INTO Account (id, customer_id, expiry_date)
VALUES (1, 1, '2010-12-31');
INSERT INTO Account (id, customer_id, expiry_date)
VALUES (2, 1, '2011-03-31');

INSERT INTO Customer (id, firstname, lastname)
VALUES (4, 'Charly', 'Matthews');
INSERT INTO Customer (id, firstname, lastname)
VALUES (5, 'Chris', 'Matthews');
INSERT INTO Customer (id, firstname, lastname)
VALUES (6, 'Paula', 'Matthews');
