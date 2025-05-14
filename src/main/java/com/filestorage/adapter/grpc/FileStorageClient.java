package com.filestorage.adapter.grpc;

import com.filestorage.core.exception.GrpcException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import com.filestorage.grpc.GrpcFileAccessSaveResponse;
import com.filestorage.grpc.GrpcFileChunk;
import com.filestorage.grpc.FileStorageServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.filestorage.core.exception.GrpcException.GRPC_OPERATION_FAILED;

@GrpcService
public class FileStorageClient extends FileStorageServiceGrpc.FileStorageServiceImplBase {
    @GrpcClient("file-storage")
    private FileStorageServiceGrpc.FileStorageServiceBlockingStub blockingStub;

    @GrpcClient("file-storage")
    private FileStorageServiceGrpc.FileStorageServiceStub asyncStub;

    @Override
    public void saveFile(GrpcFileAccessSaveRequest request, StreamObserver<GrpcFileAccessSaveResponse> responseObserver) {
        ByteString contents = request.getContents();
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
