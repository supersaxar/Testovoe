DROP TABLE IF EXISTS sorting_results;

CREATE TABLE sorting_results (
    id SERIAL PRIMARY KEY,
    sorting_id BIGINT,
    position INTEGER,
    value INTEGER
);