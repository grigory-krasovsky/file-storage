package com.filestorage.adapter.grpc;

import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import com.filestorage.grpc.GrpcFileAccessSaveResponse;
import com.filestorage.grpc.GrpcFileChunk;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class FileStreamObserver implements StreamObserver<GrpcFileChunk> {

    private final ByteArrayOutputStream fileData = new ByteArrayOutputStream();
    private final StreamObserver<GrpcFileAccessSaveResponse> responseObserver;
    private final FileStreamProcessor fileStreamProcessor;
    private String id;
    private String filename;
    private String contentType;

    @Override
    public void onNext(GrpcFileChunk grpcFileChunk) {
        try {
            captureMetadataIfFirstChunk(grpcFileChunk);
            appendChunkData(grpcFileChunk);
        } catch (Exception e) {
            handleError(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void onCompleted() {
        try {
            GrpcFileAccessSaveRequest request = buildSaveRequest();
            fileStreamProcessor.processFile(request);
//            fileAccessManager.createFileAccess(request);
            sendSuccessResponse();
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void captureMetadataIfFirstChunk(GrpcFileChunk chunk) {
        if (id == null) {
            id = chunk.getId();
            filename = chunk.getFilename();
            contentType = chunk.getContentType();
        }
    }

    private void appendChunkData(GrpcFileChunk chunk) throws IOException {
        fileData.write(chunk.getChunk().toByteArray());
    }

    private GrpcFileAccessSaveRequest buildSaveRequest() {
        return GrpcFileAccessSaveRequest.newBuilder()
                .setId(id)
                .setContents(ByteString.copyFrom(fileData.toByteArray()))
                .setFilename(filename)
                .setContentType(contentType)
                .build();
    }

    private void sendSuccessResponse() {
        GrpcFileAccessSaveResponse response = GrpcFileAccessSaveResponse.newBuilder()
                .setSuccess(true)
                .setMessage("File saved successfully via streaming")
                .setFileId(id)
                .setSize(fileData.size())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void handleError(Throwable t) {
        responseObserver.onError(t);
    }
}
