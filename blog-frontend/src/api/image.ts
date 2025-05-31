import axios, { AxiosInstance, AxiosResponse, AxiosProgressEvent } from "axios";
import { ElMessage } from "element-plus";
import { Ref } from "vue";

// Define response data interface
interface ApiResponse<T> {
  code: number;
  msg: string;
  data: T;
}

// Define upload response data type
interface UploadResponseData {
  url: string; // URL returned after successful upload
}

const service: AxiosInstance = axios.create({
  baseURL: "/image",
});

// Interceptor for handling responses
service.interceptors.response.use(
  (response: AxiosResponse<ApiResponse<any>>): any => {
    const res = response.data;
    const code = res.code || 200;
    if (code === 200) {
      return res.data;
    }

    const msg = `${res.code} ${res.msg}`;
    ElMessage.error(msg);

    return Promise.reject(new Error("Image upload failed: " + msg));
  },
  (error) => {
    // Error handling
    ElMessage.error("Network error or server not responding");
    return Promise.reject(error);
  }
);

/**
 * Upload image
 * @param {File} file Image file
 * @param {Ref<number>} progress Upload progress (Vue Ref)
 * @returns {Promise<UploadResponseData>} Returns a Promise that resolves to the upload result
 */
function uploadImage(
  file: File,
  progress: Ref<number>
): Promise<UploadResponseData> {
  const formData = new FormData();
  formData.append("file", file);

  return service.post<ApiResponse<UploadResponseData>>("/upload", formData, {
    onUploadProgress: (event: AxiosProgressEvent) => {
      if (event.total) {
        const v = Math.round((event.loaded / event.total) * 100);
        progress.value = v === 100 ? 80 : v;
      }
    },
  }).then((response: AxiosResponse<ApiResponse<UploadResponseData>>) => {
    return response.data.data; // Extract actual data from AxiosResponse
  });
}

export { uploadImage };
