DROP TABLE refusal;

CREATE TABLE refusal(
                        id SERIAL NOT NULL PRIMARY KEY,
                        element VARCHAR NOT NULL,
                        lambda VARCHAR,
                        unit VARCHAR
);
