import request from "../utils/request";

export interface CategoryCount {
    id: string;
    name: string;
    count: number;
}

// Modify return type to match server response format
interface ApiResponse<T> {
    status: number;
    message: string;
    data: T;
}

// Modify getCategoryCountList return value to match actual API response
function getCategoryCountList(): Promise<ApiResponse<CategoryCount[]>> {
    return request.get("/category/categoryCountList");
}

export { getCategoryCountList };