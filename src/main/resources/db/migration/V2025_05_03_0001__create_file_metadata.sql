CREATE TABLE file_metadata (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               file_location_id UUID NOT NULL REFERENCES file_location(id),
                               author TEXT,
                               "comment" TEXT,
                               file_date_time TIMESTAMP WITH TIME ZONE,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Add comments for documentation
COMMENT ON TABLE file_metadata IS 'Stores file metadata in the system';
COMMENT ON COLUMN file_metadata.id IS 'UUID primary key';
COMMENT ON COLUMN file_metadata.file_location_id IS 'FK to file_location';
COMMENT ON COLUMN file_metadata.file_date_time IS 'Date and time of the file';
COMMENT ON COLUMN file_metadata.comment IS 'Optional comment text';
COMMENT ON COLUMN file_metadata.author IS 'Optional author';
COMMENT ON COLUMN file_metadata.created_at IS 'Timestamp when record was created (with timezone)';