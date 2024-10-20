package ifg.dto;

import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

public class UploadObjectRequestDto {

    @RestForm
    private String bucketName;

    @RestForm
    private String fileName;

    @RestForm
    private File file;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
