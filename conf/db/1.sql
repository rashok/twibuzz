CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;


drop table call_log;

CREATE TABLE call_log
(
    id            BIGINT                 NOT NULL,
    sid           TEXT,
    ac_id         TEXT,
    status        CHARACTER VARYING(255),
    who           CHARACTER VARYING(255),
    caller        CHARACTER VARYING(255),
    created       TIMESTAMP(6) WITHOUT TIME ZONE,
    modified      TIMESTAMP(6) WITHOUT TIME ZONE,
    duration      CHARACTER VARYING(255),
    delay         BIGINT,
    type          CHARACTER VARYING(255),
    input         CHARACTER VARYING(255),
    PRIMARY KEY (id)
);

-- Create an index on call sid
CREATE UNIQUE INDEX idx_sid ON call_log (sid);