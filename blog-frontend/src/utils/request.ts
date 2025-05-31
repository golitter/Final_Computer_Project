// Secondary encapsulation of axios: using request and response interceptors
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { getToken } from './storage';

// axios.defaults.headers['Content-Type'] = "application/json"

// Create axios instance
let request = axios.create({
    baseURL: 'http://localhost:8080/', // API base URL
    timeout: 5000 // Request timeout
});

// Request interceptor
request.interceptors.request.use( (config) => {
    // Config object, headers property for request headers, often used to carry common parameters to the server
    let needAuthentication = config.needAuthentication
    // Set token
    if (needAuthentication && getToken()) {
        config.headers['token'] = getToken()
    }
    // console.log('config', config)
    // Return config object
    return config;
});

// Response interceptor
request.interceptors.response.use( (response) => {
    // console.log('suss', response)
    return response.data;
}, (error) => {
    // Error callback: handle network error messages
    // Define a variable: store network error message
    let status = error.response.request.status || 500;
    console.log('error', error)
    let message = getErrorMessage(status);
    console.log('error', error)
    ElMessage.error({
        type: 'error',
        message: message
    });
    return Promise.reject(message);
});

// Error message encapsulation function
function getErrorMessage(status: number): string {
    switch (status) {
        case 400:
            return 'Invalid request parameters';
        case 401:
            return 'Unauthorized';
        case 403:
            return 'Forbidden';
        case 404:
            return 'Request path error';
        case 500:
            return 'Server error';
        default:
            return 'Network error';
    }
}

export default request;