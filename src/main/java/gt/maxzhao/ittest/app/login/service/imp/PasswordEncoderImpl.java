package gt.maxzhao.ittest.app.login.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * @author maxzhao
 */
public class PasswordEncoderImpl implements PasswordEncoder {
    private Pattern BCRYPT_PATTERN;
    private Logger logger;
    private final int strength;
    private final SecureRandom random;

    /**
     * 构造函数用于设置不同的加密过程
     */
    public PasswordEncoderImpl() {
        this(-1);
    }

    public PasswordEncoderImpl(int strength) {
        this(strength, null);
    }

    public PasswordEncoderImpl(int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.logger = LoggerFactory.getLogger(this.getClass());
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    /**
     * 对原始密码进行编码。通常，一个好的编码算法应用SHA-1或更大的哈希值和一个8字节或更大的随机生成的salt。
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = BCrypt.gensalt(this.strength, this.random);
            } else {
                salt = BCrypt.gensalt(this.strength);
            }
        } else {
            salt = BCrypt.gensalt();
        }

        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    /**
     * 验证从存储中获得的已编码密码在经过编码后是否与提交的原始密码匹配。
     * 如果密码匹配，返回true;如果密码不匹配，返回false。存储的密码本身永远不会被解码。
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }

    /**
     * 如果为了更好的安全性，应该再次对已编码的密码进行编码，则返回true，否则为false。
     *
     * @param encodedPassword the encoded password to check
     * @return Returns true if the encoded password should be encoded again for better security, else false. The default implementation always returns false.
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
