-- SmartHostel Sample Data for Testing
USE smarthostel;

-- Insert Hostels
INSERT INTO HOSTELS (HostelNo, HostelName, TotalRooms, TotalFloors, Type) VALUES
('H1', 'Block A - Boys Hostel', 100, 4, 'BOYS'),
('H2', 'Block B - Boys Hostel', 80, 4, 'BOYS'),
('H3', 'Block C - Girls Hostel', 100, 4, 'GIRLS'),
('H4', 'Block D - Girls Hostel', 60, 3, 'GIRLS');

-- Insert Rooms
INSERT INTO ROOM (RoomNo, Capacity, HostelNo, Floor) VALUES
-- Block A
('A-101', 2, 'H1', 1), ('A-102', 2, 'H1', 1), ('A-103', 2, 'H1', 1), ('A-104', 2, 'H1', 1),
('A-201', 2, 'H1', 2), ('A-202', 2, 'H1', 2), ('A-203', 2, 'H1', 2), ('A-204', 2, 'H1', 2),
('A-301', 2, 'H1', 3), ('A-302', 2, 'H1', 3), ('A-303', 2, 'H1', 3), ('A-304', 2, 'H1', 3),
-- Block B
('B-101', 2, 'H2', 1), ('B-102', 2, 'H2', 1), ('B-103', 2, 'H2', 1),
('B-201', 2, 'H2', 2), ('B-202', 2, 'H2', 2), ('B-203', 2, 'H2', 2),
-- Block C
('C-101', 2, 'H3', 1), ('C-102', 2, 'H3', 1), ('C-103', 2, 'H3', 1),
('C-201', 2, 'H3', 2), ('C-202', 2, 'H3', 2), ('C-203', 2, 'H3', 2);

-- Insert Students
INSERT INTO STUDENT (StudentID, FirstName, MiddleName, LastName, RoomNo, HostelNo) VALUES
('1RV21CS001', 'Rahul', NULL, 'Sharma', 'A-204', 'H1'),
('1RV21CS002', 'Priya', 'K', 'Nair', 'C-101', 'H3'),
('1RV21CS003', 'Amit', 'Kumar', 'Verma', 'A-201', 'H1'),
('1RV21CS004', 'Sneha', NULL, 'Reddy', 'C-102', 'H3'),
('1RV21CS005', 'Vikram', 'S', 'Iyer', 'A-301', 'H1'),
('1RV21CS006', 'Anjali', NULL, 'Menon', 'C-201', 'H3'),
('1RV21EC001', 'Karthik', 'R', 'Prasad', 'B-101', 'H2'),
('1RV21EC002', 'Divya', 'M', 'Patil', 'C-103', 'H3'),
('1RV21ME001', 'Arjun', NULL, 'Kumar', 'A-102', 'H1'),
('1RV21ME002', 'Meera', 'S', 'Krishnan', 'C-202', 'H3');

-- Insert Employees
INSERT INTO EMPLOYEES (SSN, FirstName, MiddleName, LastName, Role) VALUES
('EMP001', 'Priya', NULL, 'Rao', 'ADMIN'),
('EMP002', 'Ramesh', 'K', 'Gupta', 'WARDEN'),
('EMP003', 'Suresh', NULL, 'Kumar', 'MESS_STAFF'),
('EMP004', 'Mohan', 'R', 'Singh', 'SECURITY'),
('EMP005', 'Lakshmi', NULL, 'Devi', 'WARDEN');

-- Insert Users (passwords are BCrypt hashed 'password123')
INSERT INTO USERS (email, password, name, role, room_number, student_id, employee_ssn) VALUES
('student@rvce.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/P9RwX4C8T.YnWJZzQGu1a', 'Rahul Sharma', 'STUDENT', 'A-204', '1RV21CS001', NULL),
('admin@rvce.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/P9RwX4C8T.YnWJZzQGu1a', 'Dr. Priya Rao', 'ADMIN', NULL, NULL, 'EMP001'),
('mess@rvce.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/P9RwX4C8T.YnWJZzQGu1a', 'Suresh Kumar', 'MESS_STAFF', NULL, NULL, 'EMP003'),
('priya@rvce.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/P9RwX4C8T.YnWJZzQGu1a', 'Priya Nair', 'STUDENT', 'C-101', '1RV21CS002', NULL),
('amit@rvce.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/P9RwX4C8T.YnWJZzQGu1a', 'Amit Verma', 'STUDENT', 'A-201', '1RV21CS003', NULL);

-- Insert Student Phone Numbers
INSERT INTO STUDENTPHONENO (StudentID, PhoneNo, IsPrimary) VALUES
('1RV21CS001', '9876543210', TRUE),
('1RV21CS001', '9876543211', FALSE),
('1RV21CS002', '9876543212', TRUE),
('1RV21CS003', '9876543213', TRUE),
('1RV21CS004', '9876543214', TRUE),
('1RV21CS005', '9876543215', TRUE),
('1RV21CS006', '9876543216', TRUE);

-- Insert Room Assignments
INSERT INTO ROOMASSIGNEDTO (RoomNo, StudentID, AssignedDate, IsActive) VALUES
('A-204', '1RV21CS001', '2024-08-01', TRUE),
('C-101', '1RV21CS002', '2024-08-01', TRUE),
('A-201', '1RV21CS003', '2024-08-01', TRUE),
('C-102', '1RV21CS004', '2024-08-01', TRUE),
('A-301', '1RV21CS005', '2024-08-01', TRUE),
('C-201', '1RV21CS006', '2024-08-01', TRUE),
('B-101', '1RV21EC001', '2024-08-01', TRUE),
('C-103', '1RV21EC002', '2024-08-01', TRUE),
('A-102', '1RV21ME001', '2024-08-01', TRUE),
('C-202', '1RV21ME002', '2024-08-01', TRUE);

-- Insert Attendance Records (last 7 days)
INSERT INTO ATTENDANCE (StudentID, TimeStamp, Type, EmployeeSSN, Location) VALUES
('1RV21CS001', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 7 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS001', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 18 HOUR, 'IN', 'EMP004', 'Main Gate'),
('1RV21CS001', NOW() - INTERVAL 2 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS002', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 8 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS002', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 17 HOUR, 'IN', 'EMP004', 'Main Gate'),
('1RV21CS003', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 9 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS003', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 20 HOUR, 'IN', 'EMP004', 'Main Gate'),
('1RV21CS004', NOW() - INTERVAL 4 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS005', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 6 HOUR, 'OUT', 'EMP004', 'Main Gate'),
('1RV21CS005', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 22 HOUR, 'IN', 'EMP004', 'Main Gate');

-- Insert Meal Choices (opt-outs)
INSERT INTO CHOICE (Date, MealTime, StudentID, OptedOut) VALUES
(CURDATE(), 'BREAKFAST', '1RV21CS001', TRUE),
(CURDATE(), 'LUNCH', '1RV21CS002', TRUE),
(CURDATE(), 'DINNER', '1RV21CS003', TRUE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'BREAKFAST', '1RV21CS001', TRUE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'LUNCH', '1RV21CS001', TRUE),
(DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'BREAKFAST', '1RV21CS004', TRUE),
(DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'DINNER', '1RV21CS005', TRUE);

-- Insert Meal Menu
INSERT INTO MEALMENU (Day, MealTime, MenuItem, Category) VALUES
-- Monday
('MONDAY', 'BREAKFAST', 'Idli, Sambar, Chutney', 'South Indian'),
('MONDAY', 'BREAKFAST', 'Bread, Butter, Jam', 'Continental'),
('MONDAY', 'LUNCH', 'Rice, Dal, Sabzi, Roti, Curd', 'North Indian'),
('MONDAY', 'DINNER', 'Chapati, Paneer Butter Masala, Rice', 'North Indian'),
-- Tuesday
('TUESDAY', 'BREAKFAST', 'Dosa, Sambar, Chutney', 'South Indian'),
('TUESDAY', 'BREAKFAST', 'Poha, Tea', 'Indian'),
('TUESDAY', 'LUNCH', 'Rice, Rasam, Vegetable Curry, Papad', 'South Indian'),
('TUESDAY', 'DINNER', 'Pulao, Raita, Dal Fry', 'North Indian'),
-- Wednesday
('WEDNESDAY', 'BREAKFAST', 'Upma, Coconut Chutney', 'South Indian'),
('WEDNESDAY', 'BREAKFAST', 'Paratha, Curd', 'North Indian'),
('WEDNESDAY', 'LUNCH', 'Chole Bhature, Rice, Salad', 'North Indian'),
('WEDNESDAY', 'DINNER', 'Roti, Mixed Veg, Dal, Rice', 'North Indian'),
-- Thursday
('THURSDAY', 'BREAKFAST', 'Pongal, Sambar', 'South Indian'),
('THURSDAY', 'BREAKFAST', 'Cornflakes, Milk', 'Continental'),
('THURSDAY', 'LUNCH', 'Biryani, Raita, Mirchi Ka Salan', 'Hyderabadi'),
('THURSDAY', 'DINNER', 'Naan, Dal Makhani, Jeera Rice', 'North Indian'),
-- Friday
('FRIDAY', 'BREAKFAST', 'Vada, Sambar, Chutney', 'South Indian'),
('FRIDAY', 'BREAKFAST', 'Sandwich, Juice', 'Continental'),
('FRIDAY', 'LUNCH', 'Rice, Sambar, Kootu, Poriyal', 'South Indian'),
('FRIDAY', 'DINNER', 'Roti, Kadai Paneer, Rice, Dal', 'North Indian'),
-- Saturday
('SATURDAY', 'BREAKFAST', 'Puri Bhaji', 'North Indian'),
('SATURDAY', 'BREAKFAST', 'Omelette, Toast', 'Continental'),
('SATURDAY', 'LUNCH', 'Fried Rice, Manchurian, Soup', 'Chinese'),
('SATURDAY', 'DINNER', 'Chapati, Aloo Gobi, Rice, Curd', 'North Indian'),
-- Sunday
('SUNDAY', 'BREAKFAST', 'Masala Dosa, Sambar', 'South Indian'),
('SUNDAY', 'BREAKFAST', 'Pancakes, Honey', 'Continental'),
('SUNDAY', 'LUNCH', 'Special Biryani, Sweet, Salad', 'Special'),
('SUNDAY', 'DINNER', 'Pizza, Pasta, Ice Cream', 'Italian');
