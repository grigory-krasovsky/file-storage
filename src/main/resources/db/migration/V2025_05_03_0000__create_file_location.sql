CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create file_location table
CREATE TABLE file_location (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               file_path VARCHAR(1000) NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Optional constraints/indexes
                               CONSTRAINT file_path_unique UNIQUE (file_path)
);

-- Optional: Add comments for documentation
COMMENT ON TABLE file_location IS 'Stores physical file locations in the system';
COMMENT ON COLUMN file_location.id IS 'UUID primary key';
COMMENT ON COLUMN file_location.file_path IS 'Absolute filesystem path (max 1000 chars)';
COMMENT ON COLUMN file_location.created_at IS 'Timestamp when record was created (with timezone)';