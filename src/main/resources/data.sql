-- DROP TABLE IF EXISTS BUS;
--
-- CREATE TABLE BUS (
--     ID INT AUTO_INCREMENT PRIMARY KEY,
--     PLATE_NUMBER VARCHAR(11) NOT NULL,
--     TYPE VARCHAR(20) NOT NULL,
--     COLOR VARCHAR(10) NOT NULL,
--     CAPACITY INT NOT NULL,
--     DEPOT_ID INT NULL
-- );
--
-- -- CONSTRAINTS, INDEXES!
--
-- DROP TABLE IF EXISTS DEPOT;
--
-- CREATE TABLE DEPOT (
--     ID INT AUTO_INCREMENT PRIMARY KEY,
--     DEPOT_NAME VARCHAR(255) NOT NULL,
--     CAPACITY INT NOT NULL
-- );

ALTER TABLE BUS
    ADD FOREIGN KEY (DEPOT_ID)
    REFERENCES DEPOT(ID);

-- INSERT DATA

INSERT INTO DEPOT (DEPOT_NAME, CAPACITY) VALUES
('Grand Central Station', 20),
('Downtown', 5),
('Uptown', 10),
('Far Far Away', 20),
('Diagonalley', 7);

INSERT INTO BUS (PLATE_NUMBER, TYPE, COLOR, CAPACITY, DEPOT_ID) VALUES
('BUS-SBZ-001', 'REGULAR', 'GREEN', 50, 4),
('BUS-MUC-001', 'DOUBLE_DECKER', 'ORANGE', 40, 1),
('BUS-VIE-567', 'REGULAR', 'GREEN', 30, null),
('BUS-BUC-321', 'REGULAR', 'GREEN', 60, null),
('BUS-MIL-202', 'DOUBLE_DECKER', 'ORANGE', 55, 4),
('BUS-LON-787', 'DOUBLE_DECKER', 'GREEN', 25, 4);
