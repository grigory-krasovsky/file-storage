package com.filestorage.adapter.grpc;

import com.filestorage.core.service.FileAccessManager;
import com.filestorage.grpc.FileStorageServiceGrpc;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import com.filestorage.grpc.GrpcFileAccessSaveResponse;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class FileStorageClient extends FileStorageServiceGrpc.FileStorageServiceImplBase {

    private final FileAccessManager fileAccessManager;
    @GrpcClient("file-storage")
    private FileStorageServiceGrpc.FileStorageServiceBlockingStub blockingStub;

    @GrpcClient("file-storage")
    private FileStorageServiceGrpc.FileStorageServiceStub asyncStub;

    public FileStorageClient(FileAccessManager fileAccessManager) {
        this.fileAccessManager = fileAccessManager;
    }

    @Override
    public void saveFile(GrpcFileAccessSaveRequest request, StreamObserver<GrpcFileAccessSaveResponse> responseObserver) {

        fileAccessManager.createFileAccess(request);

        GrpcFileAccessSaveResponse response = GrpcFileAccessSaveResponse.newBuilder()
                .setSuccess(true)
                .setMessage("File saved successfully")
                .setFileId(request.getId())
                .setSize(request.getContents().size())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
