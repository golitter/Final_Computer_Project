/**
 * Get token
 * @returns {string | null} token
 */
function getToken(): string | null {
    return localStorage.getItem('token');
}

/**
 * Set token
 * @param {string} token token
 */
function setToken(token: string): void {
    localStorage.setItem('token', token);
}

/**
 * Remove token
 */
function removeToken(): void {
    localStorage.removeItem('token');
}

/**
 * Get user information
 * @returns {Record<string, any> | null} userInfo
 */
// storage.ts
function getUserInfo() {
    const userInfo = localStorage.getItem('userInfo');
    console.log('userInfo', userInfo);
    if (!userInfo) {
        return null; // Avoid returning undefined
    }
    try {
        return JSON.parse(userInfo); // Ensure safe JSON parsing
    } catch (error) {
        console.error('Failed to parse userInfo:', error);
        return null; // Return null if parsing fails
    }
}

/**
 * Set user information
 * @param {Record<string, any>} userInfo user information
 */
function setUserInfo(userInfo: object | null) {
    if (userInfo) {
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
    } else {
        localStorage.removeItem('userInfo'); // Avoid storing "undefined"
    }
}

/**
 * Remove user information
 */
function removeUserInfo(): void {
    localStorage.removeItem('userInfo');
}

export { getToken, setToken, removeToken, getUserInfo, setUserInfo, removeUserInfo };
