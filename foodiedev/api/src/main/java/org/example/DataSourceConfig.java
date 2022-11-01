package org.example;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${datasource.decrypt.key}")
    private String decryptKey;


    @SuppressWarnings("unchecked")
    protected <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {

        //生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), this.decryptKey.getBytes()).getEncoded();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        String encryptUserName = aes.decryptStr(properties.determineUsername(), CharsetUtil.CHARSET_UTF_8);
        String encryptPassword = aes.decryptStr(properties.determinePassword(), CharsetUtil.CHARSET_UTF_8);

        System.out.println("解密后的username = [" + encryptUserName + "]");
        System.out.println("解密后的password = [" + encryptPassword + "]");

        DataSourceBuilder builder = DataSourceBuilder.create(properties.getClassLoader()).type(properties.getType()).driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl()).username(encryptUserName).password(encryptPassword);

        return (T) builder.type(type).build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

    /*
        test for encrypt
     */
//    public static void main(String[] args) {
//        String passwd = "mysecret-pw";
//        String username = "root";
//
//        String securityKey = "pan8Odpdj4O7fnap";
//        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), securityKey.getBytes()).getEncoded();
//        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
//        //加密为16进制表示
//        String encryptHexUserName = aes.encryptHex(username);
//
//        String encryptHexPassword = aes.encryptHex(passwd);
//        //解密为字符串
////        String decryptUserNameStr = aes.decryptStr(encryptHexUserName, CharsetUtil.CHARSET_UTF_8);
//        System.out.println("加密后的username = [" + encryptHexUserName + "]");
//        System.out.println("加密后的password= [" + encryptHexPassword + "]");
//        String encryptUserName = aes.decryptStr(encryptHexUserName, CharsetUtil.CHARSET_UTF_8);
//        String encryptPassword = aes.decryptStr(encryptHexPassword, CharsetUtil.CHARSET_UTF_8);
//        System.out.println(encryptUserName);
//        System.out.println(encryptPassword);
//    }

}
