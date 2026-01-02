package com.ruoyi.common.utils.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加密工具类 (用于身份证等敏感信息加密)
 */
public class AesUtils {
    
    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);

    // 密钥 (必须是16位字符串，千万不能丢，丢了数据就找不回来了！)
    private static final String KEY = "Hospital_Keshe_K"; // 16 characters

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     * @param content 明文（如：320123...）
     * @return 密文（如：y8asdu19...）
     */
    public static String encrypt(String content) {
        if (content == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            return null;
        }
    }

    /**
     * 解密
     * @param encryptStr 密文
     * @return 明文
     */
    public static String decrypt(String encryptStr) {
        if (encryptStr == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptStr));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 如果解密失败（比如数据本身就不是加密的），就原样返回，防止报错
            return encryptStr;
        }
    }
}
