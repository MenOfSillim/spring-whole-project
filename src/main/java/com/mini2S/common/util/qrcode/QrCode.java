package com.mini2S.common.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mini2S.common.enums.ImageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class QrCode {
    public static String createQRCodeImage(String featureName, String uniqueName, String fileName, String url) throws IOException {
        String suffix = "png";
        String name = fileName + "." + suffix;
        final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
//        Resource resource = defaultResourceLoader.getResource("classpath:static/image/"+featureDirectory);
        Resource resource = defaultResourceLoader.getResource("file:src/main/resources/static/image/" + featureName);
        String path = resource.getFile().getAbsolutePath();
        log.info("QrCode > createQRCodeImage > path : [{}]",path);

        try {
            // QR 코드 이미지 저장 디렉토리 지정
            File file = new File(path + "/" + uniqueName);
            log.info("QrCode > createQRCodeImage > file : [{}]",file);
            if (!file.exists()) {
                System.out.println("디렉토리가 존재하지 않음");
                file.mkdirs();
            }

            // qr코드 인식시 이동할 url 주소
            String codeUrl = new String(url.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

            // QR 코드(URL + 바코드 형태 + 사이즈)
            BitMatrix bitMatrix = new QRCodeWriter().encode(codeUrl,
                                                            BarcodeFormat.QR_CODE,
                                                            ImageEnum.QRCODE_WIDTH.getValue(),
                                                            ImageEnum.QRCODE_HEIGHT.getValue());

            // QR 코드 색상 지정(2종류)
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(
                                                            ImageEnum.QRCODE_COLOR.getValue(),
                                                            ImageEnum.QRCODE_BG_COLOR.getValue());

            // 이미지 객체 생성
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

            String qrPath = path + "/" + uniqueName + "/" + name;
            log.info("QrCode > createQRCodeImage > qrPath : [{}]",qrPath);
            // 파일 생성
            ImageIO.write(bufferedImage, suffix, new File(qrPath));

            return "/image/"+featureName+"/"+uniqueName+"/"+name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
