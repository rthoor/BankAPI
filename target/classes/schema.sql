DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS transaction_history CASCADE;
DROP SEQUENCE IF EXISTS number_sequence;
CREATE SEQUENCE number_sequence START WITH 1000000 INCREMENT BY 1;

CREATE TABLE account (
                      number INTEGER PRIMARY KEY NOT NULL,
                      balance INTEGER NOT NULL,
                      beneficiary_name VARCHAR(255) NOT NULL,
                      pin_code_hash VARCHAR(255) NOT NULL,
                      is_active BOOLEAN NOT NULL);

CREATE TABLE transaction_history (
                          id UUID PRIMARY KEY,
                          balance_before INTEGER NOT NULL,
                          balance_after INTEGER NOT NULL,
                          amount INTEGER NOT NULL,
                          date_time TIMESTAMP(6) NOT NULL,
                          is_successful BOOLEAN NOT NULL,
                          transaction_type VARCHAR(255) CHECK (transaction_type IN ('DEPOSIT','WITHDRAW','TRANSFER')),
                          sender_number INTEGER,
                          receiver_number INTEGER);

ALTER TABLE transaction_history
    ADD CONSTRAINT fk_sender_number
        FOREIGN KEY (sender_number)
            REFERENCES account (number);

ALTER TABLE transaction_history
    ADD CONSTRAINT fk_receiver_number
        FOREIGN KEY (receiver_number)
            REFERENCES account (number);