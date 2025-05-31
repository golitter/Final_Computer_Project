// Type definitions for user interface responses
export interface LoginResponse {
    status: number;  // Whether the login request was successful
    message?: string;   // Optional message field, may contain error or success information
    data?: {
        token: string;
        userInfo: any;
    };      // Optional data field, used to store the token and user information returned after login
}

export interface RegisterResponse {
    success: boolean;
    message?: string; // Optional field
    status?: number;  // Optional field
}