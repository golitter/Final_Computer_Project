import request from '../utils/request';
import { LoginResponse, RegisterResponse } from '../types/user';


function getRandomColor(): string {
    // Generate random color, returns format #RRGGBB
    const randomColor = Math.floor(Math.random() * 16777215).toString(16);
    return `#${randomColor.padStart(6, '0')}`;
}

function getRandomAvatar(name: string): string {
    const backgroundColor = getRandomColor(); // Random background color
    const textColor = getRandomColor(); // Random text color
    const apiUrl = `https://ui-avatars.com/api/?name=${encodeURIComponent(name)}&background=${backgroundColor.slice(1)}&color=${textColor.slice(1)}`;
    
    return apiUrl;
}



/**
 * User login
 * @param username Username
 * @param password Password
 * @returns Promise<LoginResponse>
 */
function login(username: string, password: string): Promise<LoginResponse> {
    return request({
        url: "user/login",
        method: "post",
        data: { username, password },
    });
}

/**
 * User logout
 * @returns Promise<any>
 */
function logout(): Promise<boolean> {
    return request({
        url: "/logout",
        method: "post",
        needAuthentication: true,
    });
}

/**
 * User registration
 * @param username Username
 * @param email Email
 * @param password Password
 * @returns Promise<RegisterResponse>
 */
function register(username: string, password: string, email: string): Promise<RegisterResponse> {
    return request({
        url: "user/register",
        method: "post",
        data: { 
            username, 
            password, 
            email,
            avatar: getRandomAvatar(username) 
        },
    });
}

/**
 * Get administrator information
 * @returns Promise<any>
 */
function getAdminInfo(): Promise<any> {
    return request.get("/user/adminInfo");
}

/**
 * Get user information
 * @returns Promise<any>
 */
function getUserInfo(): Promise<any> {
    return request.get("/user/userInfo");
}

/**
 * Update user information
 * @param userInfo User information
 * @returns Promise<any>
 */
function updateUserInfo(userInfo: object): Promise<any> {
    return request({
        url: "/user/userInfo",
        method: "put",
        data: userInfo,
        needAuthentication: true,
    });
}

export { 
    login, 
    logout, 
    register, 
    getAdminInfo, 
    getUserInfo, 
    updateUserInfo 
};
