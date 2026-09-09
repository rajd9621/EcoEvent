package com.ecoevent.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class QRCodeUtil {

    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;

    public String generateQRCodeBase64(String text) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (Exception e) {
            throw new WriterException("Failed to generate QR code: " + e.getMessage());
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
