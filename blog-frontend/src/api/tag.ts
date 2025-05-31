import request from "../utils/request";

// Define tag data interface
interface TagCount {
    id: number;
    name: string;
    count: number;
}

// Define API response interface
interface ApiResponse<T> {
    status: number;
    message: string;
    data: T;
}

/**
 * Get article count for all tags
 * @returns promise
 */
function getTagCountList(): Promise<ApiResponse<TagCount[]>> {
    return request.get("/tag/tagCountList");
}

export { getTagCountList, type TagCount, type ApiResponse };
