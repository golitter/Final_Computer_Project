import CryptoJS from 'crypto-js';

/**
 * Use CryptoJS to perform MD5 encryption
 * @param plainText Plain text to be encrypted
 * @return string Encrypted string
 */
export function md5Encrypt(plainText: string): string {
    return CryptoJS.MD5(plainText).toString();
}
