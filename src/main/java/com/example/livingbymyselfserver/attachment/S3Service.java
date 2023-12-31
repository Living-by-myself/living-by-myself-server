package com.example.livingbymyselfserver.attachment;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.livingbymyselfserver.attachment.entity.Attachment;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "S3Service")
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    public List<String> uploadFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();

        if (files.length > 5) {
            throw new IllegalArgumentException("사진 최대 추가개수는 5개 입니다.");
        }

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + file.getOriginalFilename(); // fileName을 난수와 함께 저장
            String fileUrl = S3FileUpload(file, fileName);
            fileNames.add(fileUrl);
        }

        return fileNames;
    }

    public String uploadFile(MultipartFile files) {
        String fileNames;
//            String fileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + files.getOriginalFilename(); // fileName을 난수와 함께 저장
        String fileUrl = S3FileUpload(files, fileName);
        fileNames = fileUrl;

        return fileNames;
    }

    public String S3FileUpload(MultipartFile multipartFile, String fileName) {
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("이미지의 크기가 너무 큽니다");
        }
    }

    public String CombineString(List<String> stringList) {
        String result = "";
        for (String str : stringList) {
            result = result + "," + str;
        }
        return result;
    }

    public void deleteFile(String originalFilename) {
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);
        log.info(fileName);

        amazonS3.deleteObject(bucket, fileName);
        amazonS3.deleteObject(bucket, "resize/"+fileName);
    }

    public void updateS3Image(Attachment attachmentUrl, MultipartFile[] multipartFiles) {
        String[] fileList = attachmentUrl.getFileName().split(",");

        for (String file : fileList) {
            deleteFile(file);
        }
        attachmentUrl.setFileName("");

        if ((multipartFiles.length) > 5) {
            throw new IllegalArgumentException("사진의 최대개수는 5개 입니다.");
        }
        List<String> uploadFileNames = uploadFiles(multipartFiles);
        String combineUploadFileName = CombineString(uploadFileNames);

        String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
        String result = (replaceUploadFileName).replaceFirst("^,",
                "");

        attachmentUrl.setFileName(result);
    }
}
