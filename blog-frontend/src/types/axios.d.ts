import 'axios';

declare module 'axios' {
    interface AxiosRequestConfig {
        needAuthentication?: boolean; // Add optional property
    }
}