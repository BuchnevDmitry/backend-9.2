ALTER TABLE rent
    ADD COLUMN time_receiving_id bigint not null,
ADD CONSTRAINT fk_time_receiving
FOREIGN KEY (time_receiving_id) REFERENCES time_receiving(id);
