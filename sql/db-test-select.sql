connect 'jdbc:derby:SummaryTask4;create=true';

SELECT * FROM users;
SELECT * FROM entrants;
SELECT * FROM certificates;
SELECT * FROM applications;
SELECT * FROM faculties;
SELECT * FROM faculties_subjects;

DISCONNECT;