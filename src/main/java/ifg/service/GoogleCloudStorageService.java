package ifg.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import ifg.dto.ListObjectResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@ApplicationScoped
public class GoogleCloudStorageService {

    @ConfigProperty(name = "quarkus.google.cloud.storage.project-id")
    String projectId;

    private final Storage  storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    public Response listBucket(){
        Page<Bucket> buckets = storage.list();
        return Response.ok(buckets.streamAll().map(b -> b.getName()).collect(Collectors.toSet())).build();
    }

    public Response listObjectFromBucket(String bucketName){
        Page<Blob> blobs = storage.list(bucketName);
        return Response.ok(blobs.streamAll().map(b -> new ListObjectResponseDto(b.getGeneratedId(), b.getName())).collect(Collectors.toSet())).build();
    }

    public Response getObjectFromBucket(String bucketName, String fileName){
        byte[] bytesFile = storage.readAllBytes(BlobId.of(bucketName,fileName));
        Response.ResponseBuilder responseBuilder = Response.ok(bytesFile);
        responseBuilder.header("Content-Disposition", "attachment; filename="+fileName);
        return responseBuilder.build();
    }

    public Response uploadObject(String bucketName, String fileName, File file) throws IOException {
        BlobId blobId = BlobId.of(bucketName,fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        storage.createFrom(blobInfo,file.toPath());
        return Response.ok().build();
    }
}
