package com.filestorage.adapter.grpc;

import com.filestorage.core.service.FileAccessManager;
import com.filestorage.grpc.FileStorageServiceGrpc;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import com.filestorage.grpc.GrpcFileAccessSaveResponse;
import com.filestorage.grpc.GrpcFileChunk;
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

    @Override
    public StreamObserver<GrpcFileChunk> saveFileStream(
            StreamObserver<GrpcFileAccessSaveResponse> responseObserver) {
        return new FileStreamObserver(responseObserver, fileAccessManager::createFileAccess);
    }

    //working:
//    @Override
//    public StreamObserver<GrpcFileChunk> saveFileStream(StreamObserver<GrpcFileAccessSaveResponse> responseObserver) {
//        StreamObserver<GrpcFileChunk> fileSavedSuccessfullyViaStreaming = new StreamObserver<>() {
//            private ByteArrayOutputStream fileData = new ByteArrayOutputStream();
//            private String id;
//            private String filename;
//            private String contentType;
//
//            @Override
//            public void onNext(GrpcFileChunk chunk) {
//                try {
//                    // Store metadata from first chunk
//                    if (id == null) {
//                        id = chunk.getId();
//                        filename = chunk.getFilename();
//                        contentType = chunk.getContentType();
//                    }
//
//                    // Accumulate chunks
//                    fileData.write(chunk.getChunk().toByteArray());
//                } catch (Exception e) {
//                    responseObserver.onError(e);
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                responseObserver.onError(t);
//
//            }
//
//            @Override
//            public void onCompleted() {
//                try {
//                    // Create file from accumulated chunks
//                    GrpcFileAccessSaveRequest request = GrpcFileAccessSaveRequest.newBuilder()
//                            .setId(id)
//                            .setContents(ByteString.copyFrom(fileData.toByteArray()))
//                            .setFilename(filename)
//                            .setContentType(contentType)
//                            .build();
//
//                    fileAccessManager.createFileAccess(request);
//
//                    GrpcFileAccessSaveResponse response = GrpcFileAccessSaveResponse.newBuilder()
//                            .setSuccess(true)
//                            .setMessage("File saved successfully via streaming")
//                            .setFileId(id)
//                            .setSize(fileData.size())
//                            .build();
//
//                    responseObserver.onNext(response);
//                    responseObserver.onCompleted();
//                } catch (Exception e) {
//                    responseObserver.onError(e);
//                }
//            }
//        };
//        return fileSavedSuccessfullyViaStreaming;
//    }

}
