package ifg.controller;

import ifg.dto.UploadObjectRequestDto;
import ifg.service.GoogleCloudStorageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.MultipartForm;

import java.io.IOException;

@Path("/v1/storages")
public class GoogleCloudStorageController {

    @Inject
    GoogleCloudStorageService googleCloudStorageService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBucket(){
        return googleCloudStorageService.listBucket();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/objects")
    public Response listObjectFromBucket(
            @QueryParam("bucketName") String bucketName
    ){
        return googleCloudStorageService.listObjectFromBucket(bucketName);
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/download")
    public Response downloadObject(
            @QueryParam("bucketName") String bucketName,
            @QueryParam("fileName") String fileName
    ){
        return googleCloudStorageService.getObjectFromBucket(bucketName,fileName);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    public Response uploadObject(
            @MultipartForm UploadObjectRequestDto request
            ) throws IOException {
        return googleCloudStorageService.uploadObject(request.getBucketName(), request.getFileName(), request.getFile());
    }

}
