package company.project.app.user.web;

import company.project.core.config.MinioConfig;
import company.project.core.config.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;

    @RequestMapping(method = RequestMethod.POST)
    public void upload(@RequestParam Map<String, MultipartFile> files) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        for (String s : files.keySet()) {
            MultipartFile multipartFile = files.get(s);

            log.info(s, multipartFile);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(multipartFile.getOriginalFilename())
                    .contentType(multipartFile.getContentType())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .build());
        }

    }
}
