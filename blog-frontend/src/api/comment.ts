import request from "../utils/request";

// Define the data type for comment list response
interface Comment {
  id: number;
  userName: string;
  content: string;
  createTime: string;
  [key: string]: any; // Other possible fields
}

// Define the response type for comment list
interface CommentListResponse {
  total: number;
  rows: Comment[];
}

/**
 * Get article comment list
 * @param articleId Article ID
 * @param pageNum Page number
 * @param pageSize Comments per page
 * @returns Comment list
 */
function getCommentList(articleId: number, pageNum: number, pageSize: number): Promise<CommentListResponse> {
  return request({
    url: "/comment/commentList",
    params: { articleId, pageNum, pageSize }
  });
}

/**
 * Add comment
 * @param articleId Article ID
 * @param content Comment content
 * @returns Created comment
 */
function addComment(articleId: number, content: string): Promise<Comment> {
  return request({
    url: "/comment",
    method: "post",
    data: { articleId, content },
    needAuthentication: true
  });
}

/**
 * Update comment
 * @param id Comment ID
 * @param content Comment content
 * @returns Updated comment
 */
function updateComment(id: number, content: string): Promise<Comment> {
  return request({
    url: "/comment",
    method: "put",
    data: { id, content },
    needAuthentication: true
  });
}

/**
 * Delete comment
 * @param id Comment ID
 * @returns Response for successful deletion
 */
function deleteComment(id: number): Promise<void> {
  return request({
    url: `/comment/${id}`,
    method: "delete",
    needAuthentication: true
  });
}

export { getCommentList, addComment, deleteComment, updateComment };
