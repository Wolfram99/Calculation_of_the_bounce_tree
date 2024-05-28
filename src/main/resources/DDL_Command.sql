CREATE TABLE refusal(
    id SERIAL NOT NULL PRIMARY KEY,
    element VARCHAR NOT NULL,
    lambda float8,
    unit VARCHAR,
    value INTEGER
)