CREATE TABLE if not exists error_log (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         service_name TEXT NOT NULL,
                                         stack_trace TEXT,
                                         request_endpoint TEXT,
                                         created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);