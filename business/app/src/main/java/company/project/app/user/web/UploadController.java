package company.project.app.user.web;

import cn.hutool.core.io.IoUtil;
import company.project.core.config.MinioConfig;
import company.project.core.config.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Autowired(required = false)
    MinioClient minioClient;

    @Autowired(required = false)
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

    @RequestMapping(path = "/down/{filename}", method = RequestMethod.GET)
    public void down(@PathVariable("filename") String filename, HttpServletResponse httpServletResponse) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        log.info("get the file:{} from minio ", filename);
        GetObjectResponse minioClientObject = minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(filename).build());
        Headers headers = minioClientObject.headers();
        for (String name : headers.names()) {
            httpServletResponse.addHeader(name,headers.get(name));
        }
        IoUtil.copy(minioClientObject,httpServletResponse.getOutputStream());
    }
}
