package com.aik.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc: 二维码工具类
 * Create by as on 2018/1/16
 */
@Slf4j
public class QrCodeUtils {

    // 二维码颜色
    private static final int BLACK = 0xFF000000;
    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;
    // 二维码图片格式
    private static final List<String> IMAGE_TYPE = new ArrayList<>();

    static {
        IMAGE_TYPE.add("jpg");
        IMAGE_TYPE.add("png");
    }

    /**
     * zxing方式生成二维码
     * 注意：
     * 1,文本生成二维码的方法独立出来,返回image流的形式,可以输出到页面
     * 2,设置容错率为最高,一般容错率越高,图片越不清晰,但是只有设置高一点才能兼容logo图片
     * 3,logo图片默认占二维码图片的20%,设置太大会导致无法解析
     *
     * @param content  二维码包含的内容，文本或网址"Hello World"
     * @param path     二维码图片目录"C:/Users/Administrator/Desktop/QRcode2/b.jpg"
     * @param size     二维码图片尺寸 null or your size
     * @param logoPath 插入图片目录
     */
    public static boolean zxingCodeCreate(String content, String path, Integer size, String logoPath) {

        try {
            String imageType = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
            if (!IMAGE_TYPE.contains(imageType)) {
                return false;
            }

            //获取二维码流的形式，写入到目录文件中
            BufferedImage image = getBufferedImage(content, size, logoPath);

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            ImageIO.write(image, imageType, file);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 二维码流的形式，包含文本内容
     *
     * @param content 二维码文本内容
     * @param size    二维码尺寸
     * @return
     */
    private static BufferedImage getBufferedImage(String content, Integer size, String logoPath) {

        if (size == null || size <= 0) {
            size = 250;
        }

        BufferedImage image = null;
        try {
            // 设置编码字符集
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            //设置编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置容错率最高
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            // 1、生成二维码
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            // 2、获取二维码宽高
            int codeWidth = bitMatrix.getWidth();
            int codeHeight = bitMatrix.getHeight();

            // 3、将二维码放入缓冲流
            image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < codeWidth; i++) {
                for (int j = 0; j < codeHeight; j++) {
                    // 4、循环将二维码内容定入图片
                    image.setRGB(i, j, bitMatrix.get(i, j) ? BLACK : WHITE);
                }
            }

            //判断是否写入logo图片
            if (logoPath != null && !"".equals(logoPath)) {
                File logoPic = new File(logoPath);
                if (logoPic.exists()) {
                    Graphics2D g = image.createGraphics();
                    BufferedImage logo = ImageIO.read(logoPic);
                    int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ?
                            (image.getWidth() * 2 / 10) : logo.getWidth(null);
                    int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ?
                            (image.getHeight() * 2 / 10) : logo.getHeight(null);
                    int x = (image.getWidth() - widthLogo) / 2;
                    int y = (image.getHeight() - heightLogo) / 2;

                    // 开始绘制图片
                    g.drawImage(logo, x, y, widthLogo, heightLogo, null);
                    g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
                    //边框宽度
                    g.setStroke(new BasicStroke(2));
                    //边框颜色
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, widthLogo, heightLogo);

                    g.dispose();
                    logo.flush();
                    image.flush();
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    /**
     * zxing方式生成二维码的解析方法
     *
     * @param path 二维码图片目录
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Result zxingCodeAnalyze(String path) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File(path);
            if (file.exists()) {
                BufferedImage image = ImageIO.read(file);
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map hints = new HashMap();
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                return formatReader.decode(binaryBitmap, hints);
            }
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", 123);
        ObjectMapper om = new ObjectMapper();
        QrCodeUtils.zxingCodeCreate(om.writeValueAsString(params), "D:/test/aaa.jpg", 300, "D:/test/logo.png");

        Result result = QrCodeUtils.zxingCodeAnalyze("D:/test/aaa.jpg");
        System.out.println(result.getText());
    }
}