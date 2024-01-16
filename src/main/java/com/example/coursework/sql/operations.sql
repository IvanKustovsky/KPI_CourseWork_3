CREATE FUNCTION calculate_contract_amount(ad_duration INT, id_program INT)
    RETURNS DECIMAL(10, 2)
    DETERMINISTIC
BEGIN
    DECLARE program_rate DECIMAL(10, 2);

    -- Отримати вартість реклами для вказаної передачі (program_id)
    SELECT rate
    INTO program_rate
    FROM program
    WHERE program_id = id_program;

    -- Повернути результат обчислення
    RETURN ad_duration * program_rate;
END;

CREATE VIEW AgentDetailsView AS
SELECT CONCAT('id: ', agent_id, ' | ', agent_name, ' ', agent_surname, ' | ', commission_rate, ' %') AS agent_details
FROM agent;


CREATE PROCEDURE GetAllAgentDetails()
BEGIN
    SELECT * FROM AgentDetailsView;
END;


CREATE FUNCTION get_ad_count(program_id_param INT, specific_date_param DATE)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE ad_count INT;

    SELECT COUNT(*)
    INTO ad_count
    FROM commercial
    WHERE program_id = program_id_param
      AND air_date = specific_date_param;

    RETURN ad_count;
END;


CREATE PROCEDURE GetOrderDetailsByCustomerId(IN customer_id INT)
BEGIN
    CREATE TEMPORARY TABLE temp_order_details
    (
        commercialId   INT,
        programName    VARCHAR(255),
        programRating  DOUBLE,
        programRate    DECIMAL(10, 2),
        airDate        DATE,
        adDuration     INT,
        contractAmount DECIMAL,
        agentDetails   VARCHAR(255)
    );

    INSERT INTO temp_order_details
    SELECT c.commercial_id,
           p.program_name,
           p.rating,
           p.rate,
           c.air_date,
           c.duration,
           con.contract_amount,
           CONCAT('id: ', a.agent_id, ' | ', a.agent_name, ' ', a.agent_surname, ' | ', a.commission_rate,
                  ' %') AS agentDetails
    FROM commercial c
             JOIN program p ON c.program_id = p.program_id
             JOIN contract con ON c.commercial_id = con.commercial_id
             JOIN agent a ON con.agent_id = a.agent_id
    WHERE c.customer_id = customer_id;

    SELECT * FROM temp_order_details;

    DROP TEMPORARY TABLE IF EXISTS temp_order_details;
END;

CREATE OR REPLACE VIEW view_all_order_details AS
SELECT c.commercial_id AS commercialId,
       cus.bank_details AS bankDetails,
       p.program_name AS programName,
       p.rating AS programRating,
       p.rate AS programRate,
       c.air_date AS airDate,
       c.duration AS adDuration,
       con.contract_amount AS contractAmount,
       CONCAT('id: ', a.agent_id, ' | ', a.agent_name, ' ', a.agent_surname, ' | ', a.commission_rate,
              ' %') AS agentDetails
FROM commercial c
         JOIN program p ON c.program_id = p.program_id
         JOIN contract con ON c.commercial_id = con.commercial_id
         JOIN agent a ON con.agent_id = a.agent_id
         JOIN customer cus ON c.customer_id = cus.customer_id;


CREATE PROCEDURE GetAllOrdersDetails()
BEGIN
    SELECT * FROM view_all_order_details ORDER BY commercialId;
END;



CREATE PROCEDURE GetAgentNameAndSurname()
BEGIN
    SELECT CONCAT(agent_name, ' ', agent_surname) AS agent_full_name
    FROM agent;
END;

CREATE PROCEDURE GetAllContractsDetails()
BEGIN
    DROP TEMPORARY TABLE IF EXISTS temp_contract_details;

    CREATE TEMPORARY TABLE temp_contract_details
    (
        contract_id     INT,
        agentFullName   VARCHAR(255),
        agentRate       DECIMAL(10, 2),
        companyDetails  VARCHAR(50),
        contract_amount DECIMAL,
        agentCommission DECIMAL(10, 2)
    );

    INSERT INTO temp_contract_details
    SELECT con.contract_id                                 AS contractId,
           CONCAT(a.agent_name, ' ', a.agent_surname)      AS agentFullName,
           a.commission_rate                               AS agentRate,
           cus.bank_details                                AS companyDetails,
           con.contract_amount                             AS contractAmount,
           (con.contract_amount * a.commission_rate / 100) AS agentCommission
    FROM contract con
             JOIN agent a ON con.agent_id = a.agent_id
             JOIN commercial c ON con.commercial_id = c.commercial_id
             JOIN customer cus on cus.customer_id = c.customer_id;

    SELECT * FROM temp_contract_details ORDER BY contract_id;
    DROP TEMPORARY TABLE IF EXISTS temp_contract_details;
END;


CREATE TRIGGER before_insert_contact_person
    BEFORE INSERT
    ON contact_person
    FOR EACH ROW
BEGIN
    IF NEW.lastname IS NULL THEN
        SET NEW.lastname = 'Не вказано';
    END IF;
END;


CREATE PROCEDURE CalculateAgentRevenue()
BEGIN
    DECLARE agentIdVar INT;
    DECLARE commissionRate DECIMAL(5, 2);
    DECLARE totalContractAmount DECIMAL(10, 2);
    DECLARE totalRevenueVar DECIMAL(10, 2);
    DECLARE done INT DEFAULT 0;

    DECLARE agentCursor CURSOR FOR
        SELECT c.agent_id, a.commission_rate, COALESCE(SUM(c.contract_amount), 0) AS total_contract_amount
        FROM agent a
                 JOIN contract c ON c.agent_id = a.agent_id
        GROUP BY c.agent_id;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN agentCursor;

    REPEAT
        FETCH agentCursor INTO agentIdVar, commissionRate, totalContractAmount;

        IF NOT done THEN
            SET totalRevenueVar = totalContractAmount * (commissionRate / 100);

            UPDATE agent
            SET total_revenue = totalRevenueVar
            WHERE agent_id = agentIdVar;
        END IF;

    UNTIL done END REPEAT;

    CLOSE agentCursor;

END;


