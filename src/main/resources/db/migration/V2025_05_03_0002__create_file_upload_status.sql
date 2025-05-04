CREATE TABLE file_upload_status (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               file_id UUID NOT NULL REFERENCES file_location(id),
                               status TEXT NOT NULL ,
                               stack_trace TEXT,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Add comments for documentation
COMMENT ON TABLE file_upload_status IS 'Stores file upload status in the system';
COMMENT ON COLUMN file_upload_status.id IS 'UUID primary key';
COMMENT ON COLUMN file_upload_status.file_id IS 'FK to file_location';
COMMENT ON COLUMN file_upload_status.status IS 'upload result status';
COMMENT ON COLUMN file_upload_status.stack_trace IS 'Stack trace in case of an error';
COMMENT ON COLUMN file_upload_status.created_at IS 'Timestamp when record was created (with timezone)';